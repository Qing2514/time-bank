package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 用户登录数据
 *
 * @author Eva7
 * @since 0.1.5
 */
@Data
public class UserLogin {
    private String username;
    private String password;
}
