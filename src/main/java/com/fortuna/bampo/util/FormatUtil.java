package com.fortuna.bampo.util;

import ch.qos.logback.core.CoreConstants;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.IntegerType;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

import static com.fortuna.bampo.config.properties.JpaProperties.JPA_ESCAPED_PERCENT;
import static com.fortuna.bampo.config.properties.JpaProperties.JPA_PERCENT;
import static com.fortuna.bampo.config.properties.RestApiProperties.*;

/**
 * 摘要工具类
 *
 * @author Eva7, Ttanjo
 * @since 0.2.2
 */
@Configuration
@RequiredArgsConstructor
public class FormatUtil {
    public static String abstractReasonByKeyword(String origin) {
        return abstractByKeyword(origin, CoreConstants.EMPTY_STRING, REASON_ABSTRACT_LENGTH);
    }

    public static String abstractContentByKeyword(String origin, String keyword) {
        return abstractByKeyword(origin, cutQuery(keyword), CONTENT_ABSTRACT_LENGTH);
    }

    public static String abstractDetailsByKeyword(String origin, String keyword) {
        return abstractByKeyword(origin, cutQuery(keyword), DETAILS_ABSTRACT_LENGTH);
    }

    public static String abstractDescriptionByKeyword(String origin, String keyword) {
        return abstractByKeyword(origin, cutQuery(keyword), DESCRIPTION_ABSTRACT_LENGTH);
    }

    private static String abstractByKeyword(String origin, String keyword, Integer length) {
        int keywordIndex = Math.max(origin.indexOf(keyword), IntegerType.ZERO);
        int delimiterIndex = Math.max(origin.substring(IntegerType.ZERO, keywordIndex)
                .replaceAll(ARTICLE_PUNCTUATION_REGEX, ABSTRACT_PUNCTUATION_REPLACEMENT)
                .lastIndexOf(ABSTRACT_PUNCTUATION_REPLACEMENT), IntegerType.ZERO);
        int abstractIndex = delimiterIndex + length;
        return origin.length() < length
                ? origin
                : keywordIndex - delimiterIndex + keyword.length() <= length
                        ? abstractIndex < origin.length()
                        ? origin.substring(delimiterIndex, abstractIndex)
                        : origin.substring(origin.length() - length)
                        : keywordIndex + keyword.length() < length
                                ? origin.substring(IntegerType.ZERO, length)
                                : origin.substring(keywordIndex + origin.length() - length,
                                        keywordIndex + origin.length());
    }

    public static String escapeAndFormatQuery(String query) {
        return query.replace(JPA_PERCENT, JPA_ESCAPED_PERCENT).replaceAll(QUERY_DELIMITER_REGEX, JPA_PERCENT);
    }

    private static String cutQuery(String query) {
        return Stream.of(query.split(QUERY_DELIMITER_REGEX)).findFirst().orElse(CoreConstants.EMPTY_STRING);
    }
}
