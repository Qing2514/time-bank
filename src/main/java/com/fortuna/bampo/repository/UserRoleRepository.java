package com.fortuna.bampo.repository;

import com.fortuna.bampo.entity.UserRole;
import com.fortuna.bampo.entity.UserRole.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户角色数据库接口
 *
 * @author Eva7
 * @since 0.0.1
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Role> {
    // TODO: Nullable

    /**
     * 通过用户角色名查询用户角色，返回一个 {@code Optional} 描述该用户角色，若查询结果为空则返回一个空的 {@code Optional}
     *
     * @param name 用于搜索的用户角色名
     * @return 返回一个 {@code Optional} 包含搜索到的用户角色，若查询结果为空则返回一个空的 {@code Optional}
     * @throws IllegalArgumentException 当传入的用户角色名为空时抛出
     */
    Optional<UserRole> findByName(Role name) ;
}
