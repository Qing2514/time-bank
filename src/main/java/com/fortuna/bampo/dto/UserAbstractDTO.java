package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.Team;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.entity.UserRole;

import java.util.Set;

/**
 * 用户摘要 DTO
 *
 * @author Eva7
 * @since 0.1.5
 */
public interface UserAbstractDTO {
    /**
     * 返回用户所在城市
     *
     * @return 城市
     */
    String getCity();

    /**
     * 返回用户名
     *
     * @return 用户名
     */
    String getUsername();

    /**
     * 返回用户加入的所有团队
     *
     * @return 团队集合
     */
    Set<Team> getTeams();

    /**
     * 返回用户的所有角色
     *
     * @return 用户角色集合
     */
    Set<UserRole> getRoles();

    /**
     * 返回用户的关注者
     *
     * @return 关注者集合
     */
    Set<User> getFollowers();
}
