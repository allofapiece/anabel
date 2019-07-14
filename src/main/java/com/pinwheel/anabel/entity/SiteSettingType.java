package com.pinwheel.anabel.entity;

import lombok.Getter;

/**
 * Type of site setting value. Used for defining of site setting value converter.
 *
 * @version 1.0.0
 */
@Getter
public enum SiteSettingType {
    /**
     * ArrayList type.
     */
    ARRAY_LIST("ArrayList"),

    /**
     * Boolean type.
     */
    BOOLEAN("Boolean"),

    /**
     * String type.
     */
    STRING("String");

    /**
     * Option for representing value in select of html.
     */
    private String option;

    /**
     * Constructor.
     *
     * @param option {@link SiteSettingType#option{}
     */
    SiteSettingType(String option) {
        this.option = option;
    }
}
