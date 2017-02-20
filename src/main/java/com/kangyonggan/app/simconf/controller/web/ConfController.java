package com.kangyonggan.app.simconf.controller.web;

import com.alibaba.fastjson.JSON;
import com.kangyonggan.app.simconf.config.Config;
import com.kangyonggan.app.simconf.model.Conf;
import com.kangyonggan.app.simconf.service.ConfService;
import com.kangyonggan.app.simconf.util.CryptoUtil;
import com.kangyonggan.app.simconf.util.SecretUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

/**
 * @author kangyonggan
 * @since 2017/2/20
 */
@Controller
@RequestMapping("conf")
@Log4j2
public class ConfController {

    @Autowired
    private ConfService confService;

    /**
     * 查找项目配置
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST)
    public void getProjConfs(HttpServletRequest request, HttpServletResponse response) {
        // 读取报文头
        ServletInputStream inputStream;
        byte bytes[] = new byte[35];
        int len;
        try {
            inputStream = request.getInputStream();
            len = inputStream.read(bytes, 0, 35);

            if (len != 35) {
                return;
            }
        } catch (Exception e) {
            log.warn("非法请求", e);
            return;
        }

        int totalLen;
        int signLen;
        try {
            // 报文头（总长度8+项目代码15+环境码8+签名长度4）= 35位
            String header = new String(bytes, 0, len);
            log.info("请求报文头:" + header);

            // 总长度,0~8位
            totalLen = Integer.parseInt(new String(bytes, 0, 8)) + 8;
            log.info("请求报文总长度:" + totalLen);

            // 从消息头中获取签名长度，用于读取签名
            String signLenStr = new String(bytes, 31, 4);// 签名长度,最后四位31~35
            signLen = Integer.parseInt(signLenStr);
            log.info("请求报文签名长度:" + signLen);
        } catch (Exception e) {
            log.warn("非法请求数据", e);
            return;
        }

        // 计算加密后报文体的长度，用于读取报文体（总长-头-签=密）
        int encryptedBytesLen = totalLen - 35 - signLen;
        log.info("请求报文密文长度:" + encryptedBytesLen);

        // 项目代码
        String projCode;
        try {
            projCode = new String(bytes, 8, 15).trim();
            log.info("请求报文项目代码:{}", projCode);
        } catch (Exception e) {
            log.warn("非法项目", e);
            return;
        }

        // 环境
        String env;
        try {
            env = new String(bytes, 23, 8).trim();
            log.info("请求报文环境代码:{}", env);
        } catch (Exception e) {
            log.warn("非法环境", e);
            return;
        }

        // 加载秘钥
        PrivateKey privateKey;
        PublicKey publicKey;
        try {
            Config config = new Config(projCode + File.separator + env);
            privateKey = SecretUtil.getPrivateKey(config.getPrivateKeyPath());
            publicKey = SecretUtil.getPublicKey(config.getPublicKeyPath());
        } catch (Exception e) {
            log.warn("服务端没有配置秘钥", e);
            return;
        }

        // 验签
        boolean isValid = isValid(inputStream, signLen, encryptedBytesLen, privateKey, publicKey);
        String data;
        if (isValid) {
            // 查库
            List<Conf> confs = confService.findConfsByProjCodeAndEnv(projCode, env);
            data = JSON.toJSONString(confs);
        } else {
            data = "{\"simErrCo\":\"1001\", \"simErrMsg\":\"验签失败\"}";
        }

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            push(outputStream, data, privateKey, publicKey);
        } catch (Exception e) {
            log.error("推送失败", e);
        }

        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 验签
     *
     * @param in
     * @param signLen
     * @param encryptedBytesLen
     * @param privateKey
     * @param publicKey
     * @return
     */
    private boolean isValid(ServletInputStream in, int signLen, int encryptedBytesLen, PrivateKey privateKey, PublicKey publicKey) {
        byte bytes[] = new byte[9999];
        try {
            // 签名
            in.read(bytes, 0, signLen);
            byte signBytes[] = ArrayUtils.subarray(bytes, 0, signLen);
            log.info("请求报文签名:signBytes.length={}", signBytes.length);

            // 密文
            byte encryptedBytes[] = new byte[encryptedBytesLen];
            in.read(encryptedBytes, 0, encryptedBytesLen);
            log.info("请求报文密文:encryptedBytes.length={}", encryptedBytes.length);

            // 解密
            byte xmlBytes[] = CryptoUtil.decrypt(encryptedBytes, privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            String xml = new String(xmlBytes, "UTF-8");
            log.info("请求报文内容:" + xml);

            // 验签
            boolean isValid = CryptoUtil.verifyDigitalSign(xmlBytes, signBytes, publicKey, "SHA1WithRSA");// 验签
            log.info("请求报文验签结果:{}", isValid);
            return isValid;
        } catch (Exception e) {
            log.error("验签异常", e);
        }
        return false;
    }

    /**
     * 推送配置
     *
     * @param out
     * @param data
     * @param privateKey
     * @param publicKey
     * @return
     */
    private boolean push(OutputStream out, String data, PrivateKey privateKey, PublicKey publicKey) {
        try {
            log.info("响应报文原文:{}", data);

            // 响应报文
            byte plainBytes[] = data.getBytes("UTF-8");
            log.info("响应报文:plainBytes.length={}", plainBytes.length);

            // 签名
            byte[] signBytes = CryptoUtil.digitalSign(plainBytes, privateKey, "SHA1WithRSA");
            log.info("响应报文签名:signBytes.length={}", signBytes.length);

            // 加密
            byte[] encryptedBytes = CryptoUtil.encrypt(plainBytes, publicKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            log.info("响应报文密文:encryptedBytes.length={}", encryptedBytes.length);

            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.leftPad(String.valueOf(12 + signBytes.length + encryptedBytes.length), 8, "0"));
            sb.append(StringUtils.leftPad(String.valueOf(signBytes.length), 4, "0"));
            log.info("响应报文头:{}", sb.toString());

            byte[] bytes = null;
            bytes = ArrayUtils.addAll(bytes, sb.toString().getBytes("UTF-8"));
            bytes = ArrayUtils.addAll(bytes, signBytes);
            bytes = ArrayUtils.addAll(bytes, encryptedBytes);
            log.info("响应报文最终返回:bytes.length={}", bytes.length);

            // 写响应
            out.write(bytes);
            out.flush();
            log.info("推送配置完成！");
            return true;
        } catch (Exception e) {
            log.error("推送配置失败", e);
            return false;
        }
    }
}
