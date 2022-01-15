package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 创建文章数据
 *
 * @author Ttanjo
 * @since 0.2.0
 */
@Data
public class ArticleCreation implements BaseCreation {
    private String title;
    private String content;
    private String source;
}

