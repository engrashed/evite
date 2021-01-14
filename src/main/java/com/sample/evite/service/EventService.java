package com.sample.evite.service;

import com.sample.evite.core.ErrorStatus;
import com.sample.evite.core.ObjectMapperHelper;
import com.sample.evite.core.ServiceException;
import com.sample.evite.dto.CreateReqParam;
import com.sample.evite.dto.UpdateReqParam;
import com.sample.evite.entity.EventEntity;
import com.sample.evite.entity.UserEntity;
import com.sample.evite.mapper.eventmanagement.EventMapper;
import com.sample.evite.response.EventResponseDto;
import com.sample.evite.response.EventResponseListDto;
import com.sample.evite.response.UserResponseDto;
import com.sample.evite.response.UserResponseListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    @Value("${time.zone}")
    private String timeZone;

    private final EventMapper mapper;

    @Autowired
    UserService userService;

    /**
     *
     * @param param
     */
    @Transactional(rollbackFor = {Exception.class})
    public void create(CreateReqParam param) {
        if (!param.getTimeZone().equalsIgnoreCase(timeZone)) {
            throw new ServiceException(ErrorStatus.Err_TIMEZONE);
        }
        // Converting Request DTO to Entity
        EventEntity entity = ObjectMapperHelper.map(param, EventEntity.class);
        Integer count = mapper.create(entity);
        if (count < 1) {
            throw new ServiceException(ErrorStatus.Err_CREATE);
        }
    }

    /**
     *
     * @return Event List DTO
     */
    public EventResponseListDto getList() {
        List<EventEntity> list = mapper.getList();
        if (list.size() < 1) {
            throw new ServiceException(ErrorStatus.Err_DATA);
        }
        List<EventResponseDto> dtos =
                list.stream()
                        .map(event -> ObjectMapperHelper.map(event, EventResponseDto.class))
                        .collect(Collectors.toList());
        EventResponseListDto eventList = new EventResponseListDto();
        eventList.setEventList(dtos);
        return eventList;
    }

    /**
     *
     * @param email
     * @return event list for a user
     */
    public EventResponseListDto getEventList(String email) {
        List<EventEntity> list = mapper.getEventListByEmail(email);
        if (list.size() < 1) {
            throw new ServiceException(ErrorStatus.Err_DATA);
        }
        List<EventResponseDto> dtos =
                list.stream()
                        .map(event -> ObjectMapperHelper.map(event, EventResponseDto.class))
                        .collect(Collectors.toList());
        EventResponseListDto eventList = new EventResponseListDto();
        eventList.setEventList(dtos);
        return eventList;
    }

    /**
     *
     * @param eventId
     * @return User list for an event
     */
    public UserResponseListDto getUserList(long eventId) {
        List<UserEntity> entities = userService.getUserListById(eventId);
        if (ObjectUtils.isEmpty(entities)) {
            throw new ServiceException(ErrorStatus.Err_DATA);
        }
        // Converting Entity to Response DTO
        List<UserResponseDto> dtos =
                entities.stream()
                        .map(user -> ObjectMapperHelper.map(user, UserResponseDto.class))
                        .collect(Collectors.toList());
        UserResponseListDto userList = new UserResponseListDto();
        userList.setUserList(dtos);
        return userList;
    }

    /**
     *
     * @param id
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteEvent(long id) {
        Integer count = mapper.update(id);
        if (count < 1) {
            throw new ServiceException(ErrorStatus.Err_DELETE);
        }
    }

    /**
     *
     * @param param
     */
    @Transactional(rollbackFor = {Exception.class})
    public void updateEvent(UpdateReqParam param) {
        EventEntity entity = mapper.getById(param.getEventId());
        if (!StringUtils.isEmpty(param.getName())) {
            entity.setName(param.getName());
        }
        if (!StringUtils.isEmpty(param.getLocation())) {
            entity.setLocation(param.getLocation());
        }
        if (!StringUtils.isEmpty(param.getStartTime())) {
            entity.setStartTime(param.getStartTime());
        }
        if (!StringUtils.isEmpty(param.getEndTime())) {
            entity.setEndTime(param.getEndTime());
        }
        mapper.updateEvent(entity);
    }


    /**
     *
     * @param eventId
     * @return Event Entity
     */
    public EventEntity getById(long eventId) {
        return mapper.getById(eventId);
    }
}
