package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.JpaProperties.VOTING_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.ValidationProperties.UUID_BINARY_LENGTH;

/**
 * 投票类
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Voting {

    /**
     * id
     */
    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = VOTING_GENERATOR_NAME)
    @GenericGenerator(name = VOTING_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID id;

    /**
     * 候选人
     */
    @NotNull
    @Setter(AccessLevel.NONE)
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, unique = true, updatable = false)
    private User elector;

    /**
     * 是否候选
     */
    @Column(nullable = false)
    private boolean electing = true;

    /**
     * 票数
     */
    @OneToMany
    @Builder.Default
    private Set<User> voters = new LinkedHashSet<>();
}
