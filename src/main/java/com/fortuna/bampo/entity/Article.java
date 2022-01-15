package com.fortuna.bampo.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.type.IntegerType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.ARTICLE_GENERATOR_NAME;
import static com.fortuna.bampo.config.properties.JpaProperties.GENERATOR_STRATEGY_UUID;
import static com.fortuna.bampo.config.properties.ValidationProperties.*;

/**
 * 文章实体类
 *
 * @author Ttanjo, Eva7
 * @since 0.2.4
 */
@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Article {

    /**
     * 文章 id
     */
    @Id
    @NotNull
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false, length = UUID_BINARY_LENGTH)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = ARTICLE_GENERATOR_NAME)
    @GenericGenerator(name = ARTICLE_GENERATOR_NAME, strategy = GENERATOR_STRATEGY_UUID)
    private UUID id;

    /**
     * 文章名
     */
    @NotNull
    @Column(nullable = false)
    @Length(min = TITLE_LENGTH_LOWER, max = TITLE_LENGTH_UPPER)
    private String title;

    /**
     * 文章内容
     */
    @Lob
    @NotNull
    @Column(nullable = false)
    @Length(min = CONTENT_LENGTH_LOWER, max = CONTENT_LENGTH_UPPER)
    private String content;

    /**
     * 文章来源
     */
    @NotNull
    @Column(nullable = false)
    @Length(max = SOURCE_LENGTH_UPPER)
    private String source;

    /**
     * 文章作者
     */
    @NotNull
    @Setter(AccessLevel.NONE)
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private User author;

    /**
     * 文章发表时间
     */
    @NotNull
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime dateModified = LocalDateTime.now();

    /**
     * 文章热度
     */
    @PositiveOrZero
    @Builder.Default
    @Column(nullable = false)
    private Integer popularity = IntegerType.ZERO;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
