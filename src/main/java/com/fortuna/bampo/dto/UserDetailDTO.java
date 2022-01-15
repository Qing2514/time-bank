package com.fortuna.bampo.dto;

/**
 * 用户详细信息 DTO
 *
 * @author Eva7
 * @since 0.2.3
 */
public interface UserDetailDTO extends UserInfoDTO {
    /**
     * 返回真实姓名
     *
     * @return 真实姓名
     */
    String getFullName();
}
