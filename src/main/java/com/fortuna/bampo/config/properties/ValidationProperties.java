package com.fortuna.bampo.config.properties;

import org.springframework.context.annotation.Configuration;

/**
 * 验证常量接口
 *
 * @author Eva7
 * @since 0.1.2
 */
@Configuration
public class ValidationProperties {
    public static final int BCRYPT_ENCODED_LENGTH = 60;
    public static final int NAME_LENGTH_LOWER = 4;
    public static final int NAME_LENGTH_UPPER = 16;
    public static final String NAME_REGEX = "^\\w+$";
    public static final String UNICODE_NAME_REGEX = "^[\\w\\u4E00-\\u9FA5]+$";

    public static final int UUID_BINARY_LENGTH = 16;
    public static final int BASE64_ENCODED_UUID_LENGTH = 22;
    public static final int TITLE_LENGTH_LOWER = 8;
    public static final int TITLE_LENGTH_UPPER = 32;
    public static final int DETAILS_LENGTH_LOWER = 16;
    public static final int DETAILS_LENGTH_UPPER = 256;
    public static final int CONTENT_LENGTH_LOWER = 256;
    public static final int CONTENT_LENGTH_UPPER = 5120;
    public static final int SOURCE_LENGTH_UPPER = 256;
    public static final int DESCRIPTION_LENGTH_LIMIT = 128;
}
