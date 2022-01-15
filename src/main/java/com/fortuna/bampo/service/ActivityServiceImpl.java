package com.fortuna.bampo.service;

import ch.qos.logback.core.CoreConstants;
import com.fortuna.bampo.contract.Contract;
import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.Activity.Status;
import com.fortuna.bampo.entity.Activity.Type;
import com.fortuna.bampo.entity.Transaction;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.data.ActivityCreation;
import com.fortuna.bampo.model.request.data.ActivitySearchFilter;
import com.fortuna.bampo.model.request.data.TransactionCreation;
import com.fortuna.bampo.model.response.data.ActivityAbstract;
import com.fortuna.bampo.model.response.data.ActivityInfo;
import com.fortuna.bampo.repository.ActivityRepository;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.FormatUtil;
import com.fortuna.bampo.util.VerifyUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.IntegerType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.RestApiProperties.*;

/**
 * 活动服务实现
 *
 * @author Qing2514, Eva7, lhx
 * @since 0.3.3
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ActivityServiceImpl implements ActivityService {

    private final UserService userService;
    private final TransactionService transactionService;
    private final ActivityRepository activityRepository;

    @Override
    public UUID create(ActivityCreation activityRegistration, String username) {
        if (activityRegistration.getDateEnd().compareTo(activityRegistration.getDateStart()) > IntegerType.ZERO) {
            Activity activity = Activity.builder()
                    .status(Activity.Status.RECRUIT)
                    .datePublished(LocalDateTime.now())
                    .city(activityRegistration.getCity())
                    .title(activityRegistration.getTitle())
                    .number(activityRegistration.getNumber())
                    .reward(activityRegistration.getReward())
                    .address(activityRegistration.getAddress())
                    .details(activityRegistration.getDetails())
                    .dateEnd(activityRegistration.getDateEnd())
                    .dateStart(activityRegistration.getDateStart())
                    .founder(userService.loadUserByUsername(username))
                    .type(Type.fromString(activityRegistration.getType()))
                    .build();
            return activityRepository.saveAndFlush(activity).getId();
        } else {
            throw new IllegalStateException("Start time must before ending time");
        }
    }

    @Override
    public List<String> search(String query, String order, String orderBy, Integer page, Integer pageSize,
                               ActivitySearchFilter activitySearchFilter) {
        List<UUID> activityAbstracts =
                activityRepository.findPageByTitleAndDetailsContaining(FormatUtil.escapeAndFormatQuery(query),
                        Objects.requireNonNullElse(activitySearchFilter.getCity(), CoreConstants.EMPTY_STRING),
                        Type.fromString(activitySearchFilter.getType()),
                        Objects.requireNonNullElse(activitySearchFilter.getFounder(), CoreConstants.EMPTY_STRING),
                        activitySearchFilter.getStatusList() == null ? null
                                : activitySearchFilter.getStatusList().stream()
                                        .map(Status::fromString).collect(Collectors.toList()),
                        Objects.requireNonNullElse(activitySearchFilter.getRewardLower(), IntegerType.ZERO),
                        Objects.requireNonNullElse(activitySearchFilter.getRewardUpper(), Integer.MAX_VALUE),
                        Objects.requireNonNullElse(activitySearchFilter.getNumberLower(), IntegerType.ZERO),
                        Objects.requireNonNullElse(activitySearchFilter.getNumberUpper(), Integer.MAX_VALUE),
                        PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                                Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE),
                                Objects.isNull(orderBy) ? Sort.unsorted()
                                        : Sort.by(Sort.Direction.fromOptionalString(order)
                                                .orElse(Sort.Direction.ASC), orderBy)));
        return activityAbstracts.isEmpty() ? Collections.emptyList()
                : activityAbstracts.stream().map(CodecUtil::encodeUuid).collect(Collectors.toList());
    }

    @Override
    public ActivityInfo getInfo(String encodedId, Integer page, Integer pageSize) {
        if (activityRepository.updatePopularity(CodecUtil.decodeUuid(encodedId)) == 0) {
            throw new IllegalStateException("Failed to update popularity");
        }
        Integer finalPage = Objects.requireNonNullElse(page, DEFAULT_PAGE);
        Integer finalPageSize = Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE);
        return activityRepository.findInfoById(CodecUtil.decodeUuid(encodedId))
                .map(activityInfoDTO -> ActivityInfo.builder()
                        .city(activityInfoDTO.getCity())
                        .title(activityInfoDTO.getTitle())
                        .reward(activityInfoDTO.getReward())
                        .number(activityInfoDTO.getNumber())
                        .address(activityInfoDTO.getAddress())
                        .details(activityInfoDTO.getDetails())
                        .dateEnd(activityInfoDTO.getDateEnd())
                        .dateStart(activityInfoDTO.getDateStart())
                        .type(activityInfoDTO.getType().toString())
                        .popularity(activityInfoDTO.getPopularity())
                        .status(activityInfoDTO.getStatus().toString())
                        .id(CodecUtil.encodeUuid(activityInfoDTO.getId()))
                        .datePublished(activityInfoDTO.getDatePublished())
                        .founder(activityInfoDTO.getFounder().getUsername())
                        .volunteersCount(activityInfoDTO.getVolunteers().size())
                        .volunteers(activityInfoDTO.getVolunteers() == null
                                ? Collections.emptyList()
                                : activityInfoDTO.getVolunteers().stream()
                                        .map(User::getUsername)
                                        .collect(Collectors.toList())
                                        .subList(finalPage * finalPageSize,
                                                Math.min(finalPage * finalPageSize + finalPageSize,
                                                        activityInfoDTO.getVolunteers().size())))
                        .build()).orElseThrow(() -> new IllegalStateException("Activity id not found"));
    }

    @Override
    public ActivityAbstract getAbstract(String encodedId, String query) {
        return activityRepository.findAbstractById(CodecUtil.decodeUuid(encodedId))
                .map(activityAbstractDTO -> ActivityAbstract.builder()
                        .city(activityAbstractDTO.getCity())
                        .title(activityAbstractDTO.getTitle())
                        .reward(activityAbstractDTO.getReward())
                        .number(activityAbstractDTO.getNumber())
                        .dateEnd(activityAbstractDTO.getDateEnd())
                        .dateStart(activityAbstractDTO.getDateStart())
                        .type(activityAbstractDTO.getType().toString())
                        .popularity(activityAbstractDTO.getPopularity())
                        .status(activityAbstractDTO.getStatus().toString())
                        .id(CodecUtil.encodeUuid(activityAbstractDTO.getId()))
                        .founder(activityAbstractDTO.getFounder().getUsername())
                        .volunteersCount(activityAbstractDTO.getVolunteers().size())
                        .details(FormatUtil.abstractDetailsByKeyword(activityAbstractDTO.getDetails(),
                                Objects.requireNonNullElse(query, CoreConstants.EMPTY_STRING)))
                        .build()).orElseThrow(() -> new IllegalStateException("Activity id not found"));
    }

    @Override
    public boolean participate(String username, String encodedId) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .filter(a -> a.getStatus() == Status.RECRUIT)
                .map(a -> {
                    if (a.getVolunteers().add(userService.loadUserByUsername(username))) {
                        return a;
                    } else {
                        throw new IllegalStateException("Failed to participate the activity");
                    }
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public boolean update(String encodedId, String addition, List<String> rejectedVolunteers) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .filter(a -> a.getStatus() == Status.RECRUIT)
                .map(a -> {
                    String additionContent = ACTIVITY_UPDATE_TEMPLATE.replace(ACTIVITY_UPDATE_TIME_PATTERN,
                            LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                    a.setDetails(a.getDetails().concat(additionContent).concat(Objects.requireNonNull(addition)));
                    if (rejectedVolunteers != null) {
                        a.getVolunteers().removeAll(rejectedVolunteers.stream()
                                .map(userService::loadUserByUsername).collect(Collectors.toSet()));
                    }
                    return a;
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public boolean recruit(String encodedId, String username) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    if (a.getStatus() == Status.RECRUIT) {
                        a.setStatus(Status.PROGRESS);
                        return a;
                    } else {
                        throw new IllegalStateException("Failed to progress activity");
                    }
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public boolean declare(String encodedId, String username, Integer reward, String declare) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    if (a.getStatus() == Status.PROGRESS && a.getFounder().getUsername().equals(username)) {
                        a.setStatus(Status.APPEAL);
                        String declareContent = ACTIVITY_DECLARE_TEMPLATE.replace(ACTIVITY_UPDATE_TIME_PATTERN,
                                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
                        a.setDetails(a.getDetails().concat(declareContent).concat(Objects.requireNonNull(declare)));
                        // TODO: Map(Username, reward) 一个志愿者对应一个报酬
                        a.setDatePublished(LocalDateTime.now());
                        return a;
                    } else {
                        throw new IllegalStateException("Failed to appeal activity");
                    }
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public boolean finish(String encodedId, String username) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    boolean noticeTime = LocalDateTime.now().isAfter(a.getDatePublished().plusDays(1));
                    if (a.getStatus() == Status.APPEAL && noticeTime) {
                        a.setStatus(Status.FINISH);
                        Set<Transaction> transactionList = Objects.requireNonNullElse(a.getTransactions(),
                                new LinkedHashSet<>());
                        transactionList.forEach(transaction -> transactionService
                                .modify(CodecUtil.encodeUuid(transaction.getId()), null, true));
                        try {
                            Contract.finishActivity(a.getId().toString(),
                                    transactionList.stream().map(transaction -> transaction.getId().toString())
                                            .collect(Collectors.toList()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new IllegalStateException("Failed to finish activity");
                        }
                        return a;
                    } else {
                        throw new IllegalStateException("Failed to finish activity");
                    }
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public boolean delete(String encodedId) {
        List<Status> statusList = new ArrayList<>(Collections.singletonList(Status.RECRUIT));
        statusList.add(Status.VERIFY);
        return activityRepository.deleteActivityById(CodecUtil.decodeUuid(encodedId), statusList) != 0;
    }

    private boolean createContractActivity(String encodedId) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    Set<User> volunteers = Objects.requireNonNullElse(a.getVolunteers(), new LinkedHashSet<>());
                    List<String> volunteerIdList = new ArrayList<>();
                    for (User volunteer : volunteers) {
                        volunteerIdList.add(volunteer.getId());
                    }
                    Integer reward = (int) (a.getReward() * 1.1 / volunteers.size());
                    List<Integer> rewardList = new ArrayList<>(Collections.nCopies(volunteers.size(), reward));
                    TransactionCreation transactionCreation = new TransactionCreation(null,
                            a.getFounder().getUsername(), reward);
                    UUID transactionId = transactionService.create(transactionCreation);
                    a.getTransactions().add(transactionService.findById(CodecUtil.encodeUuid(transactionId)));
                    try {
                        Contract.createActivity(a.getId().toString(), a.getFounder().getId(), a.getVolunteers().size(),
                                volunteerIdList, rewardList, transactionId.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalStateException("Failed to create contract activity");
                    }
                    return a;
                }).orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return Contract.activityTest(activity.getId().toString())
                && activityRepository.saveAndFlush(activity) == activity;
    }

    @Override
    public Activity loadActivityById(String encodedId) {
        return activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .orElseThrow(() -> new IllegalStateException("Activity id not found"));
    }

    @Override
    public boolean verify(String encodedId, User user, boolean result) {
        Activity activity = activityRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(a -> {
                    boolean success = Status.VERIFY.equals(a.getStatus()) &&
                            (result ? a.getPassedVerifiers().add(user)
                                    : a.getRejectedVerifiers().add(user));
                    if (!success) {
                        throw new IllegalStateException("Failed to verify");
                    }
                    boolean rejected = a.getRejectedVerifiers().size() >= VerifyUtil.getRequiredNumber(a.getNumber());
                    if (rejected) {
                        a.setStatus(Status.REJECTED);
                    }
                    boolean passed = a.getPassedVerifiers().size() >= VerifyUtil.getRequiredNumber(a.getNumber());
//                    && createContractActivity(encodedId)
                    if (passed) {
                        a.setStatus(Status.PROGRESS);
                    }
                    return a;
                })
                .orElseThrow(() -> new IllegalStateException("Activity id not found"));
        return activityRepository.saveAndFlush(activity) == activity;
    }
}