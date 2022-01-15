package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * 文章信息
 *
 * @author Eva7
 * @since 0.2.4
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArticleInfo extends ArticleAbstract implements BaseInfo {
}
