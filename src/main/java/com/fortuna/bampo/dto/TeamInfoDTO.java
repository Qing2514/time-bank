package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.util.Set;

/**
 * 团队信息 DTO
 *
 * @author Eva7
 * @since 0.2.4
 */
public interface TeamInfoDTO extends TeamAbstractDTO {
    /**
     * 返回团队成员
     *
     * @return 团队成员
     */
    Set<User> getMembers();
}
