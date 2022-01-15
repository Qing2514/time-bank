package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.fortuna.bampo.config.properties.RestApiProperties.REASON_ABSTRACT_LENGTH;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 申诉摘要
 *
 * @author Eva7
 * @since 0.3.1
 */
@Data
@SuperBuilder
public class AppealAbstract implements BaseAbstract {

    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String id;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String username;
    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String activityId;
    protected String status;
    @NotNull
    @Length(max = REASON_ABSTRACT_LENGTH)
    protected String reason;
}
