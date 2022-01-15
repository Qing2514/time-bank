package com.fortuna.bampo.controller;

import com.fortuna.bampo.model.request.CreationRequest;
import com.fortuna.bampo.model.request.data.AppealCreation;
import com.fortuna.bampo.model.response.AbstractResponse;
import com.fortuna.bampo.model.response.CreationResponse;
import com.fortuna.bampo.model.response.InfoResponse;
import com.fortuna.bampo.model.response.ListResponse;
import com.fortuna.bampo.model.response.data.AppealAbstract;
import com.fortuna.bampo.model.response.data.AppealInfo;
import com.fortuna.bampo.service.AppealService;
import com.fortuna.bampo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static com.fortuna.bampo.config.properties.JpaProperties.APPEAL_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * 申诉 Rest API
 *
 * @author Eva7
 * @since 0.3.1
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + APPEAL_ENTITY_NAME)
public class AppealController {

    AppealService appealService;

    @PostMapping
    public ResponseEntity<CreationResponse> create(@RequestBody CreationRequest<AppealCreation> request) {
        AppealCreation appealCreation = request.getData();
        String id = appealService.create(appealCreation,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        URI location =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/appeal/{id}")
                        .buildAndExpand(id).toUriString());
        return ResponseUtil.creationResponseCreated(location);
    }

    @GetMapping("/list")
    public ResponseEntity<ListResponse> getList(@RequestParam(value = "page", required = false) Integer page,
                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return ResponseUtil.listResponseOk(appealService.getList(page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfoResponse<AppealInfo>> getInfo(@PathVariable String id) {
        return ResponseUtil.infoResponseOk(appealService.getInfo(id));
    }

    @GetMapping("/{id}/abstract")
    public ResponseEntity<AbstractResponse<AppealAbstract>> getAbstract(@PathVariable String id) {
        return ResponseUtil.abstractResponseOk(appealService.getAbstract(id));
    }
}
