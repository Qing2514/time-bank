package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.type.IntegerType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.*;

import static com.fortuna.bampo.config.properties.JpaProperties.ACTIVITY_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 活动实体类
 *
 * @author Qing2514, Eva7, lhx
 * @since 0.3.3
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class Activity {

    /**
     * 活动id
     */
    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = ACTIVITY_GENERATOR_NAME)
    @GenericGenerator(name = ACTIVITY_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID id;

    /**
     * 活动标题
     */
    @NotNull
    @Column(nullable = false)
    @Length(min = TITLE_LENGTH_LOWER, max = TITLE_LENGTH_UPPER)
    private String title;

    /**
     * 活动状态
     */
    @NotNull
    @Column(nullable = false)
    private Status status;

    /**
     * 活动所提供的时间币数量
     */
    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer reward;

    /**
     * 人数
     */
    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer number;

    /**
     * 发起者id
     */
    @NotNull
    @Setter(AccessLevel.NONE)
    @JoinColumn(nullable = false, updatable = false)
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    private User founder;

    /**
     * 活动类型
     */
    @NotNull
    @Column(nullable = false)
    private Type type;

    /**
     * 活动详情
     */
    @NotNull
    @Column(nullable = false)
    @Length(min = DETAILS_LENGTH_LOWER, max = DETAILS_LENGTH_UPPER)
    private String details;

    /**
     * 活动城市
     */
    @NotNull
    @Column(nullable = false)
    private String city;

    /**
     * 详细地址
     */
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private String address;

    /**
     * 报名用户
     */
    @ManyToMany(cascade = {CascadeType.DETACH})
    private Set<User> users = new LinkedHashSet<>();

    /**
     * 志愿者
     */
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.DETACH})
    private Set<User> volunteers = new LinkedHashSet<>();

    /**
     * 选择通过的审核人
     */
    @ManyToMany
    private Set<User> passedVerifiers = new LinkedHashSet<>();

    /**
     * 选择不通过的审核人
     */
    @ManyToMany
    private Set<User> rejectedVerifiers = new LinkedHashSet<>();

    /**
     * 交易信息
     */
    @ManyToMany(cascade = {CascadeType.DETACH})
    private Set<Transaction> transactions = new LinkedHashSet<>();

    /**
     * 活动开始时间
     */
    @Future
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateStart;

    /**
     * 活动结束时间
     */
    @Future
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateEnd;

    /**
     * 活动发布时间
     */
    @NotNull
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime datePublished = LocalDateTime.now();

    /**
     * 活动热度
     */
    @PositiveOrZero
    @Builder.Default
    @Column(nullable = false)
    private Integer popularity = IntegerType.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Activity activity = (Activity) o;
        return id != null && Objects.equals(id, activity.id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Status {

        /**
         * 招募
         */
        RECRUIT,

        /**
         * 审查
         */
        VERIFY,

        /**
         * 进行
         */
        PROGRESS,

        /**
         * 申诉
         */
        APPEAL,

        /**
         * 结束
         */
        FINISH,

        /**
         * 未通过
         */
        REJECTED;

        public static Status fromString(String status) {
            if (status == null) {
                return null;
            }
            return valueOf(status.toUpperCase(Locale.US));
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public enum Type {

        /**
         * 社区服务
         */
        COMMUNITY_SERVICE,

        /**
         * 环境保护
         */
        ENVIRONMENTAL_PROTECTION,

        /**
         * 知识宣传
         */
        KNOWLEDGE_PUBLICITY,

        /**
         * 助老助残
         */
        AGED_DISABLED_AID,

        /**
         * 扶贫济困
         */
        POVERTY_ALLEVIATION,

        /**
         * 社会援助
         */
        SOCIAL_ASSISTANCE,

        /**
         * 专业服务
         */
        PROFESSIONAL_SERVICE,

        /**
         * 其他
         */
        OTHER;

        public static Type fromString(String type) {
            if (type == null) {
                return null;
            }
            return valueOf(type.toUpperCase(Locale.US));
        }

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
