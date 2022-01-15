package com.fortuna.bampo.service;

import com.fortuna.bampo.contract.Contract;
import com.fortuna.bampo.entity.Transaction;
import com.fortuna.bampo.model.request.data.TransactionCreation;
import com.fortuna.bampo.model.response.data.TransactionInfo;
import com.fortuna.bampo.repository.TransactionRepository;
import com.fortuna.bampo.util.CodecUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * 交易服务实现
 *
 * @author Ttanjo, Eva7
 * @since 0.3.4
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;

    @Override
    public UUID create(TransactionCreation transactionCreation) {
        Transaction transaction = Transaction.builder()
                .amount(transactionCreation.getAmount())
                .payee(userService.loadUserByUsername(transactionCreation.getPayee()))
                .payer(userService.loadUserByUsername(transactionCreation.getPayer()))
                .dateBilled(LocalDateTime.now())
                .build();

        UUID id = transactionRepository.saveAndFlush(transaction).getId();

        try {
            Contract.userToUserTransfer(transactionCreation.getPayee()
                    , transactionCreation.getPayer()
                    , transactionCreation.getAmount()
                    , id.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    UUID create(String payee, String payer, Integer amount) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .payee(userService.loadUserByUsername(payee))
                .payer(userService.loadUserByUsername(payer))
                .build();
        return transactionRepository.saveAndFlush(transaction).getId();
    }

    @Override
    public TransactionInfo getInfo(String encodedId) {
        return transactionRepository.getInfoById(CodecUtil.decodeUuid(encodedId))
                .map(transactionInfoDTO -> TransactionInfo.builder()
                        .id(CodecUtil.encodeUuid(transactionInfoDTO.getId()))
                        .payee(transactionInfoDTO.getPayee().getUsername())
                        .payer(transactionInfoDTO.getPayer().getUsername())
                        .amount(transactionInfoDTO.getAmount())
                        .dateBilled(transactionInfoDTO.getDateBilled())
                        .build()).orElseThrow(() -> new IllegalStateException("Transaction id not found"));
    }

    @Override
    public boolean modify(String encodedId, Integer amount, boolean success) {
        Transaction transaction = transactionRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    a.setAmount(Objects.requireNonNullElse(amount, a.getAmount()));
                    if (success) {
                        a.setDateBilled(LocalDateTime.now());
                    }
                    return a;
                }).orElseThrow(() -> new IllegalStateException("Transaction id not found"));
        return transactionRepository.saveAndFlush(transaction) == transaction;
    }

    @Override
    public boolean delete(String encodedId) {
        return transactionRepository.deleteTransactionById(CodecUtil.decodeUuid(encodedId)) != 0;
    }

    @Override
    public Transaction findById(String encodedId) {
        return transactionRepository.findById(CodecUtil.decodeUuid(encodedId))
                .orElseThrow(() -> new IllegalStateException("Transaction id not found"));
    }
}
