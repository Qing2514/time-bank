package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.UserAbstractDTO;
import com.fortuna.bampo.dto.UserDetailDTO;
import com.fortuna.bampo.dto.UserInfoDTO;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.entity.UserRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户数据库接口
 *
 * @author Eva7, Qing2514, lhx
 * @since 0.3.4
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // TODO: Nullable

    /**
     * 通过用户名查询用户，返回一个 {@code Optional} 描述该用户，若查询结果为空则返回一个空的 {@code Optional}
     *
     * @param username 用于搜索的用户名
     * @return 返回一个 {@code Optional} 包含搜索到的用户，若查询结果为空则返回一个空的 {@code Optional}
     * @throws IllegalArgumentException 当传入的用户名为空时抛出
     */
    @Query("select u from User u where u.username = ?1 and u.expired = false")
    Optional<User> findByUsername(String username);

    /**
     * 查找合法用户
     *
     * @param username 用于搜索的用户名
     * @return 返回一个 {@code Optional} 包含搜索到的用户，若查询结果为空则返回一个空的 {@code Optional}
     */
    @Query("select u from User u where " +
            "u.username = ?1 and u.enabled = true and u.locked = false and u.expired = false")
    Optional<User> findUserByUsername(String username);

    /**
     * 查询所有合法用户的 id，使用共享锁
     *
     * @return 返回所有用户 id 的集合
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select u.id from User u")
    Set<String> findAllIds();

    /**
     * 通过模糊用户名模糊查询用户
     *
     * @param query    用于搜索的模糊用户名
     * @param city     城市
     * @param locked   是否封禁
     * @param enabled  是否验证
     * @param userRole 用户角色
     * @param pageable 分页信息
     * @return 包含该模糊用户名的所有用户
     */
    @Query("select distinct u.username from User u left join u.roles r where " +
            "u.username like concat('%', ?1, '%') and " +
            "u.city like concat('%', ?2, '%') and " +
            "u.locked = ?3 and " +
            "u.enabled = ?4 and " +
            "(r.name in(?5) or ?5 is null) " +
            "order by size(u.followers) desc, size(u.roles) desc, size(u.teams) desc")
    List<String> findPageByUsernameContaining(String query,
                                              String city,
                                              boolean locked,
                                              boolean enabled,
                                              List<UserRole.Role> userRole,
                                              Pageable pageable);

    /**
     * 获取用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    @Query("select u from User u where u.username = ?1 and u.expired = false")
    Optional<UserAbstractDTO> findAbstractByUsername(String username);

    /**
     * 获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Query("select u from User u where u.username = ?1 and u.enabled = true and u.expired = false")
    Optional<UserInfoDTO> findInfoByUsername(String username);

    /**
     * 获取用户详情
     *
     * @param username 用户名
     * @return 用户详情
     */
    @Query("select u from User u where u.username = ?1 and u.expired = false")
    Optional<UserDetailDTO> findDetailByUsername(String username);
}