package com.fortuna.bampo.repository;

import com.fortuna.bampo.dto.TeamAbstractDTO;
import com.fortuna.bampo.dto.TeamInfoDTO;
import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * 团队数据库接口
 *
 * @author CMT, Eva7
 * @since 0.3.4
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    // TODO: Nullable

    /**
     * 通过团队名查询团队，返回一个 {@code Optional} 描述该用户，若查询结果为空则返回一个空的 {@code Optional}
     *
     * @param name 用于搜索的团队名
     * @return 返回一个 {@code Optional} 包含搜索到的团队，若查询结果为空则返回一个空的 {@code Optional}
     * @throws IllegalArgumentException 当传入的团队名为空时抛出
     */
    Optional<Team> findByName(String name);

    /**
     * 通过关键字查询团队列表
     *
     * @param query       查询字符串
     * @param city        城市
     * @param founder     队长用户名（模糊）
     * @param type        服务类型
     * @param numberLower 人数下限
     * @param numberUpper 人数上限
     * @param pageable    分页信息
     * @return 返回团队摘要
     */
    @Query("select t.id from Team t left join t.founder f where " +
            "(t.name like concat('%', ?1, '%') or t.description like concat('%', ?1, '%')) and " +
            "t.city like concat('%', ?2, '%') and " +
            "f.username like concat('%', ?3, '%') and " +
            "(t.type = ?4 or ?4 is null) and " +
            "size(t.members) >= ?5 and size(t.members) <= ?6 " +
            "order by size(f.followers) desc, size(t.members) desc")
    List<UUID> findPageByNameAndDescriptionContaining(String query,
                                                      String city,
                                                      String founder,
                                                      Activity.Type type,
                                                      Integer numberLower, Integer numberUpper,
                                                      Pageable pageable);

    /**
     * 查看团队信息
     *
     * @param id 团队 id
     * @return 团队信息
     */
    @Query("select t from Team t where t.id = ?1")
    Optional<TeamInfoDTO> findInfoById(UUID id);

    /**
     * 查看团队摘要
     *
     * @param id 团队 id
     * @return 团队摘要
     */
    @Query("select t from Team t where t.id = ?1")
    Optional<TeamAbstractDTO> findAbstractById(UUID id);

    /**
     * 通过团队 id 删除团队信息
     *
     * @param id 团队 id
     * @return 删除的条数
     */
    @Modifying
    int deleteTeamById(UUID id);
}
