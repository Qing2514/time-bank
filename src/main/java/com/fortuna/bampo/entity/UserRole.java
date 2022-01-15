package com.fortuna.bampo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Locale;
import java.util.Objects;

/**
 * 用户角色
 *
 * @author Eva7
 * @since 0.1.2
 */
@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public final class UserRole {

    public enum Role {

        /**
         * 志愿者
         */
        VOLUNTEER,

        /**
         * 审核人
         */
        VERIFIER,

        /**
         * 主编
         */
        EDITOR,

        /**
         * 管理员
         */
        ADMIN;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public static Role fromString(String role) {
            if (role == null) {
                return null;
            }
            return valueOf(role.toUpperCase(Locale.US));
        }
    }

    @Id
    @NotNull
    @Column(nullable = false, updatable = false)
    private Role name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserRole that = (UserRole) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ")";
    }
}
