package com.pinwheel.anabel.service.converter.setting;

/**
 * Setting converter converts string representing of the setting value from database to specify type in java and vise
 * versa.
 *
 * @version 1.0.0
 */
public interface SettingConverter {
    /**
     * Converts database value to java represented form.
     *
     * @param value value from database.
     * @return converted value.
     */
    Object convertToJava(String value);

    /**
     * Converts value to string for saving it in database.
     *
     * @param value java form value.
     * @return string which will be saved in database.
     */
    String convertToDatabase(Object value);
}
