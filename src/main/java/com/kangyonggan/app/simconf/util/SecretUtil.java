package com.kangyonggan.app.simconf.util;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author kangyonggan
 * @since 2017/2/15
 */
@Log4j2
public class SecretUtil {

    /**
     * 加载公钥
     */
    public static PublicKey getPublicKey(String publicKeyPath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(publicKeyPath);
            return CryptoUtil.getPublicKey(inputStream, "RSA");
        } catch (Exception e) {
            log.error("无法加载对方公钥[" + publicKeyPath + "]", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.warn("关闭流失败", e);
            }
        }
        return null;
    }

    /**
     * 加载私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyPath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(privateKeyPath);
            return CryptoUtil.getPrivateKey(inputStream, "RSA");
        } catch (Exception e) {
            log.error("无法加载已方私钥[" + privateKeyPath + "]", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.warn("关闭流失败", e);
            }
        }
        return null;
    }

}
