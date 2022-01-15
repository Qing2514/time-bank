package com.fortuna.bampo.service;

/**
 * 投票服务
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
public interface VotingService {

    /**
     * 参与候选
     *
     * @param username 候选人用户名
     * @param password 密码
     * @return 是否成功
     */
    boolean elect(String username, String password);

    /**
     * 用户对候选人成为审核人进行投票
     *
     * @param elector 被投者用户名
     * @param voter   投票者用户名
     * @return 是否成功
     */
    boolean vote(String elector, String voter);

    /**
     * 获取候选人票数
     *
     * @param username 候选人用户名
     * @return 票数
     */
    Integer getCount(String username);
}
