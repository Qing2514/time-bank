package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

import static com.fortuna.bampo.config.properties.RestApiProperties.DETAILS_ABSTRACT_LENGTH;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 活动的搜索结果对象
 *
 * @author lhx, Eva7
 * @since 0.2.6
 */
@Data
@SuperBuilder
public class ActivityAbstract implements BaseAbstract {

    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String id;
    protected String type;
    protected String city;
    protected String title;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String founder;
    protected String status;
    protected Integer reward;
    protected Integer number;
    @NotNull
    @Length(max = DETAILS_ABSTRACT_LENGTH)
    protected String details;
    protected Integer popularity;
    @PositiveOrZero
    protected Integer volunteersCount;

    // TODO: 返回时长

    protected LocalDateTime dateEnd;
    protected LocalDateTime dateStart;
}
