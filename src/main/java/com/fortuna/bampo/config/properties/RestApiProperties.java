package com.fortuna.bampo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * RESTful api 常量接口
 *
 * @author Eva7
 * @since 0.2.5
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bampo.rest-api")
public class RestApiProperties {

    public static final String BASE = "/api/";
    public static final String VERSION = "v1";
    public static final String SEPARATOR = "/";
    public static final String CROSS_ORIGIN = "*";
    public static final String PATH_ALL_MATCH = "/**";
    public static final String PREFIX = BASE + VERSION + SEPARATOR;
    public static final String LOGIN_PATH = SEPARATOR + "login";
    public static final String REGISTRATION_PATH = SEPARATOR + "registration";
    public static final String VERIFICATION_PATH = SEPARATOR + "verification";
    public static final String CONTACT_PATH = SEPARATOR + "contact";
    public static final String VERIFICATION_PARAM_TOKEN = "token";

    public static final int REASON_ABSTRACT_LENGTH = 32;
    public static final int DETAILS_ABSTRACT_LENGTH = 64;
    public static final int CONTENT_ABSTRACT_LENGTH = 128;
    public static final int DESCRIPTION_ABSTRACT_LENGTH = 32;
    public static final String QUERY_DELIMITER_REGEX = "[\\p{P}\\s]";
    public static final String ABSTRACT_PUNCTUATION_REPLACEMENT = "。";
    public static final String ARTICLE_PUNCTUATION_REGEX = "[(,\\s)，(.\\s)。(:\\s)：(;\\s)；(?\\s)？(!\\s)！(……)(...\\s)" +
            "\\f\\n\\r]";

    public static final String VERIFICATION_EMAIL_SUBJECT = "[Bampo] 验证您的电子邮箱";
    public static final String VERIFICATION_EMAIL_TEMPLATE = """
            尊敬的 #username 您好,

            非常感谢您注册 Bampo 用户!

            为了您的账户安全，请先验证您的电子邮箱：

            #verification_link

            如果上面的链接无法使用，请手动复制到浏览器并访问，请勿将本邮件内容泄露给他人！
                        
            若您并未注册 Bampo 账户，请通过下方链接联系我们
                        
            #contact_link
                        
            此致
            Fortuna 团队
            """;
    public static final String EMAIL_USERNAME_PATTERN = "#username";
    public static final String EMAIL_VERIFICATION_LINK_PATTERN = "#verification_link";
    public static final String EMAIL_CONTACT_LINK_PATTERN = "#contact_link";

    public static final String ACTIVITY_UPDATE_TEMPLATE = "\n\n---更新于 #update_time---\n\n";
    public static final String ACTIVITY_DECLARE_TEMPLATE = "\n\n---申报于 #update_time---\n\n";
    public static final String ACTIVITY_UPDATE_TIME_PATTERN = "#update_time";

    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_ORDER = "asc";

    private static final String EMAIL_FROM_NAME = "Bampo";

    public static String getEmailAddress(String emailAddress) {
        return EMAIL_FROM_NAME + " <" + emailAddress + ">";
    }

    private String host;
}
