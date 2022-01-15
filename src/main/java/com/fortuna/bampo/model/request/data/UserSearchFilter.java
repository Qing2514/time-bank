package com.fortuna.bampo.model.request.data;

import lombok.Data;

import java.util.List;

/**
 * 用户搜索筛选数据
 *
 * @author Eva7
 * @since 0.1.3
 */
@Data
public class UserSearchFilter implements BaseSearchFilter {
    private String city;
    private boolean enabled = true;
    private boolean locked = false;
    private List<String> userRoleList;
}
