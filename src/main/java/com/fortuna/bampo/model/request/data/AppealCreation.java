package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 发起申诉
 *
 * @author Eva7
 * @since 0.3.1
 */
@Data
public class AppealCreation implements BaseCreation {
    private String reason;
    private String activityId;
    private Integer compensation;
}
