package com.kangyonggan.app.simconf.constants;

import lombok.Getter;

/**
 * 环境枚举
 *
 * @author kangyonggan
 * @since 2017/2/15
 */
public enum Env {

    COMMON("common", "通配环境"),
    LOCAL("local", "本地环境"),
    DEV("dev", "开发环境"),
    UAT("uat", "联调环境"),
    HD("hd", "灰度环境"),
    PROD("prod", "生产环境");

    /**
     * 错误码
     */
    @Getter
    private final String env;

    /**
     * 错误信息
     */
    @Getter
    private final String desc;

    Env(String end, String desc) {
        this.env = end;
        this.desc = desc;
    }
}
