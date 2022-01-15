package com.fortuna.bampo.dto;

import com.fortuna.bampo.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 文章摘要 DTO
 *
 * @author Ttanjo, Eva7
 * @since 0.2.4
 */
public interface ArticleAbstractDTO {

    /**
     * 返回文章 id
     *
     * @return 活动 id
     */
    UUID getId();

    /**
     * 返回文章名
     *
     * @return 文章名
     */
    String getTitle();

    /**
     * 返回文章内容
     *
     * @return 文章内容
     */
    String getContent();

    /**
     * 返回文章来源
     *
     * @return 文章来源
     */
    String getSource();

    /**
     * 返回文章作者
     *
     * @return 文章作者
     */
    User getAuthor();

    /**
     * 返回文章修改时间
     *
     * @return 文章修改时间
     */
    LocalDateTime getDateModified();
}
