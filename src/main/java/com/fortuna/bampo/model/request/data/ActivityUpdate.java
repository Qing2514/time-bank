package com.fortuna.bampo.model.request.data;

import lombok.Data;

import java.util.List;

/**
 * 修改活动数据
 *
 * @author Qing2514
 * @since 0.2.6
 */
@Data
public class ActivityUpdate implements BaseUpdate {
    private String addition;
    private List<String> rejectedVolunteers;
}
