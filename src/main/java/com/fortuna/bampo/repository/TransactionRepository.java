package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.TransactionInfoDTO;
import com.fortuna.bampo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * 交易数据库接口
 *
 * @author Ttanjo
 * @since 0.3.4
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    /**
     * 根据 id 获取交易记录
     *
     * @param id 交易 id
     * @return 交易信息
     */
    @Query("select a from Transaction a where a.id = ?1")
    Optional<TransactionInfoDTO> getInfoById(UUID id);

    /**
     * 根据 id 删除交易记录
     *
     * @param id 交易 id
     * @return 删除的条数
     */
    @Modifying
    int deleteTransactionById(UUID id);
}
