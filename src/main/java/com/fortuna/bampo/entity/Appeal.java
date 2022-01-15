package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.APPEAL_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.ValidationProperties.UUID_BINARY_LENGTH;

/**
 * 申诉类
 *
 * @author lhx, Eva7
 * @since 0.4.1
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Appeal {

    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = APPEAL_GENERATOR_NAME)
    @GenericGenerator(name = APPEAL_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID id;

    /**
     * 申诉用户名
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private User user;

    /**
     * 申诉的活动id
     */
    @NotNull
    @Setter(AccessLevel.NONE)
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Activity activity;

    /**
     * 申诉的状态
     */
    @NotNull
    @Enumerated
    @Builder.Default
    @Column(nullable = false)
    private Appeal.Status status = Status.VERIFY;

    /**
     * 补偿的时间币
     */
    @NotNull
    @PositiveOrZero
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Integer compensation;

    /**
     * 详细信息
     */
    @NotNull
    @Column(nullable = false)
    private String reason;

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
     * 创建时间
     */
    @PastOrPresent
    @Builder.Default
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    public enum Status {

        /**
         * 待审核
         */
        VERIFY,

        /**
         * 申诉审核通过
         */
        PASSED,

        /**
         * 申诉审核驳回
         */
        REJECTED,

        /**
         * 取消
         */
        CANCEL;

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
}
