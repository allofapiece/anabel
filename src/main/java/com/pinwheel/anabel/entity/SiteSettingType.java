package com.pinwheel.anabel.entity;

import lombok.Getter;

/**
 *
 *
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
@Getter
public enum SiteSettingType {
    /**
     *
     */
    ARRAY_LIST("ArrayList"),

    /**
     *
     */
    BOOLEAN("Boolean"),

    STRING("String");

    private String name;

    SiteSettingType(String name) {
        this.name = name;
    }
}