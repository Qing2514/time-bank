package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户验证 DTO
 *
 * @author Eva7
 * @since 0.2.2
 */
public interface UserVerificationDTO {
    /**
     * 返回 Token 绑定的用户
     *
     * @return 用户
     */
    User getUser();

    /**
     * 返回 Token
     *
     * @return Token
     */
    UUID getToken();

    /**
     * 返回发送时间
     *
     * @return 发送时间
     */
    LocalDateTime getDateSent();
}
