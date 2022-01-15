package com.fortuna.bampo.service;

import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.data.ActivityCreation;
import com.fortuna.bampo.model.request.data.ActivitySearchFilter;
import com.fortuna.bampo.model.response.data.ActivityAbstract;
import com.fortuna.bampo.model.response.data.ActivityInfo;

import java.util.List;
import java.util.UUID;

/**
 * 活动服务接口
 *
 * @author Qing2514, Eva7, lhx
 * @since 0.3.3
 */
public interface ActivityService {

    /**
     * 创建一个新活动
     *
     * @param activity 待创建的活动实体
     * @param username 发起人用户名
     * @return 创建后的活动实体 id
     */
    UUID create(ActivityCreation activity, String username);

    /**
     * 根据活动基本信息搜索活动
     *
     * @param query                搜索字符串
     * @param order                排序方法
     * @param orderBy              排序依据
     * @param page                 页数
     * @param pageSize             分页大小
     * @param activitySearchFilter 活动搜索筛选数据
     * @return 搜索到的活动 id 集合
     */
    List<String> search(String query,
                        String order,
                        String orderBy,
                        Integer page,
                        Integer pageSize,
                        ActivitySearchFilter activitySearchFilter);

    /**
     * 获取活动信息
     *
     * @param encodedId 加密的活动 id
     * @param page      页数
     * @param pageSize  分页大小
     * @return 活动信息
     */
    ActivityInfo getInfo(String encodedId, Integer page, Integer pageSize);

    /**
     * 获取活动摘要
     *
     * @param encodedId 加密的活动 id
     * @param query     搜索字符串
     * @return 活动摘要
     */
    ActivityAbstract getAbstract(String encodedId, String query);

    /**
     * 用户报名活动
     *
     * @param username  报名活动的用户名称
     * @param encodedId Base64 加密的活动 id
     * @return 修改后的活动实体
     */
    boolean participate(String username, String encodedId);

    /**
     * 修改活动信息
     *
     * @param encodedId          加密的活动 id
     * @param addition           补充说明
     * @param rejectedVolunteers 发起人拒绝的志愿者
     * @return 是否修改成功
     */
    boolean update(String encodedId, String addition, List<String> rejectedVolunteers);

    /**
     * 结束招募
     *
     * @param encodedId 加密的活动 id
     * @param username  发起人用户名
     * @return 发起人结束招募
     */
    boolean recruit(String encodedId, String username);

    /**
     * 提交申报
     *
     * @param encodedId 加密的 id
     * @param username  发起人用户名
     * @param reward    活动报酬
     * @param declare   申报信息
     * @return 发起人提交申报
     */
    boolean declare(String encodedId, String username, Integer reward, String declare);

    /**
     * 结束公示
     *
     * @param encodedId 加密的 id
     * @param username  发起人用户名
     * @return 发起人结束公示
     * @throws Exception 网络异常/活动不存在/用户不存在
     */
    boolean finish(String encodedId, String username) throws Exception;

    /**
     * 删除活动信息
     *
     * @param encodedId 用于删除活动的活动 id
     * @return 是否删除成功
     */
    boolean delete(String encodedId);

    /**
     * 通过活动 id 获取活动
     *
     * @param encodedId Base64 加密的活动 id
     * @return 匹配该活动 id 的活动
     */
    Activity loadActivityById(String encodedId);

    /**
     * 审核发起活动
     *
     * @param encodedId 活动id
     * @param user      审核人
     * @param result    是否通过
     * @return 投票是否成功
     */
    boolean verify(String encodedId, User user, boolean result);
}
