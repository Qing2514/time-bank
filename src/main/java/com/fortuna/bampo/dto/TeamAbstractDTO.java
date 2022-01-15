package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.User;

import java.util.UUID;

/**
 * 团队摘要 DTO
 *
 * @author Eva7
 * @since 0.1.5
 */
public interface TeamAbstractDTO {
    /**
     * 返回团队 id
     *
     * @return 团队 id
     */
    UUID getId();

    /**
     * 返回团队名
     *
     * @return 团队名
     */
    String getName();

    /**
     * 返回团队所在城市
     *
     * @return 城市
     */
    String getCity();

    /**
     * 返回创建者
     *
     * @return 创建者
     */
    User getFounder();

    /**
     * 返回服务类型
     *
     * @return 服务类型
     */
    Activity.Type getType();

    /**
     * 返回团队简介
     *
     * @return 团队简介
     */
    String getDescription();
}
