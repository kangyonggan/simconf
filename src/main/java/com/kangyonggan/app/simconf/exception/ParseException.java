package com.kangyonggan.app.simconf.exception;

/**
 * @author kangyonggan
 * @since 17/02/16
 */
public class ParseException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = 1L;

    public ParseException() {
        super("解析异常");
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }
}
