package com.kangyonggan.app.simconf.exception;

/**
 * @author kangyonggan
 * @since 17/02/16
 */
public class ConfigException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    public ConfigException() {
        super("配置异常");
    }

    public ConfigException(String msg) {
        super(msg);
    }

    public ConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ConfigException(Throwable cause) {
        super(cause);
    }
}
