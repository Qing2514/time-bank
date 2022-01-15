package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.util.Set;

/**
 * 申诉信息 DTO
 *
 * @author Eva7
 * @since 0.3.1
 */
public interface AppealInfoDTO extends AppealAbstractDTO {
    /**
     * 返回补偿
     *
     * @return 补偿的时间币
     */
    Integer getCompensation();

    /**
     * 返回选择通过的审核人
     *
     * @return 选择通过的审核人
     */
    Set<User> getPassedVerifiers();

    /**
     * 返回选择不通过的审核人
     *
     * @return 选择不通过的审核人
     */
    Set<User> getRejectedVerifiers();
}
