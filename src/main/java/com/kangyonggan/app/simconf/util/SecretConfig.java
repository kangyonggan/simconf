package com.kangyonggan.app.simconf.util;

import lombok.Getter;
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
public class SecretConfig {

    private static SecretConfig instance;

    @Getter
    private PublicKey publicKey;

    @Getter
    private PrivateKey privateKey;

    private SecretConfig() {
        loadPublicKey();
        loadPrivateKey();
    }

    public static SecretConfig getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new SecretConfig();
                }
            }
        }
        return instance;
    }

    /**
     * 加载公钥
     */
    private void loadPublicKey() {
        String publicKeyPath = null;
        InputStream inputStream = null;
        try {
            publicKeyPath = PropertiesUtil.getProperties("PUBLIC_KEY_PATH");
            inputStream = new FileInputStream(publicKeyPath);
            publicKey = CryptoUtil.getPublicKey(inputStream, "RSA");
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
    }

    /**
     * 加载私钥
     */
    private void loadPrivateKey() {
        String privateKeyPath = null;
        InputStream inputStream = null;
        try {
            privateKeyPath = PropertiesUtil.getProperties("PRIVATE_KEY_PATH");
            inputStream = new FileInputStream(privateKeyPath);
            privateKey = CryptoUtil.getPrivateKey(inputStream, "RSA");
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
    }

}
