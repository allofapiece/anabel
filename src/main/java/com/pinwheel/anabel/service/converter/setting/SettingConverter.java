package com.pinwheel.anabel.service.converter.setting;

/**
 * @version 1.0.0
 */
public interface SettingConverter {
    Object convertToJava(String value);
    String convertToDatabase(Object value);
}
