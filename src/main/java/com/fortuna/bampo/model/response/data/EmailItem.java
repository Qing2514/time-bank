package com.fortuna.bampo.model.response.data;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 邮件模板
 *
 * @author Eva7
 * @since 0.2.2
 */
@Data
@Builder
public class EmailItem {
    @Email
    @NotNull
    private String to;
    @NotNull
    private String text;
    @NotNull
    private String subject;
}
