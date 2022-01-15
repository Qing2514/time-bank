package com.fortuna.bampo.service;

import com.fortuna.bampo.entity.Appeal;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.data.AppealCreation;
import com.fortuna.bampo.model.response.data.AppealAbstract;
import com.fortuna.bampo.model.response.data.AppealInfo;
import com.fortuna.bampo.repository.AppealRepository;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE;
import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE_SIZE;

/**
 * 申诉服务实现
 *
 * @author Eva7
 * @since 0.3.1
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AppealServiceImpl implements AppealService {

    private final UserService userService;
    private final ActivityService activityService;
    private final AppealRepository appealRepository;

    @Override
    public String create(AppealCreation appealCreation, String username) {
        if (appealRepository.existsByUserAndActivity(username, CodecUtil.decodeUuid(appealCreation.getActivityId()))) {
            throw new IllegalStateException("You have already created an appeal of this activity");
        } else {
            Appeal appeal = Appeal.builder()
                    .reason(appealCreation.getReason())
                    .user(userService.loadUserByUsername(username))
                    .compensation(appealCreation.getCompensation())
                    .activity(activityService.loadActivityById(appealCreation.getActivityId()))
                    .build();
            return CodecUtil.encodeUuid(appealRepository.saveAndFlush(appeal).getId());
        }
    }

    @Override
    public List<String> getList(Integer page, Integer pageSize) {
        List<UUID> ids = appealRepository.findListByStatus(Appeal.Status.VERIFY,
                PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                        Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE), Sort.unsorted()));
        return ids == null ? Collections.emptyList()
                : ids.stream().map(CodecUtil::encodeUuid).collect(Collectors.toList());
    }

    @Override
    public AppealAbstract getAbstract(String encodedId) {
        return appealRepository.findAbstractById(CodecUtil.decodeUuid(encodedId))
                .map(appealAbstractDTO -> AppealAbstract.builder()
                        .id(encodedId)
                        .status(appealAbstractDTO.getStatus().toString())
                        .username(appealAbstractDTO.getUser().getUsername())
                        .activityId(CodecUtil.encodeUuid(appealAbstractDTO.getActivity().getId()))
                        .reason(FormatUtil.abstractReasonByKeyword(appealAbstractDTO.getReason()))
                        .build()).orElseThrow(() -> new IllegalStateException("Appeal id not found"));
    }

    @Override
    public AppealInfo getInfo(String encodedId) {
        return appealRepository.findInfoById(CodecUtil.decodeUuid(encodedId))
                .map(appealInfoDTO -> AppealInfo.builder()
                        .id(encodedId)
                        .reason(appealInfoDTO.getReason())
                        .status(appealInfoDTO.getStatus().toString())
                        .compensation(appealInfoDTO.getCompensation())
                        .username(appealInfoDTO.getUser().getUsername())
                        .activityId(CodecUtil.encodeUuid(appealInfoDTO.getActivity().getId()))
                        .passedVerifiers(appealInfoDTO.getPassedVerifiers() == null
                                ? Collections.emptyList() : appealInfoDTO.getPassedVerifiers().stream()
                                .map(User::getUsername).collect(Collectors.toList()))
                        .rejectedVerifiers(appealInfoDTO.getRejectedVerifiers() == null
                                ? Collections.emptyList() : appealInfoDTO.getRejectedVerifiers().stream()
                                .map(User::getUsername).collect(Collectors.toList()))
                        .build()).orElseThrow(() -> new IllegalStateException("Appeal id not found"));
    }

    private boolean updateContractActivity() {
        // TODO: 修改活动的时间币分配
        return false;
    }
}
