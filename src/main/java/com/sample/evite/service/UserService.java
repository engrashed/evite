package com.sample.evite.service;

import com.sample.evite.core.EmailService;
import com.sample.evite.core.ErrorStatus;
import com.sample.evite.core.ObjectMapperHelper;
import com.sample.evite.core.ServiceException;
import com.sample.evite.dto.AddEventParam;
import com.sample.evite.dto.UserRequestParam;
import com.sample.evite.entity.EventEntity;
import com.sample.evite.entity.UserEntity;
import com.sample.evite.entity.UserEventEntity;
import com.sample.evite.mapper.eventmanagement.UserMapper;
import com.sample.evite.resonse.AddEventResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${mail.subject}")
    private String mailSubject;

    @Value("${mail.body}")
    private String mailBody;

    @Value("${mail.user}")
    private String user;

    @Value("${mail.event}")
    private String event;

    private final UserMapper mapper;

    @Autowired
    EmailService emailService;

    @Autowired
    EventService service;

    /**
     *
     * @param eventId
     * @return  User entity list
     */
    public List<UserEntity> getUserListById(long eventId) {
        return mapper.getListByEnventId(eventId);
    }

    /**
     *
     * @param param
     */
    @Transactional(rollbackFor = {Exception.class})
    public void create(UserRequestParam param) {
        UserEntity entity = ObjectMapperHelper.map(param, UserEntity.class);
        UserEntity entityExist = mapper.getByEmail(param.getEmail());
        if (!ObjectUtils.isEmpty(entityExist)) {
            throw new ServiceException(ErrorStatus.Err_EXIST);
        }
        mapper.create(entity);
    }

    /**
     *
     * @param email
     * @return user entity
     */
    public UserEntity singleUser(String email){
        return mapper.getByEmail(email);
    }

    /**
     *
     * @param param
     * @return event Id and user email address
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class})
    public AddEventResponseDto addEvent(AddEventParam param) throws Exception {
        UserEventEntity userExist = mapper.getUserEventByEmail(param.getEmail(), param.getEventId());
        if (!ObjectUtils.isEmpty(userExist)) {
            throw new ServiceException(ErrorStatus.Err_EXIST);
        }
        int count = mapper.createUserEvent(param.getEventId(), param.getEmail());
        if (count < 1) {
            throw new ServiceException(ErrorStatus.Err_EVENTCREATE);
        }
        // Send Completion email
        EventEntity event = service.getById(param.getEventId());
        sendCompletionEmail(param.getEmail(), event.getName());
        //Prepare Response
        AddEventResponseDto response = new AddEventResponseDto();
        response.setEventId(param.getEventId());
        response.setEmail(param.getEmail());
        return response;
    }

    /**
     *
     * @param email
     * @param eventId
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUserFromEvent(String email, long eventId) {
        int count = mapper.deleteUserEvent(email, eventId);
        if (count < 1) {
            throw new ServiceException(ErrorStatus.Err_EVENTDELETE);
        }
    }

    private void sendCompletionEmail(String email, String eventName) throws Exception {
        UserEntity entity = mapper.getByEmail(email);

        String body = mailBody.replace(
                user, entity.getName())
                .replace(event, eventName);

        emailService.sendEMail(email, mailSubject, body);
    }
}
