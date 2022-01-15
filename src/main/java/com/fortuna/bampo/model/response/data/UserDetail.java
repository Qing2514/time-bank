package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

/**
 * 用户详情
 *
 * @author Eva7
 * @since 0.2.6
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserDetail extends UserInfo {
    @NotNull
    private String fullName;
}
