package com.fortuna.bampo.controller;

import com.fortuna.bampo.model.request.CreationRequest;
import com.fortuna.bampo.model.request.SearchRequest;
import com.fortuna.bampo.model.request.UpdateRequest;
import com.fortuna.bampo.model.request.data.ActivityCreation;
import com.fortuna.bampo.model.request.data.ActivitySearchFilter;
import com.fortuna.bampo.model.request.data.ActivityUpdate;
import com.fortuna.bampo.model.response.*;
import com.fortuna.bampo.model.response.data.ActivityAbstract;
import com.fortuna.bampo.model.response.data.ActivityInfo;
import com.fortuna.bampo.service.ActivityService;
import com.fortuna.bampo.service.UserService;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static com.fortuna.bampo.config.properties.JpaProperties.ACTIVITY_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * 活动实体 RESTful API
 *
 * @author Qing2514, lhx
 * @since 0.3.3
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + ACTIVITY_ENTITY_NAME)
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    /**
     * 创建活动
     *
     * @param request 接收到的 HTTP 请求
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<CreationResponse> create(@RequestBody CreationRequest<ActivityCreation> request) {
        ActivityCreation activityRegistration = request.getData();
        UUID id = activityService.create(activityRegistration,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        URI location =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/activity/{id}")
                        .buildAndExpand(CodecUtil.encodeUuid(id)).toUriString());
        return ResponseUtil.creationResponseCreated(location);
    }

    /**
     * 根据活动的基本信息搜索活动摘要信息
     *
     * @param request 接收到的 HTTP 请求
     * @return 活动信息集合
     */
    @PostMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest<ActivitySearchFilter> request) {
        ActivitySearchFilter activitySearchFilter = request.getData();
        return ResponseUtil.searchResponseOk(activityService.search(request.getQuery(),
                request.getOrder(),
                request.getOrderBy(),
                request.getPage(),
                request.getPageSize(),
                activitySearchFilter));
    }

    /**
     * 通过活动 id 查询活动
     *
     * @param id 用于查询的活动 id
     * @return 通过活动 id 查询活动详情的结果
     */
    @GetMapping("/{id}")
    public ResponseEntity<InfoResponse<ActivityInfo>> getInfo(@PathVariable String id,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResponseUtil.infoResponseOk(activityService.getInfo(id, page, pageSize));
    }

    @GetMapping("/{id}/abstract")
    public ResponseEntity<AbstractResponse<ActivityAbstract>> getAbstract(@PathVariable String id,
                                                                          @RequestParam(value = "query", required =
                                                                                  false) String query) {
        return ResponseUtil.abstractResponseOk(activityService.getAbstract(id, query));
    }

    /**
     * 参与活动
     *
     * @param id 接收到的活动 id
     * @return 创建结果
     */
    @PostMapping("/{id}/participation")
    public ResponseEntity<UpdateResponse> participate(@PathVariable String id) {
        return activityService
                .participate(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()), id)
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }

    /**
     * 修改活动信息
     *
     * @param request 接收到的 HTTP 请求
     * @return 修改结果
     */
    @PatchMapping("/{id}/update")
    public ResponseEntity<UpdateResponse> update(@RequestBody UpdateRequest<ActivityUpdate> request,
                                                 @PathVariable String id) {
        ActivityUpdate activityUpdate = request.getData();
        return activityService.update(id, activityUpdate.getAddition(), activityUpdate.getRejectedVolunteers())
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }

    @PostMapping("/{id}/recruit")
    public ResponseEntity<UpdateResponse> recruit(@PathVariable String id) {
        return activityService.recruit(id,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                ? ResponseUtil.updateResponseOk() : ResponseUtil.updateResponseBadRequest();
    }

    /**
     * 删除活动信息
     *
     * @param id 接收到的活动 id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return activityService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    /**
     * 审核活动
     *
     * @param id     活动id
     * @param result 是否投票通过
     * @return 审核完成的活动
     */
    @PostMapping("/{id}/verify")
    public ResponseEntity<Object> verify(@PathVariable String id, @RequestParam("result") boolean result) {
        return activityService.verify(id,
                userService.loadUserByUsername(
                        String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal())), result)
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
