package com.fortuna.bampo.config.properties;

import org.springframework.context.annotation.Configuration;

/**
 * Spring Data JPA 常量接口
 *
 * @author Eva7
 * @since 0.2.2
 */
@Configuration
public class JpaProperties {
    public static final String GENERATOR_STRATEGY_UUID = "org.hibernate.id.UUIDGenerator";
    public static final String TEAM_GENERATOR_NAME = "team_uuid";
    public static final String ARTICLE_GENERATOR_NAME = "article_uuid";
    public static final String APPEAL_GENERATOR_NAME = "appeal_uuid";
    public static final String ACTIVITY_GENERATOR_NAME = "activity_uuid";
    public static final String VOTING_GENERATOR_NAME = "voting_uuid";
    public static final String TOKEN_GENERATOR_NAME = "token_uuid";

    public static final String USER_ENTITY_NAME = "user";
    public static final String ARTICLE_ENTITY_NAME = "article";
    public static final String TEAM_ENTITY_NAME = "team";
    public static final String ACTIVITY_ENTITY_NAME = "activity";
    public static final String APPEAL_ENTITY_NAME = "appeal";
    public static final String VOTING_ENTITY_NAME = "voting";
    public static final String USER_TEAMS_OWNER = "members";

    public static final String JPA_PERCENT = "%";
    public static final String JPA_ESCAPED_PERCENT = "\\%";
}
