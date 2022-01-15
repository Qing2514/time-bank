package com.fortuna.bampo.model.request.data;

import lombok.Data;

/**
 * 用户名更新
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
public class UsernameUpdate implements BaseUpdate {
    private String username;
}
