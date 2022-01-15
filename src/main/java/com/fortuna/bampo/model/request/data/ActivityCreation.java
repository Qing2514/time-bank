package com.fortuna.bampo.model.request.data;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 创建活动数据
 *
 * @author Qing2514, Eva7
 * @since 0.1.6
 */
@Data
public class ActivityCreation implements BaseCreation {
    private String type;
    private String city;
    private String title;
    private Integer reward;
    private Integer number;
    private String details;
    private String address;
    private LocalDateTime dateEnd;
    private LocalDateTime dateStart;
}
