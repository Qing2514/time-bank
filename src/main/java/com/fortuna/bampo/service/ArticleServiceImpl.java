package com.fortuna.bampo.service;

import ch.qos.logback.core.CoreConstants;
import com.fortuna.bampo.entity.Article;
import com.fortuna.bampo.model.request.data.ArticleCreation;
import com.fortuna.bampo.model.request.data.ArticleSearchFilter;
import com.fortuna.bampo.model.response.data.ArticleAbstract;
import com.fortuna.bampo.model.response.data.ArticleInfo;
import com.fortuna.bampo.repository.ArticleRepository;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.IntegerType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE;
import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE_SIZE;

/**
 * 文章服务实现
 *
 * @author Ttanjo, Eva7
 * @since 0.2.8
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Override
    public UUID create(ArticleCreation articleCreation, String author) {
        Article article = Article.builder()
                .title(articleCreation.getTitle())
                .content(articleCreation.getContent())
                .source(articleCreation.getSource())
                .author(userService.loadUserByUsername(author))
                .build();
        return articleRepository.saveAndFlush(article).getId();
    }

    @Override
    public List<String> search(String query, String order, String orderBy, Integer page, Integer pageSize,
                               ArticleSearchFilter articleSearchFilter) {
        List<UUID> articleAbstracts =
                articleRepository.findPageByTitleAndContentContaining(FormatUtil.escapeAndFormatQuery(query),
                        Objects.requireNonNullElse(articleSearchFilter.getSource(), CoreConstants.EMPTY_STRING),
                        Objects.requireNonNullElse(articleSearchFilter.getAuthor(), CoreConstants.EMPTY_STRING),
                        Objects.requireNonNullElse(articleSearchFilter.getDateModifiedLower(), LocalDateTime.MIN),
                        Objects.requireNonNullElse(articleSearchFilter.getDateModifiedUpper(), LocalDateTime.MAX),
                        PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                                Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE),
                                Objects.isNull(orderBy) ? Sort.unsorted()
                                        : Sort.by(Sort.Direction.fromOptionalString(order)
                                                .orElse(Sort.Direction.ASC), orderBy)));
        return articleAbstracts.isEmpty() ? Collections.emptyList()
                : articleAbstracts.stream().map(CodecUtil::encodeUuid).collect(Collectors.toList());
    }

    @Override
    public ArticleInfo getInfo(String encodedId) {
        if (articleRepository.updatePopularity(CodecUtil.decodeUuid(encodedId)) == 0) {
            throw new IllegalStateException("Failed to update popularity");
        }
        return articleRepository.findInfoById(CodecUtil.decodeUuid(encodedId))
                .map(articleInfoDTO -> ArticleInfo.builder()
                        .title(articleInfoDTO.getTitle())
                        .author(articleInfoDTO.getAuthor().getUsername())
                        .source(articleInfoDTO.getSource())
                        .content(articleInfoDTO.getContent())
                        .dateModified(articleInfoDTO.getDateModified())
                        .id(CodecUtil.encodeUuid(articleInfoDTO.getId()))
                        .build()).orElseThrow(() -> new IllegalStateException("Article id not found"));
    }

    @Override
    public ArticleAbstract getAbstract(String encodedId, String query) {
        return articleRepository.findAbstractById(CodecUtil.decodeUuid(encodedId))
                .map(articleAbstractDTO -> ArticleAbstract.builder()
                        .id(CodecUtil.encodeUuid(articleAbstractDTO.getId()))
                        .title(articleAbstractDTO.getTitle())
                        .author(articleAbstractDTO.getAuthor().getUsername())
                        .source(articleAbstractDTO.getSource())
                        .content(FormatUtil.abstractContentByKeyword(articleAbstractDTO.getContent(),
                                Objects.requireNonNullElse(query, CoreConstants.EMPTY_STRING)))
                        .dateModified(articleAbstractDTO.getDateModified())
                        .build()).orElseThrow(() -> new IllegalStateException("Article id not found"));
    }

    @Override
    public boolean update(String encodedId, String title, String content, String source) {
        Article article = articleRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    a.setTitle(Objects.requireNonNullElse(title, a.getTitle()));
                    a.setContent(Objects.requireNonNullElse(content, a.getContent()));
                    a.setSource(Objects.requireNonNullElse(source, a.getSource()));
                    return a;
                }).orElseThrow(() -> new IllegalStateException("Article id not found"));
        return articleRepository.saveAndFlush(article) == article;
    }

    @Override
    public boolean delete(String encodedId) {
        return articleRepository.deleteArticleById(CodecUtil.decodeUuid(encodedId)) != IntegerType.ZERO;
    }
}
