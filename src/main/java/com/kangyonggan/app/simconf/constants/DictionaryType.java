package com.kangyonggan.app.simconf.constants;

import lombok.Getter;

/**
 * 字典类型
 *
 * @author kangyonggan
 * @since 2016/12/3
 */
public enum DictionaryType {

    ENVIRONMENT("environment", "环境");

    /**
     * 类型
     */
    @Getter
    private final String type;

    /**
     * 类型名称
     */
    @Getter
    private final String name;

    DictionaryType(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
