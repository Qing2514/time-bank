package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.Activity.Status;
import com.fortuna.bampo.entity.Activity.Type;
import com.fortuna.bampo.entity.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * 活动摘要 DTO
 *
 * @author Eva7
 * @since 0.1.5
 */
public interface ActivityAbstractDTO {

    /**
     * 返回活动 id
     *
     * @return 活动 id
     */
    UUID getId();

    /**
     * 返回活动类型
     *
     * @return 活动类型
     */
    Type getType();

    /**
     * 返回活动城市
     *
     * @return 城市
     */
    String getCity();

    /**
     * 返回活动标题
     *
     * @return 活动标题
     */
    String getTitle();

    /**
     * 返回发起人
     *
     * @return 发起人
     */
    User getFounder();

    /**
     * 返回活动状态
     *
     * @return 活动状态
     */
    Status getStatus();

    /**
     * 返回活动时间币
     *
     * @return 时间币
     */
    Integer getReward();

    /**
     * 返回活动人数
     *
     * @return 活动人数
     */
    Integer getNumber();

    /**
     * 返回活动详情
     *
     * @return 详情
     */
    String getDetails();

    /**
     * 返回活动热度
     *
     * @return 活动热度
     */
    Integer getPopularity();

    /**
     * 返回活动截止时间
     *
     * @return 活动截止时间
     */
    LocalDateTime getDateEnd();

    /**
     * 返回活动开始时间
     *
     * @return 活动开始时间
     */
    LocalDateTime getDateStart();

    /**
     * 返回志愿者
     *
     * @return 志愿者
     */
    Set<User> getVolunteers();
}
