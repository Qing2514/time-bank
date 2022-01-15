package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 用户修改数据
 *
 * @author CMT, Eva7
 * @since 0.2.3
 */
@Data
public class UserModification implements BaseModification {
    private String city;
    private String email;
    private Long phoneNumber;
}
