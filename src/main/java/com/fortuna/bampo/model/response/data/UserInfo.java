package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * 用户信息
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends UserAbstract implements BaseInfo {
    @Email
    @NotNull
    protected String email;
    protected Long phoneNumber;
    @PositiveOrZero
    protected Integer followingCount;
}


