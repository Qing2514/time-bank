package com.fortuna.bampo.model.request.data;

import lombok.Data;

import java.util.List;

/**
 * 活动搜索筛选数据
 *
 * @author Eva7
 * @since 0.2.5
 */
@Data
public class ActivitySearchFilter implements BaseSearchFilter {
    private String city;
    private String type;
    private String founder;
    private List<String> statusList;
    private Integer rewardLower;
    private Integer rewardUpper;
    private Integer numberLower;
    private Integer numberUpper;
}
