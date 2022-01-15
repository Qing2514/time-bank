package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 文章修改
 *
 * @author Ttanjo, Eva7
 * @since 0.2.3
 */
@Data
public class ArticleModification implements BaseModification {
    private String id;
    private String title;
    private String content;
    private String source;
}
