package com.kangyonggan.app.simconf.controller.web;

import com.kangyonggan.app.simconf.annotation.LogTime;
import com.kangyonggan.app.simconf.controller.BaseController;
import com.kangyonggan.app.simconf.service.ConfService;
import com.kangyonggan.app.simconf.service.ProjectService;
import com.kangyonggan.app.simconf.util.CryptoUtil;
import com.kangyonggan.app.simconf.util.SecretConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@RestController
@RequestMapping("conf")
@Log4j2
public class ConfController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ConfService confService;

    /**
     * 获取项目配置
     *
     * @param data
     * @return
     */
    @RequestMapping
    @LogTime
    public Map<String, Object> getProjectConfs(@RequestParam("data") String data) {
        log.info("===============获取项目配置开始===============");
        Map<String, Object> resultMap = getResultMap();

        // 0. 校验
        if (StringUtils.isEmpty(data)) {
            resultMap.put(ERR_CODE, "0001");
            resultMap.put(ERR_MSG, "密文不能为空！");
            return resultMap;
        }

        // 1. 解密
        SecretConfig config = SecretConfig.getInstance();
        byte[] xmlBytes = null;
        byte[] signBytes = null;

        // 2. 验签
        try {
            boolean isValid = CryptoUtil.verifyDigitalSign(xmlBytes, signBytes, config.getPublicKey(), "SHA1WithRSA");// 验签
        } catch (Exception e) {
            log.error("验签失败", e);
        }

        // 3. 校验

        // 4. 请求数据

        // 5. 组装报文

        // 6. 签名

        // 7. 加密

        log.info("===============获取项目配置结束===============");
        return getResultMap();
    }

}
