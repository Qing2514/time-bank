package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 团队搜索筛选数据
 *
 * @author CMT, Eva7
 * @since 0.2.5
 */
@Data
public class TeamSearchFilter implements BaseSearchFilter {
    private String city;
    private String type;
    private String founder;
    private Integer numberLower;
    private Integer numberUpper;
}
