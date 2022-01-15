package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.util.Set;

/**
 * 用户信息 DTO
 *
 * @author Eva7
 * @since 0.2.3
 */
public interface UserInfoDTO extends UserAbstractDTO {
    /**
     * 返回电子邮箱
     *
     * @return 电子邮箱
     */
    String getEmail();

    /**
     * 返回电话号码
     *
     * @return 电话号码
     */
    Long getPhoneNumber();

    /**
     * 返回关注列表
     *
     * @return 关注列表
     */
    Set<User> getFollowing();
}
