package com.fortuna.bampo.util;

import com.fortuna.bampo.model.response.*;
import com.fortuna.bampo.model.response.data.BaseAbstract;
import com.fortuna.bampo.model.response.data.BaseInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * HTTP 响应工具类
 *
 * @author Eva7
 * @since 0.3.7
 */
public class ResponseUtil {

    public static ResponseEntity<RegistrationResponse> registrationResponseCreated(URI location) {
        return ResponseEntity.created(location).body(RegistrationResponse.builder()
                .message("Registered")
                .path(location.getPath())
                .status(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<RegistrationResponse> registrationResponseBadRequest() {
        return ResponseEntity.badRequest().body(RegistrationResponse.builder()
                .message("Failed to register")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<CreationResponse> creationResponseCreated(URI location) {
        return ResponseEntity.created(location).body(CreationResponse.builder()
                .message("Created")
                .path(location.getPath())
                .status(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<VerificationResponse> verificationResponseOk() {
        return ResponseEntity.ok().body(VerificationResponse.builder()
                .message("Ok")
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<VerificationResponse> verificationResponseBadRequest() {
        return ResponseEntity.badRequest().body(VerificationResponse.builder()
                .message("Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static <E extends BaseAbstract> ResponseEntity<AbstractResponse<E>> abstractResponseOk(E data) {
        return ResponseEntity.ok().body(AbstractResponse.<E>builder()
                .data(data)
                .message("Abstract fetched")
                .status(HttpStatus.OK.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static <E extends BaseInfo> ResponseEntity<InfoResponse<E>> infoResponseOk(E data) {
        return ResponseEntity.ok().body(InfoResponse.<E>builder()
                .data(data)
                .message("Info fetched")
                .status(HttpStatus.OK.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<SearchResponse> searchResponseOk(List<String> data) {
        return ResponseEntity.ok().body(SearchResponse.builder()
                .data(data)
                .message("Search success")
                .status(HttpStatus.OK.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<ListResponse> listResponseOk(List<String> data) {
        return ResponseEntity.ok().body(ListResponse.builder()
                .data(data)
                .message("Fetch list success")
                .status(HttpStatus.OK.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<UpdateResponse> updateResponseOk() {
        return ResponseEntity.ok().body(UpdateResponse.builder()
                .message("Update success")
                .status(HttpStatus.OK.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }

    public static ResponseEntity<UpdateResponse> updateResponseBadRequest() {
        return ResponseEntity.badRequest().body(UpdateResponse.builder()
                .message("Failed to update")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(ServletUriComponentsBuilder.fromCurrentContextPath().build().getPath())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build());
    }
}
