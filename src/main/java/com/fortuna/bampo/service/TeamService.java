package com.fortuna.bampo.service;


import com.fortuna.bampo.model.request.data.TeamRegistration;
import com.fortuna.bampo.model.request.data.TeamSearchFilter;
import com.fortuna.bampo.model.response.data.TeamAbstract;
import com.fortuna.bampo.model.response.data.TeamInfo;

import java.util.List;
import java.util.UUID;

/**
 * 团队服务接口
 *
 * @author CMT, Eva7, Qing 2514
 * @since 0.2.4
 */
public interface TeamService {

    /**
     * 注册一个新团队
     *
     * @param teamRegistration 待注册的团队
     * @param username         创建者用户名
     * @return 团队 id
     */
    UUID register(TeamRegistration teamRegistration, String username);

    /**
     * 模糊搜索团队信息
     *
     * @param query            团队名称
     * @param order            排序方法
     * @param orderBy          排序依据
     * @param page             页数
     * @param pageSize         分页大小
     * @param teamSearchFilter 团队搜索筛选数据
     * @return 匹配该团队信息含有输入字段的团队 id
     */
    List<String> search(String query, String order, String orderBy, Integer page, Integer pageSize,
                        TeamSearchFilter teamSearchFilter);

    /**
     * 通过团队 id 获取团队信息
     *
     * @param encodedId base64 加密 id
     * @param page      页数
     * @param pageSize  分页大小
     * @return 匹配该团队名称的团队
     */
    TeamInfo getInfo(String encodedId, Integer page, Integer pageSize);

    /**
     * 获取团队摘要
     *
     * @param encodedId 加密的团队 id
     * @param query     查询字符串
     * @return 团队摘要
     */
    TeamAbstract getAbstract(String encodedId, String query);

    /**
     * 通过团队 id 删除团队信息
     *
     * @param name 团队名称
     * @return 是否删除成功
     */
    boolean delete(String name);

    /**
     * 修改团队基本信息
     *
     * @param encodedId   加密的团队 id
     * @param name        团队名称
     * @param city        城市
     * @param type        服务类型
     * @param description 团队简介
     * @return 修改后的团队信息
     */
    boolean update(String encodedId, String name, String city, String type, String description);

    /**
     * 加入团队
     *
     * @param encodedId 加密的团队 id
     * @param username  用户名
     * @return 是否成功
     */
    boolean join(String encodedId, String username);

    /**
     * 团队成员退出团队
     *
     * @param encodedId 加密的团队 id
     * @param username  用户名
     * @return 是否成功
     */
    boolean quit(String encodedId, String username);

    /**
     * 转让团队
     *
     * @param founder    创建者
     * @param encodedId  团队 id
     * @param newFounder 新的团队创建者
     * @return 是否转让成功
     */
    boolean transfer(String founder, String encodedId, String newFounder);
}
