package com.fortuna.bampo.model.request;

import com.fortuna.bampo.model.request.data.BaseSearchFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 搜索请求
 *
 * @author lhx, Eva7
 * @since 0.2.2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRequest<E extends BaseSearchFilter> extends BaseDataRequest<E> {
    private String query;
    private Integer page;
    private String order;
    private String orderBy;
    private Integer pageSize;
}
