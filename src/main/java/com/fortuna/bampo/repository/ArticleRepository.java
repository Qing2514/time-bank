package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.ArticleAbstractDTO;
import com.fortuna.bampo.dto.ArticleInfoDTO;
import com.fortuna.bampo.entity.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 文章数据库接口
 *
 * @author Ttanjo, Eva7
 * @since 0.3.4
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    /**
     * 根据信息搜索文章摘要，时间介于上下限之间
     *
     * @param query           搜索字符串
     * @param source          文章来源
     * @param author          文章作者
     * @param createDateLower 文章发表时间下限
     * @param createDateUpper 文章发表时间上限
     * @param pageable        分页信息
     * @return 返回搜索到的摘要的集合
     */
    @Query("select a.id from Article a left join a.author f where " +
            "(a.title like concat('%', ?1, '%') or a.content like concat('%', ?1, '%')) and " +
            "a.source like concat('%', ?2, '%') and " +
            "f.username like concat('%', ?3, '%') " +
            "order by a.popularity desc, size(f.followers) desc, a.dateModified desc")
    List<UUID> findPageByTitleAndContentContaining(String query,
                                                   String source,
                                                   String author,
                                                   LocalDateTime createDateLower,
                                                   LocalDateTime createDateUpper,
                                                   Pageable pageable);

    /**
     * 更新热度
     *
     * @param id 文章 id
     * @return 是否更新成功
     */
    @Modifying
    @Query("update Article a set a.popularity = a.popularity + 1 where a.id = ?1")
    int updatePopularity(UUID id);

    /**
     * 根据文章 id 获取文章信息
     *
     * @param id 文章 id
     * @return 文章信息
     */
    @Query("select a from Article a where a.id = ?1")
    Optional<ArticleInfoDTO> findInfoById(UUID id);

    /**
     * 根据文章 id 获取文章摘要
     *
     * @param id 文章 id
     * @return 文章摘要
     */
    @Query("select a from Article a where a.id = ?1")
    Optional<ArticleAbstractDTO> findAbstractById(UUID id);

    /**
     * 根据 id 删除文章
     *
     * @param id 文章 id
     * @return 删除的条数
     */
    @Modifying
    int deleteArticleById(UUID id);
}
