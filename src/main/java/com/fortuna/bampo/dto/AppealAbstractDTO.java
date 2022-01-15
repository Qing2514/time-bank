package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.Appeal.Status;
import com.fortuna.bampo.entity.User;

import java.util.UUID;

/**
 * 申诉摘要 DTO
 *
 * @author Eva7
 * @since 0.3.1
 */
public interface AppealAbstractDTO {

    /**
     * 返回 id
     *
     * @return 申诉 id
     */
    UUID getId();

    /**
     * 返回申诉者
     *
     * @return 申诉者
     */
    User getUser();

    /**
     * 返回活动
     *
     * @return 活动
     */
    Activity getActivity();

    /**
     * 返回状态
     *
     * @return 状态
     */
    Status getStatus();

    /**
     * 返回理由
     *
     * @return 理由
     */
    String getReason();
}
