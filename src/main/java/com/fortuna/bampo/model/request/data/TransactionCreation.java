package com.fortuna.bampo.model.request.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 创建交易数据
 *
 * @author Ttanjo, Eva7
 * @since 0.3.4
 */
@Data
@AllArgsConstructor
public class TransactionCreation implements BaseCreation {
    private String payee;
    private String payer;
    private Integer amount;
}
