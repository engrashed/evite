package com.sample.evite;

import com.sample.evite.core.EmailService;
import com.sample.evite.core.ObjectMapperHelper;
import com.sample.evite.dto.AddEventParam;
import com.sample.evite.dto.UserRequestParam;
import com.sample.evite.entity.EventEntity;
import com.sample.evite.entity.UserEntity;
import com.sample.evite.entity.UserEventEntity;
import com.sample.evite.mapper.eventmanagement.EventMapper;
import com.sample.evite.mapper.eventmanagement.UserMapper;
import com.sample.evite.resonse.AddEventResponseDto;
import com.sample.evite.service.EventService;
import com.sample.evite.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.ObjectUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Import({EventService.class, UserService.class, EmailService.class})
@SpringBootTest(classes = UserServiceTest.class)
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private EventMapper eventMapper;

    @MockBean
    private UserMapper mapper;

    @Test
    public void userCreateTest() {
        int count = 1;
        UserRequestParam param = new UserRequestParam();
        param.setName("Sample User1");
        param.setEmail("sample_user@test.com");
        param.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        UserEntity entity = ObjectMapperHelper.map(param, UserEntity.class);
        entity.setUserId(1L);

        when(mapper.create(entity)).thenReturn(count);
        service.create(param);
        when(mapper.getByEmail(entity.getEmail())).thenReturn(entity);
        UserEntity user = service.singleUser(entity.getEmail());
        assertEquals(user.getName(), entity.getName());
        assertEquals(user.getEmail(), user.getEmail());
    }

    @Test
    public void addUserToEventTest() throws Exception {
        int count = 1;
        // Creating user
        UserEntity entity = new UserEntity();
        entity.setUserId(1L);
        entity.setName("Test Event User");
        entity.setEmail("test.event.user@test.com");
        entity.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        when(mapper.create(entity)).thenReturn(count);

        // creating event
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);
        eventEntity.setName("Test Event");
        eventEntity.setLocation("Saitama");
        eventEntity.setTimeZone("Asia/Tokyo");
        eventEntity.setStartTime("2021-01-13 09:20");
        eventEntity.setEndTime("2021-01-13 19:20");
        when(eventMapper.create(eventEntity)).thenReturn(count);

        // creating user_event
        when(mapper.createUserEvent(eventEntity.getEventId(), entity.getEmail())).thenReturn(count);
        // Get user event
        when(eventMapper.getById(eventEntity.getEventId())).thenReturn(eventEntity);
        // get user entity
        when(mapper.getByEmail(entity.getEmail())).thenReturn(entity);

        AddEventParam param = new AddEventParam();
        param.setEventId(eventEntity.getEventId());
        param.setEmail(entity.getEmail());
        AddEventResponseDto responseDto = service.addEvent(param);

        assertEquals(responseDto.getEmail(), entity.getEmail());
        assertEquals(Long.valueOf(responseDto.getEventId()), eventEntity.getEventId());
    }

    @Test
    public void deleteUserFromEventTest() throws Exception {
        int count = 1;
        // Creating user
        UserEntity entity = new UserEntity();
        entity.setUserId(1L);
        entity.setName("Test Event User");
        entity.setEmail("test.event.user@test.com");
        entity.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        when(mapper.create(entity)).thenReturn(count);

        // creating event
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);
        eventEntity.setName("Test Event");
        eventEntity.setLocation("Saitama");
        eventEntity.setTimeZone("Asia/Tokyo");
        eventEntity.setStartTime("2021-01-13 09:20");
        eventEntity.setEndTime("2021-01-13 19:20");
        when(eventMapper.create(eventEntity)).thenReturn(count);

        // creating user_event
        when(mapper.createUserEvent(eventEntity.getEventId(), entity.getEmail())).thenReturn(count);
        // Get user event
        when(mapper.deleteUserEvent(entity.getEmail(), eventEntity.getEventId())).thenReturn(count);
        // get user event entity
        when(mapper.getUserEventByEmail(entity.getEmail(), eventEntity.getEventId())).thenReturn(new UserEventEntity());

       service.deleteUserFromEvent(entity.getEmail(), eventEntity.getEventId());
       UserEventEntity userEventEntity = new UserEventEntity();
       assertTrue(ObjectUtils.isEmpty(userEventEntity.getUserEventId()));
    }
}
