package com.fortuna.bampo.controller;

import com.fortuna.bampo.entity.Team;
import com.fortuna.bampo.model.request.ModificationRequest;
import com.fortuna.bampo.model.request.RegistrationRequest;
import com.fortuna.bampo.model.request.SearchRequest;
import com.fortuna.bampo.model.request.data.TeamModification;
import com.fortuna.bampo.model.request.data.TeamRegistration;
import com.fortuna.bampo.model.request.data.TeamSearchFilter;
import com.fortuna.bampo.model.response.*;
import com.fortuna.bampo.model.response.data.TeamAbstract;
import com.fortuna.bampo.model.response.data.TeamInfo;
import com.fortuna.bampo.service.TeamService;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.TEAM_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;
import static com.fortuna.bampo.config.properties.RestApiProperties.REGISTRATION_PATH;

/**
 * 注册团队
 *
 * @author CMT, Eva7, Qing 2514
 * @since 0.3.7
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + TEAM_ENTITY_NAME)
public class TeamController {

    private final TeamService teamService;

    /**
     * 注册团队
     *
     * @param request 接收到的 HTTP 请求
     * @return 注册结果
     */
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest<TeamRegistration> request) {
        TeamRegistration teamRegistration = request.getData();
        UUID id = teamService.register(teamRegistration,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        URI location =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/team/{id}")
                        .buildAndExpand(CodecUtil.encodeUuid(id)).toUriString());
        return ResponseUtil.registrationResponseCreated(location);
    }

    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest<TeamSearchFilter> request) {
        TeamSearchFilter teamSearchFilter = request.getData();
        return ResponseUtil.searchResponseOk(teamService.search(request.getQuery(),
                request.getOrder(),
                request.getOrderBy(),
                request.getPage(),
                request.getPageSize(),
                teamSearchFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfoResponse<TeamInfo>> getInfo(@PathVariable String id,
                                                          @RequestParam(value = "page", required = false) Integer page,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResponseUtil.infoResponseOk(teamService.getInfo(id, page, pageSize));
    }

    @GetMapping("/{id}/abstract")
    public ResponseEntity<AbstractResponse<TeamAbstract>> getAbstract(@PathVariable String id,
                                                                      @RequestParam(value = "query",
                                                                              required = false) String query) {
        return ResponseUtil.abstractResponseOk(teamService.getAbstract(id, query));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> delete(@PathVariable String id) {
        return teamService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Team> update(@RequestBody ModificationRequest<TeamModification> request,
                                       @PathVariable String id) {
        TeamModification teamModification = request.getData();
        return teamService.update(id,
                teamModification.getName(),
                teamModification.getCity(),
                teamModification.getType(),
                teamModification.getDescription()) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<UpdateResponse> join(@PathVariable String id) {
        return teamService.join(id,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }

    @PostMapping("/{id}/quit")
    public ResponseEntity<UpdateResponse> quit(@PathVariable String id) {
        return teamService.quit(id,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }

    @PostMapping("/{id}/transfer")
    public ResponseEntity<UpdateResponse> transfer(@PathVariable String id, @RequestParam("username") String username) {
        return teamService.transfer(String
                .valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()), id, username)
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }
}
