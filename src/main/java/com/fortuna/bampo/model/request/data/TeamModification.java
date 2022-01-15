package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 团队修改数据
 *
 * @author CMT, Eva7
 * @since 0.2.3
 */
@Data
public class TeamModification implements BaseModification {
    private String name;
    private String city;
    private String type;
    private String description;
}