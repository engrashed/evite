package com.sample.evite.controller;

import com.sample.evite.core.CommonResponse;
import com.sample.evite.dto.*;
import com.sample.evite.response.EventResponseListDto;
import com.sample.evite.response.UserResponseListDto;
import com.sample.evite.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("event")
public class EventController {
    @Autowired
    EventService service;

    @PostMapping("/create")
    public CommonResponse create(@RequestBody @Valid CreateReqParam param) {
        service.create(param);
        return CommonResponse.success("OK");
    }

    @GetMapping("/list")
    public CommonResponse<EventResponseListDto> fetchList() {
        EventResponseListDto dto = service.getList();
        return CommonResponse.success(dto);
    }

    @GetMapping("/user-event/list")
    public CommonResponse<EventResponseListDto> fetchUserList(@RequestParam @NotNull String email) {
        EventResponseListDto dto = service.getEventList(email);
        return CommonResponse.success(dto);
    }

    @GetMapping("/user-list/{eventId}")
    public CommonResponse<UserResponseListDto> userList(@PathVariable @NotNull Long eventId) {
        UserResponseListDto dtos = service.getUserList(eventId);
        return CommonResponse.success(dtos);
    }

    @PutMapping("/delete")
    public CommonResponse delete(@RequestBody @Valid DeleteEventParam param) {
        service.deleteEvent(param.getEventId());
        return CommonResponse.success("OK");
    }

    @PutMapping("/update")
    public CommonResponse update(@RequestBody @Valid UpdateReqParam param) {
        service.updateEvent(param);
        return CommonResponse.success("OK");
    }
}
