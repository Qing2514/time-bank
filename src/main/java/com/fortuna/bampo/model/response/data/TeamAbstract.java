package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.fortuna.bampo.config.properties.RestApiProperties.DESCRIPTION_ABSTRACT_LENGTH;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 团队摘要
 *
 * @author CMT, Eva7
 * @since 0.2.2
 */
@Data
@SuperBuilder
public class TeamAbstract implements BaseAbstract {

    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String id;
    protected String name;
    protected String city;
    protected String type;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String founder;
    @NotNull
    @Length(max = DESCRIPTION_ABSTRACT_LENGTH)
    protected String description;
}
