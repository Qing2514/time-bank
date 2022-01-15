package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.ActivityAbstractDTO;
import com.fortuna.bampo.dto.ActivityInfoDTO;
import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.Activity.Status;
import com.fortuna.bampo.entity.Activity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 活动数据库接口
 *
 * @author Qing2514, Eva7, lhx
 * @since 0.3.4
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    /**
     * 根据信息搜索活动摘要
     *
     * @param query       搜索字符串
     * @param city        城市
     * @param type        活动类型
     * @param founder     发起人用户名（模糊）
     * @param status      状态集合
     * @param rewardLower 时间币下限
     * @param rewardUpper 时间币上限
     * @param numberLower 人数上限
     * @param numberUpper 人数下限
     * @param pageable    分页信息
     * @return 返回搜索到的摘要的集合
     */
    @Query("select a.id from Activity a left join a.founder f where " +
            "(a.title like concat('%', ?1, '%') or a.details like concat('%', ?1, '%')) and " +
            "a.city like concat('%', ?2, '%') and " +
            "(a.type = ?3 or ?3 is null) and " +
            "f.username like concat('%', ?4, '%') and " +
            "(a.status in(?5) or ?5 is null) and " +
            "a.reward >= ?6 and a.reward <= ?7 and " +
            "a.number >= ?8 and a.number <= ?9 " +
            "order by a.popularity desc, size(f.followers) desc, a.number desc, a.datePublished desc, a.reward desc")
    List<UUID> findPageByTitleAndDetailsContaining(String query,
                                                   String city,
                                                   Type type,
                                                   String founder,
                                                   List<Status> status,
                                                   Integer rewardLower, Integer rewardUpper,
                                                   Integer numberLower, Integer numberUpper,
                                                   Pageable pageable);

    /**
     * 获取活动信息
     *
     * @param id 活动 id
     * @return 活动信息
     */
    @Query("select a from Activity a where a.id = ?1")
    Optional<ActivityInfoDTO> findInfoById(UUID id);

    /**
     * 更新热度
     *
     * @param id 活动 id
     * @return 是否更新成功
     */
    @Modifying
    @Query("update Activity a set a.popularity = a.popularity + 1 where a.id = ?1")
    int updatePopularity(UUID id);

    /**
     * 获取活动摘要
     *
     * @param id 活动 id
     * @return 活动摘要
     */
    @Query("select a from Activity a where a.id = ?1")
    Optional<ActivityAbstractDTO> findAbstractById(UUID id);

    /**
     * 通过活动 id 删除活动
     *
     * @param id         活动 id
     * @param statusList 活动在招募中
     * @return 删除条数
     */
    @Modifying
    @Query("delete from Activity a where a.id = ?1 and a.status in(?2)")
    int deleteActivityById(UUID id, List<Status> statusList);
}
