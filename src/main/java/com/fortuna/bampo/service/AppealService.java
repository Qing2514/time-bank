package com.fortuna.bampo.service;

import com.fortuna.bampo.model.request.data.AppealCreation;
import com.fortuna.bampo.model.response.data.AppealAbstract;
import com.fortuna.bampo.model.response.data.AppealInfo;

import java.util.List;

/**
 * 申诉服务接口
 *
 * @author Eva7
 * @since 0.3.1
 */
public interface AppealService {

    /**
     * 创建申诉
     *
     * @param appealCreation 创建申诉的信息
     * @param username       申诉者用户名
     * @return 申诉 id
     */
    String create(AppealCreation appealCreation, String username);

    /**
     * 获取申诉列表
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @return 申诉 id 列表
     */
    List<String> getList(Integer page, Integer pageSize);

    /**
     * 获取申诉摘要
     *
     * @param encodedId 加密的申诉 id
     * @return 申诉摘要
     */
    AppealAbstract getAbstract(String encodedId);

    /**
     * 获取申诉详情
     *
     * @param encodedId 加密的申诉 id
     * @return 申诉详情
     */
    AppealInfo getInfo(String encodedId);
}
