package com.fortuna.bampo.controller;

import com.fortuna.bampo.model.request.UpdateRequest;
import com.fortuna.bampo.service.VotingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.fortuna.bampo.config.properties.JpaProperties.VOTING_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * VotingController
 *
 * @author lhx, Eva7
 * @since 0.3.1
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + VOTING_ENTITY_NAME)
public class VotingController {

    private final VotingService votingService;

    @PostMapping
    public ResponseEntity<Object> elect(@RequestBody UpdateRequest<?> request) {
        return votingService.elect(String
                        .valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
                request.getPassword()) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * 候选人投票
     *
     * @param username 候选人用户名
     * @return 是否投票成功
     */
    @PatchMapping("/{username}")
    public ResponseEntity<Object> voting(@PathVariable String username) {
        return votingService.vote(username,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getCount(@PathVariable String username) {
        return ResponseEntity.ok(votingService.getCount(username));
    }
}
