package com.sample.evite.controller;

import com.sample.evite.core.CommonResponse;
import com.sample.evite.dto.AddEventParam;
import com.sample.evite.dto.DeleteParam;
import com.sample.evite.dto.UserRequestParam;
import com.sample.evite.entity.UserEntity;
import com.sample.evite.resonse.AddEventResponseDto;
import com.sample.evite.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("/sign-up")
    public CommonResponse create(@RequestBody @Valid UserRequestParam param) {
        service.create(param);
        return CommonResponse.success("OK");
    }

    @GetMapping("/info")
    public CommonResponse<UserEntity> user(@RequestParam @NotNull String email) {
        UserEntity entity = service.singleUser(email);
        return CommonResponse.success(entity);
    }

    @PostMapping("/add-event")
    public CommonResponse<AddEventResponseDto> add(@RequestBody @Valid AddEventParam param) throws Exception {
        AddEventResponseDto dto = service.addEvent(param);
        return CommonResponse.success(dto);
    }

    @PutMapping("/remove/event")
    public CommonResponse deleteUserEvent(@RequestBody @Valid DeleteParam param) {
        service.deleteUserFromEvent(param.getEmail(), param.getEventId());
        return CommonResponse.success("OK");
    }
}
