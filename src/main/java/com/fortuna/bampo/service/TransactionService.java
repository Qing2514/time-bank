package com.fortuna.bampo.service;

import com.fortuna.bampo.entity.Transaction;
import com.fortuna.bampo.model.request.data.TransactionCreation;
import com.fortuna.bampo.model.response.data.TransactionInfo;

import java.util.UUID;

/**
 * 交易服务接口
 *
 * @author Ttanjo, Eva7
 * @since 0.3.4
 */
public interface TransactionService {

    /**
     * 创建一条新交易记录（前端）
     *
     * @param transaction 待创建的交易记录实体
     * @return 创建后的账户流水头目 id
     */
    UUID create(TransactionCreation transaction);

    /**
     * 获取交易信息
     *
     * @param encodedId 加密的交易账户流水头目 id
     * @return 交易信息
     */
    TransactionInfo getInfo(String encodedId);

    /**
     * 修改活动信息
     *
     * @param encodedId 加密的交易账户流水头目 id
     * @param amount    交易金额
     * @param success   账单交易状态
     * @return 是否修改成功
     */
    boolean modify(String encodedId, Integer amount, boolean success);

    /**
     * 删除交易信息
     *
     * @param encodedId 加密的交易账户流水头目 id
     * @return 是否删除成功
     */
    boolean delete(String encodedId);

    /**
     * 通过交易 id 获取交易
     *
     * @param encodedId Base64 加密的活动 id
     * @return 匹配该活动 id 的活动
     */
    Transaction findById(String encodedId);
}
