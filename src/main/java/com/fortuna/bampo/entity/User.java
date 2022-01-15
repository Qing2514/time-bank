package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.JpaProperties.USER_TEAMS_OWNER;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 用户实体
 *
 * @author Eva7
 * @since 0.2.3
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class User implements UserDetails {

    /**
     * 用户 id
     */
    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Length(min = BCRYPT_ENCODED_LENGTH, max = BCRYPT_ENCODED_LENGTH)
    @Column(nullable = false, updatable = false, length = BCRYPT_ENCODED_LENGTH)
    private String id;

    /**
     * 用户名
     */
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    @Column(nullable = false, unique = true, length = NAME_LENGTH_UPPER)
    private String username;

    /**
     * 用户密码
     */
    @NotNull
    @Column(nullable = false, length = BCRYPT_ENCODED_LENGTH)
    @Length(min = BCRYPT_ENCODED_LENGTH, max = BCRYPT_ENCODED_LENGTH)
    private String password;

    /**
     * 是否过期
     */
    @Builder.Default
    @Column(nullable = false)
    private boolean expired = false;

    /**
     * 是否封禁
     */
    @Builder.Default
    @Column(nullable = false)
    private boolean locked = false;

    /**
     * 是否注销
     */
    @Builder.Default
    @Column(nullable = false)
    private boolean enabled = false;

    /**
     * 用户名修改时间
     */
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime dateUsernameUpdated = LocalDateTime.now();

    /**
     * 授权过期时间
     */
    @FutureOrPresent
    private LocalDateTime dateCredentialExpired;

    /**
     * 姓名
     */
    @NotNull
    @Column(nullable = false, updatable = false)
    private String fullName;

    /**
     * 城市
     */
    @NotNull
    @Column(nullable = false)
    private String city;

    /**
     * 用户邮箱
     */
    @Email
    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 用户电话
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private Long phoneNumber;

    /**
     * 用户角色
     */
    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new LinkedHashSet<>();

    /**
     * 用户团队
     */
    @ManyToMany(mappedBy = USER_TEAMS_OWNER)
    @Builder.Default
    private Set<Team> teams = new LinkedHashSet<>();

    /**
     * 关注
     */
    @ManyToMany
    @Builder.Default
    private Set<User> following = new LinkedHashSet<>();

    /**
     * 关注者
     */
    @Builder.Default
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return dateCredentialExpired == null || dateCredentialExpired.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User appUser = (User) o;
        return id != null && Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }
}
