package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 交易信息 DTO
 *
 * @author Ttanjo
 * @since 0.3.4
 */
public interface TransactionInfoDTO {

    /**
     * 返回交易 id
     *
     * @return 交易 id
     */
    UUID getId();

    /**
     * 返回收款用户
     *
     * @return 收款用户
     */
    User getPayee();

    /**
     * 返回交易金额
     *
     * @return 交易金额
     */
    Integer getAmount();

    /**
     * 返回付款用户
     *
     * @return 付款用户
     */
    User getPayer();

    /**
     * 返回账单结算时间
     *
     * @return 账单结算时间
     */
    LocalDateTime getDateBilled();
}
