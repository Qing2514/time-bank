package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.JpaProperties.TOKEN_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.ValidationProperties.UUID_BINARY_LENGTH;

/**
 * 用户验证 Token
 *
 * @author Eva7
 * @since 0.2.2
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class UserVerificationToken {

    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = TOKEN_GENERATOR_NAME)
    @GenericGenerator(name = TOKEN_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID token;
    @NotNull
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime dateSent = LocalDateTime.now();
    @NotNull
    @FutureOrPresent
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateExpired;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, unique = true, updatable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserVerificationToken that = (UserVerificationToken) o;
        return token != null && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "token = " + token + ")";
    }
}
