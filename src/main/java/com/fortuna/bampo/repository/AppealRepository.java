package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.AppealAbstractDTO;
import com.fortuna.bampo.dto.AppealInfoDTO;
import com.fortuna.bampo.entity.Appeal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 申诉数据库接口
 *
 * @author Eva7
 * @since 0.3.1
 */
public interface AppealRepository extends JpaRepository<Appeal, UUID> {

    /**
     * 获取申诉列表
     *
     * @param status   申诉状态
     * @param pageable 分页信息
     * @return 申诉 id 列表
     */
    @Query("select a.id from Appeal a where a.status = ?1 or ?1 is null " +
            "order by a.dateCreated desc, a.compensation desc, size(a.passedVerifiers) asc")
    List<UUID> findListByStatus(Appeal.Status status, Pageable pageable);

    /**
     * 获取申诉摘要
     *
     * @param id 申诉 id
     * @return 申诉摘要
     */
    @Query("select a from Appeal a where a.id = ?1")
    Optional<AppealAbstractDTO> findAbstractById(UUID id);

    /**
     * 获取申诉信息
     *
     * @param id 申诉 id
     * @return 申诉信息
     */
    @Query("select a from Appeal a where a.id = ?1")
    Optional<AppealInfoDTO> findInfoById(UUID id);

    /**
     * 是否存在匹配用户和活动的申诉
     *
     * @param username   用户名
     * @param activityId 活动 id
     * @return 是否存在匹配用户和活动的申诉
     */
    @Query("select (count(a) > 0) from Appeal a left join a.user u left join a.activity t where " +
            "u.username = ?1 and t.id = ?2")
    boolean existsByUserAndActivity(String username, UUID activityId);
}
