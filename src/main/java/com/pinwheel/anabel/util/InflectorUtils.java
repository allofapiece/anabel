package com.pinwheel.anabel.util;

import com.google.common.base.CaseFormat;

/**
 * Inflector utils. Contains helper methods for converting string values from one format to another via google
 * {@link CaseFormat} class.
 *
 * @version 1.0.0
 */
public class InflectorUtils {
    /**
     * Converts passed string from camel format to hyphen(kebab). Uses {@code lower} parameter for defining of passed
     * string case.
     * <pre>
     * {@code
     * String hyphenString = camel2Hyphen("lowerCamelCaseStringValue", true); // result "lower-camel-case-string-value"
     * String hyphenString = camel2Hyphen("upperCamelCaseStringValue", false); // result "upper-camel-case-string-value"
     * }
     * </pre>
     *
     * @param string string for converting.
     * @param lower  whether passed string in lower camel case.
     * @return converted to hyphen string.
     */
    public static String camel2Hyphen(String string, boolean lower) {
        return (lower ? CaseFormat.LOWER_CAMEL : CaseFormat.UPPER_CAMEL).to(CaseFormat.LOWER_HYPHEN, string);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#camel2Hyphen(String, boolean)} with {@code lower} argument as true
     * by default.
     *
     * @param string string for converting.
     * @return converted to hyphen string.
     */
    public static String camel2Hyphen(String string) {
        return InflectorUtils.camel2Hyphen(string, true);
    }

    /**
     * Converts passed string from hyphen(kebab) format to camel. Uses {@code lower} parameter for defining of result
     * string case.
     * <pre>
     * {@code
     * String camelString = hyphen2Camel("lower-camel-case-string-value", true); // result "lowerCamelCaseStringValue"
     * String camelString = hyphen2Camel("upper-camel-case-string-value", false); // result "upperCamelCaseStringValue"
     * }
     * </pre>
     *
     * @param string string for converting.
     * @param lower  whether passed string in lower camel case.
     * @return converted to hyphen string.
     */
    public static String hyphen2Camel(String string, boolean lower) {
        return CaseFormat.LOWER_HYPHEN.to((lower ? CaseFormat.LOWER_CAMEL : CaseFormat.UPPER_CAMEL), string);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#hyphen2Camel(String, boolean)} with {@code lower} argument as true
     * by default.
     *
     * @param string string for converting.
     * @return converted to camel case string.
     */
    public static String hyphen2Camel(String string) {
        return InflectorUtils.hyphen2Camel(string, true);
    }

    /**
     * Converts passed string from camel format to underscore. Uses {@code lowerInput} parameter for defining of passed
     * string case and {@code lowerOutput} parameter for result case.
     * <pre>
     * {@code
     * String underscoreString = camel2Underscore("camelCase", true, true); // result "camel_case"
     * String underscoreString = camel2Underscore("CamelCase", false, false); // result "CAMEL_CASE"
     * String underscoreString = camel2Underscore("camelCase", true, false); // result "CAMEL_CASE"
     * String underscoreString = camel2Underscore("CamelCase", false, true); // result "camel_case"
     * }
     * </pre>
     *
     * @param string      string for converting.
     * @param lowerInput  whether passed string in lower camel case.
     * @param lowerOutput whether result string will be in lower underscore format.
     * @return converted to underscore format string.
     */
    public static String camel2Underscore(String string, boolean lowerInput, boolean lowerOutput) {
        return (lowerInput ? CaseFormat.LOWER_CAMEL : CaseFormat.UPPER_CAMEL)
                .to((lowerOutput ? CaseFormat.LOWER_UNDERSCORE : CaseFormat.UPPER_UNDERSCORE), string);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#camel2Underscore(String, boolean, boolean)} with {@code lowerOutput}
     * argument as true by default.
     *
     * @param string     string for converting.
     * @param lowerInput whether passed string in lower camel case.
     * @return converted to underscore case string.
     */
    public static String camel2Underscore(String string, boolean lowerInput) {
        return InflectorUtils.camel2Underscore(string, lowerInput, true);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#camel2Underscore(String, boolean, boolean)} with {@code lowerInput}
     * and {@code lowerOutput} arguments as true by default.
     *
     * @param string string for converting.
     * @return converted to underscore case string.
     */
    public static String camel2Underscore(String string) {
        return InflectorUtils.camel2Underscore(string, true, true);
    }

    /**
     * Converts passed string from underscore format to camel. Uses {@code lowerInput} parameter for defining of passed
     * string case and {@code lowerOutput} parameter for result case.
     * <pre>
     * {@code
     * String camelString = underscore2Camel("underscore_case", true, true); // result "underscoreCase"
     * String camelString = underscore2Camel("UNDERSCORE_CASE", false, false); // result "UnderscoreCase"
     * String camelString = underscore2Camel("underscore_case", true, false); // result "UnderscoreCase"
     * String camelString = underscore2Camel("UNDERSCORE_CASE", false, true); // result "underscoreCase"
     * }
     * </pre>
     *
     * @param string      string for converting.
     * @param lowerInput  whether passed string in lower underscore case.
     * @param lowerOutput whether result string will be in lower lower camel case format.
     * @return converted to camel case format string.
     */
    public static String underscore2Camel(String string, boolean lowerInput, boolean lowerOutput) {
        return (lowerInput ? CaseFormat.LOWER_UNDERSCORE : CaseFormat.UPPER_UNDERSCORE)
                .to((lowerOutput ? CaseFormat.LOWER_CAMEL : CaseFormat.UPPER_CAMEL), string);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#underscore2Camel(String, boolean, boolean)} with {@code lowerOutput}
     * argument as true by default.
     *
     * @param string     string for converting.
     * @param lowerInput whether passed string in lower underscore case.
     * @return converted to underscore case string.
     */
    public static String underscore2Camel(String string, boolean lowerInput) {
        return InflectorUtils.underscore2Camel(string, lowerInput, true);
    }

    /**
     * Overloaded variant for {@link InflectorUtils#underscore2Camel(String, boolean, boolean)} with {@code lowerInput}
     * and {@code lowerOutput} arguments as true by default.
     *
     * @param string string for converting.
     * @return converted to camel case string.
     */
    public static String underscore2Camel(String string) {
        return InflectorUtils.underscore2Camel(string, true, true);
    }
}
