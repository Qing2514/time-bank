package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.JpaProperties.TEAM_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 团队角色
 *
 * @author CMT, Eva7
 * @since 0.1.2
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class Team {

    /**
     * 团队 id
     */
    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = TEAM_GENERATOR_NAME)
    @GenericGenerator(name = TEAM_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID id;

    /**
     * 团队名称
     */
    @NotNull
    @Pattern(regexp = UNICODE_NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    @Column(nullable = false, unique = true, length = NAME_LENGTH_UPPER)
    private String name;

    /**
     * 创建者
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User founder;

    /**
     * 团队成员
     */
    @ManyToMany
    @Builder.Default
    private Set<User> members = new LinkedHashSet<>();

    /**
     * 城市
     */
    @NotNull
    @Column(nullable = false)
    private String city;

    /**
     * 描述
     */
    @Length(max = DESCRIPTION_LENGTH_LIMIT)
    @Column(length = DESCRIPTION_LENGTH_LIMIT)
    private String description;

    /**
     * 主要活动类型
     */
    @NotNull
    @Column(nullable = false)
    private Activity.Type type;

    /**
     * 团队创建时间
     */
    @NotNull
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @Column(updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Team team = (Team) o;
        return id != null && Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
