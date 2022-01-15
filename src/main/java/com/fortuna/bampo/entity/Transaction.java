package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 交易类
 *
 * @author CMT, Eva7
 * @since 0.3.4
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Transaction {

    /**
     * 账户流水头目 id
     */
    @Id
    @NotNull
    @Column(nullable = false)
    private UUID id;

    /**
     * 收款用户
     */
    @JoinColumn
    @ManyToOne(optional = false)
    private User payee;

    /**
     * 交易金额
     */
    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private Integer amount;

    /**
     * 付款用户
     */
    @JoinColumn
    @ManyToOne(optional = false)
    private User payer;

    /**
     * 账单结算时间
     */
    @PastOrPresent
    @Builder.Default
    private LocalDateTime dateBilled = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Transaction transaction = (Transaction) o;
        return id != null && Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}