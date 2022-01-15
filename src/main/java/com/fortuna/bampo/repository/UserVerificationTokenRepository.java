package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.UserVerificationDTO;
import com.fortuna.bampo.entity.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * 用户验证 Token 数据库接口
 *
 * @author Eva7
 * @since 0.2.2
 */
public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, UUID> {
    /**
     * 查询未失效的 Token
     *
     * @param token 查询的 Token
     * @param now   现在的时间
     * @return 查询到的 Token 和用户
     */
    Optional<UserVerificationDTO> findByTokenAndDateExpiredAfter(UUID token, LocalDateTime now);

    /**
     * 通过用户名查询 Token
     *
     * @param username 查询的用户名
     * @param now      现在的时间
     * @return 查询到的 Token 和用户
     */
    @Query("select u from UserVerificationToken u left join u.user s where s.username = ?1 and u.dateExpired > ?2")
    Optional<UserVerificationDTO> findByUsernameAndDateExpiredAfter(String username, LocalDateTime now);

    /**
     * 删除 Token
     *
     * @param token 已验证的 Token
     * @return 删除个数
     */
    @Modifying
    int deleteByToken(UUID token);

    /**
     * 删除所有已过期的 Token
     *
     * @param now 现在的时间
     */
    @Modifying
    void deleteAllByDateExpiredBefore(LocalDateTime now);
}
