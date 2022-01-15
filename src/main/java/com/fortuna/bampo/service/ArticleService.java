package com.fortuna.bampo.service;

import com.fortuna.bampo.model.request.data.ArticleCreation;
import com.fortuna.bampo.model.request.data.ArticleSearchFilter;
import com.fortuna.bampo.model.response.data.ArticleAbstract;
import com.fortuna.bampo.model.response.data.ArticleInfo;

import java.util.List;
import java.util.UUID;


/**
 * 文章服务接口
 *
 * @author Ttanjo, Eva7
 * @since 0.2.8
 */

public interface ArticleService {
    /**
     * 添加一篇新文章
     *
     * @param articleCreation 待添加的文章实体
     * @param author          作者
     * @return 文章 id
     */
    UUID create(ArticleCreation articleCreation, String author);

    /**
     * 根据活动基本信息搜索文章
     *
     * @param query               搜索字符串
     * @param order               排序方法
     * @param orderBy             排序依据
     * @param page                页数
     * @param pageSize            分页大小
     * @param articleSearchFilter 活动搜索筛选数据
     * @return 搜索到的活动搜索结果实体集合
     */
    List<String> search(String query, String order, String orderBy, Integer page, Integer pageSize,
                        ArticleSearchFilter articleSearchFilter);

    /**
     * 通过文章 id 获取文章信息
     *
     * @param encodedId Base64 加密的文章 id
     * @return 文章信息
     */
    ArticleInfo getInfo(String encodedId);

    /**
     * 获取文章摘要
     *
     * @param encodedId 加密的文章 id
     * @param query     搜索字符串
     * @return 文章摘要
     */
    ArticleAbstract getAbstract(String encodedId, String query);

    /**
     * 通过文章 id 修改文章
     *
     * @param encodedId 加密过的文章 id
     * @param title     文章标题
     * @param content   文章内容
     * @param source    文章来源
     * @return 是否更新成功
     */
    boolean update(String encodedId, String title, String content, String source);

    /**
     * 通过文章 id 删除文章
     *
     * @param encodedId 加密过的文章 id
     * @return 是否删除成功
     */
    boolean delete(String encodedId);
}
