package com.fortuna.bampo.repository;

import com.fortuna.bampo.entity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * VotingRepository
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
@Repository
public interface VotingRepository extends JpaRepository<Voting, String> {
    /**
     * 根据被投者用户名获取投票信息
     *
     * @param username 候选人用户名
     * @return 查找到的投票信息
     */
    @Query("select v from Voting v left join v.elector e where e.username = ?1")
    Optional<Voting> getVotingByUsername(String username);

    /**
     * 根据被投者用户名获取票数
     *
     * @param username 候选人用户名
     * @return 查找到的票数
     */
    @Query("select size(v.voters) from Voting v left join v.elector e where e.username = ?1")
    Optional<Integer> getCountByUsername(String username);
}
