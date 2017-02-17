package com.kangyonggan.app.simconf.config;

import com.kangyonggan.app.simconf.exception.ConfigException;
import com.kangyonggan.app.simconf.util.PropertiesUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author kangyonggan
 * @since 2017/2/16
 */
@Log4j2
public class Config {

    private Properties props;

    /**
     * @param resourcePath
     * @throws ConfigException
     */
    public Config(String resourcePath) throws ConfigException {
        loadProperties(PropertiesUtil.getProperties("config.root.path") + resourcePath + "/config.properties");
    }

    /**
     * 加载配置
     *
     * @param resourcePath
     * @throws ConfigException
     */
    private void loadProperties(String resourcePath) throws ConfigException {
        props = new Properties();

        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(resourcePath));
        } catch (FileNotFoundException e) {
            throw new ConfigException("配置文件不存在");
        }

        InputStreamReader reader;
        try {
            reader = new InputStreamReader(fis, "UTF-8");
            props.load(reader);
        } catch (Exception e) {
            throw new ConfigException("加载配置文件失败");
        }
    }

    /**
     * 获取公钥路径
     *
     * @return
     */
    public String getPublicKeyPath() {
        return getPropertiesOrDefault("publicKeyPath", "");
    }

    /**
     * 获取私钥路径
     *
     * @return
     */
    public String getPrivateKeyPath() {
        return getPropertiesOrDefault("privateKeyPath", "");
    }

    /**
     * 获取properties的值，默认值""
     *
     * @param name
     * @return
     */
    public String getProperties(String name) {
        return getPropertiesOrDefault(name, "");
    }

    /**
     * 获取properties的值
     *
     * @param name
     * @param defaultValue 默认值
     * @return
     */
    public String getPropertiesOrDefault(String name, String defaultValue) {
        return props.getProperty(name, defaultValue);
    }

}
