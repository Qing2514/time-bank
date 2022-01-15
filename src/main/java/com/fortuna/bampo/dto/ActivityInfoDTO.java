package com.fortuna.bampo.dto;

import java.time.LocalDateTime;

/**
 * 活动信息 DTO
 *
 * @author Eva7
 * @since 0.2.6
 */
public interface ActivityInfoDTO extends ActivityAbstractDTO {
    /**
     * 获取地址
     *
     * @return 地址
     */
    String getAddress();

    /**
     * 获取发布时间
     *
     * @return 发布时间
     */
    LocalDateTime getDatePublished();
}
