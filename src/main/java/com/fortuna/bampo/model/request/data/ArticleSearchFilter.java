package com.fortuna.bampo.model.request.data;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文章搜索筛选数据
 *
 * @author Ttanjo
 * @since 0.2.0
 */
@Data
public class ArticleSearchFilter implements BaseSearchFilter {
    private String source;
    private String author;
    private LocalDateTime dateModifiedLower;
    private LocalDateTime dateModifiedUpper;
}
