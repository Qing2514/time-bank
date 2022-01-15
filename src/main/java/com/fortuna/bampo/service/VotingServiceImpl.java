package com.fortuna.bampo.service;

import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.entity.Voting;
import com.fortuna.bampo.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 投票服务实现
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class VotingServiceImpl implements VotingService {

    private final UserService userService;
    private final VotingRepository votingRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean elect(String username, String password) {
        // TODO: credit prerequisite

        // Refactor
        votingRepository.getVotingByUsername(username).ifPresent(voting -> {
            throw new IllegalStateException("You are already a elector");
        });
        User user = userService.loadUserByUsername(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            Voting voting = Voting.builder()
                    .electing(true).elector(userService.loadUserByUsername(username)).build();
            return votingRepository.saveAndFlush(voting) == voting;
        } else {
            throw new IllegalStateException("Password incorrect!");
        }
    }

    @Override
    public boolean vote(String voter, String elector) {
        User voterUser = userService.loadUserByUsername(voter);
        Voting voting = votingRepository.getVotingByUsername(voter)
                .filter(v -> v.isElecting() && !v.getVoters().contains(voterUser)
                        && v.getElector() != voterUser)
                .map(v -> {
                    if (v.getVoters().add(voterUser)) {
                        // TODO: Reset if elected
                        return v;
                    } else {
                        throw new IllegalStateException("Failed to vote");
                    }
                }).orElseThrow(() -> new IllegalStateException("Unable to vote"));
        return votingRepository.saveAndFlush(voting) == voting;
    }

    @Override
    public Integer getCount(String username) {
        return votingRepository.getCountByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Voting information not found"));
    }
}
