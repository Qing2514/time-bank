package com.fortuna.bampo.model.response.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static com.fortuna.bampo.config.properties.RestApiProperties.CONTENT_ABSTRACT_LENGTH;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 文章的搜索结果对象
 *
 * @author Ttanjo, Eva7
 * @since 0.2.4
 */
@Data
@SuperBuilder
public class ArticleAbstract implements BaseAbstract {

    @NotNull
    @Length(min = BASE64_ENCODED_UUID_LENGTH, max = BASE64_ENCODED_UUID_LENGTH)
    protected String id;
    protected String title;
    @NotNull
    @Pattern(regexp = NAME_REGEX)
    @Length(min = NAME_LENGTH_LOWER, max = NAME_LENGTH_UPPER)
    protected String author;
    protected String source;
    @NotNull
    @Length(max = CONTENT_ABSTRACT_LENGTH)
    protected String content;
    protected LocalDateTime dateModified;
}
