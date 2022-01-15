package com.fortuna.bampo.controller;

import com.fortuna.bampo.entity.Article;
import com.fortuna.bampo.model.request.CreationRequest;
import com.fortuna.bampo.model.request.ModificationRequest;
import com.fortuna.bampo.model.request.SearchRequest;
import com.fortuna.bampo.model.request.data.ArticleCreation;
import com.fortuna.bampo.model.request.data.ArticleModification;
import com.fortuna.bampo.model.request.data.ArticleSearchFilter;
import com.fortuna.bampo.model.response.AbstractResponse;
import com.fortuna.bampo.model.response.InfoResponse;
import com.fortuna.bampo.model.response.SearchResponse;
import com.fortuna.bampo.model.response.data.ArticleAbstract;
import com.fortuna.bampo.model.response.data.ArticleInfo;
import com.fortuna.bampo.service.ArticleService;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.ARTICLE_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * 文章实体 RESTful API
 *
 * @author Ttanjo, Eva7
 * @since 0.2.8
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + ARTICLE_ENTITY_NAME)
public class ArticleController {

    private final ArticleService articleService;

    /**
     * 添加文章
     *
     * @param request 接收到的 HTTP 请求
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<Article> create(@RequestBody CreationRequest<ArticleCreation> request) {
        ArticleCreation articleCreation = request.getData();
        UUID id = articleService.create(articleCreation,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        URI location =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/article/{id}")
                        .buildAndExpand(CodecUtil.encodeUuid(id)).toUriString());
        return ResponseEntity.created(location).build();
    }

    /**
     * 根据文章的基本信息搜索文章摘要信息
     *
     * @param request 接收到的 HTTP 请求
     * @return 文章信息集合
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest<ArticleSearchFilter> request) {
        ArticleSearchFilter articleSearchFilter = request.getData();
        return ResponseUtil.searchResponseOk(articleService.search(request.getQuery(),
                request.getOrder(),
                request.getOrderBy(),
                request.getPage(),
                request.getPageSize(),
                articleSearchFilter));
    }

    /**
     * 通过文章 id 查询文章
     *
     * @param id 用于查询的文章 id
     * @return 通过文章 id 查询文章详情的结果
     */
    @GetMapping("/{id}")
    public ResponseEntity<InfoResponse<ArticleInfo>> getInfo(@PathVariable String id) {
        return ResponseUtil.infoResponseOk(articleService.getInfo(id));
    }

    @GetMapping("/{id}/abstract")
    public ResponseEntity<AbstractResponse<ArticleAbstract>> getAbstract(@PathVariable String id,
                                                                         @RequestParam(value = "query", required =
                                                                                 false) String query) {
        return ResponseUtil.abstractResponseOk(articleService.getAbstract(id, query));
    }

    /**
     * 修改文章
     *
     * @param request 接收到的 HTTP 请求
     * @return 通过文章 id 修改文章后的结果
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Article> modify(@RequestBody ModificationRequest<ArticleModification> request,
                                          @PathVariable String id) {
        ArticleModification articleModification = request.getData();
        return articleService.update(id,
                articleModification.getTitle(),
                articleModification.getContent(),
                articleModification.getSource()) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * 通过文章 id 删除文章
     *
     * @param id 文章id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Article> delete(@PathVariable String id) {
        return articleService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
