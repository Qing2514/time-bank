package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 团队注册数据
 *
 * @author Eva7
 * @since 0.0.5
 */
@Data
public class TeamRegistration implements BaseRegistration {
    private String name;
    private String city;
    private String type;
    private String description;
}
