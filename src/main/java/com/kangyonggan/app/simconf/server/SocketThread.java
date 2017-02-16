package com.kangyonggan.app.simconf.server;

import com.alibaba.fastjson.JSON;
import com.kangyonggan.app.simconf.config.Config;
import com.kangyonggan.app.simconf.exception.ConfigException;
import com.kangyonggan.app.simconf.exception.ParseException;
import com.kangyonggan.app.simconf.model.Conf;
import com.kangyonggan.app.simconf.service.ConfService;
import com.kangyonggan.app.simconf.util.CryptoUtil;
import com.kangyonggan.app.simconf.util.SecretUtil;
import com.kangyonggan.app.simconf.util.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/1/3
 */
@Log4j2
public class SocketThread extends Thread {

    private InputStream in;

    private OutputStream out;

    private ConfService confService;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private Map<String, Object> dataMap;

    public SocketThread(Socket socket) {
        dataMap = new HashMap();
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (Exception e) {
            log.error(e);
        }
        confService = SpringUtils.getBean(ConfService.class);
    }

    @Override
    public void run() {
        byte bytes[] = new byte[9999];
        int len;

        try {
            while ((len = in.read(bytes, 0, 35)) != -1) {
                // 解析报文
                parseData(bytes, len);

                // 初始化配置
                initConfig();

                // 验签
                boolean isValid = isValid(bytes);

                if (isValid) {
                    // 查库
                    List<Conf> confs = confService.findConfsByProjCodeAndEnv((String) dataMap.get("projCode"), (String) dataMap.get("env"));
                    String data = JSON.toJSONString(confs);
                    push(data);
                    SocketMap.put(dataMap.get("projCode") + "|" + dataMap.get("env"), this);
                } else {
                    push("验签失败");
                }
            }
        } catch (IOException e) {
            log.warn("客户端可能断开了链接", e);
            SocketMap.remove(dataMap.get("projCode") + "|" + dataMap.get("env"));
        } catch (ConfigException e) {
            log.warn(e.getMessage(), e);
        } catch (ParseException e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * 解析报文
     *
     * @param bytes
     * @param len
     * @throws ParseException
     */
    private void parseData(byte bytes[], int len) throws ParseException {
        try {
            // 报文头（总长度8+项目代码15+环境码8+签名长度4）= 35位
            String header = new String(bytes, 0, len);
            log.info("报文头:" + header);
            dataMap.put("header", header);

            // 总长度,0~8位
            int totalLen = Integer.parseInt(new String(bytes, 0, 8)) + 8;
            log.info("报文总长度:" + totalLen);
            dataMap.put("totalLen", totalLen);

            // 从消息头中获取签名长度，用于读取签名
            String signLenStr = new String(bytes, 31, 4);// 签名长度,最后四位31~35
            int signLen = Integer.parseInt(signLenStr);
            log.info("签名长度:" + signLen);
            dataMap.put("signLen", signLen);

            // 计算加密后报文体的长度，用于读取报文体（总长-头-签=密）
            int encryptedBytesLen = totalLen - 35 - signLen;
            log.info("加密后报文的长度:" + encryptedBytesLen);
            dataMap.put("encryptedBytesLen", encryptedBytesLen);

            // 项目代码
            String projCode = new String(bytes, 8, 15).trim();
            log.info("项目代码:{}", projCode);
            dataMap.put("projCode", projCode);

            // 环境
            String env = new String(bytes, 23, 8).trim();
            log.info("环境代码:{}", env);
            dataMap.put("env", env);
        } catch (Exception e) {
            throw new ParseException("解析报文异常", e);
        }
    }

    /**
     * 初始化配置
     *
     * @throws ConfigException
     */
    private void initConfig() throws ConfigException {
        try {
            Config config = new Config(dataMap.get("projCode") + "-" + dataMap.get("env"));
            privateKey = SecretUtil.getPrivateKey(config.getPrivateKeyPath());
            publicKey = SecretUtil.getPublicKey(config.getPublicKeyPath());
        } catch (Exception e) {
            throw new ConfigException("初始化配置异常", e);
        }
    }

    /**
     * 验签
     *
     * @return
     */
    private boolean isValid(byte bytes[]) {
        try {
            // 签名
            int signLen = (int) dataMap.get("signLen");
            in.read(bytes, 0, signLen);
            byte signBytes[] = ArrayUtils.subarray(bytes, 0, signLen);

            // 密文
            int encryptedBytesLen = (int) dataMap.get("encryptedBytesLen");
            byte encryptedBytes[] = new byte[encryptedBytesLen];
            in.read(encryptedBytes, 0, encryptedBytesLen);

            // 解密
            byte xmlBytes[] = CryptoUtil.decrypt(encryptedBytes, privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
            String xml = new String(xmlBytes, "UTF-8");
            log.info("报文:" + xml);

            // 验签
            boolean isValid = CryptoUtil.verifyDigitalSign(xmlBytes, signBytes, publicKey, "SHA1WithRSA");// 验签
            log.info("验签结果:{}", isValid);
            return isValid;
        } catch (Exception e) {
            log.error("验签异常", e);
        }
        return false;
    }

    /**
     * 推送配置
     *
     * @param data
     */
    public boolean push(String data) {
        try {
            log.info("响应报文:{}", data);

            // 响应报文
            byte plainBytes[] = data.getBytes("UTF-8");

            // 签名
            byte[] signBytes = CryptoUtil.digitalSign(plainBytes, privateKey, "SHA1WithRSA");

            // 加密
            byte[] encryptedBytes = CryptoUtil.encrypt(plainBytes, publicKey, 2048, 11, "RSA/ECB/PKCS1Padding");

            StringBuffer buffer = new StringBuffer();
            buffer.append(StringUtils.leftPad(String.valueOf(4 + signBytes.length + encryptedBytes.length), 8, "0"));
            buffer.append(StringUtils.leftPad(String.valueOf(signBytes.length), 4, "0"));

            byte[] bytes = null;
            bytes = ArrayUtils.addAll(bytes, buffer.toString().getBytes("UTF-8"));
            bytes = ArrayUtils.addAll(bytes, signBytes);
            bytes = ArrayUtils.addAll(bytes, encryptedBytes);

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
