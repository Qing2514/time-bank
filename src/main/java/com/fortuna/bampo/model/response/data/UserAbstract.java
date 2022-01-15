package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 用户摘要
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
@SuperBuilder
public class UserAbstract implements BaseAbstract {
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String username;
    protected String city;
    protected List<String> teams;
    protected List<String> roles;
    @PositiveOrZero
    protected Integer followersCount;
}
