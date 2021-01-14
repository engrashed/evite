
package com.sample.evite;


import com.sample.evite.core.EmailService;
import com.sample.evite.core.ObjectMapperHelper;
import com.sample.evite.dto.CreateReqParam;
import com.sample.evite.dto.UpdateReqParam;
import com.sample.evite.entity.EventEntity;
import com.sample.evite.entity.UserEntity;
import com.sample.evite.mapper.eventmanagement.EventMapper;
import com.sample.evite.mapper.eventmanagement.UserMapper;
import com.sample.evite.resonse.EventResponseListDto;
import com.sample.evite.resonse.UserResponseListDto;
import com.sample.evite.service.EventService;
import com.sample.evite.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Import({EventService.class, UserService.class, EmailService.class})
@SpringBootTest(classes = EventServiceTest.class)
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private EventMapper eventMapper;

    @Test
    public void eventCreateTest() {
        int count = 1;
        CreateReqParam param = new CreateReqParam();
        param.setName("Test Event");
        param.setLocation("Saitama");
        param.setTimeZone("Asia/Tokyo");
        param.setStartTime("2021-01-13 09:20");
        param.setEndTime("2021-01-13 19:20");
        EventEntity entity = ObjectMapperHelper.map(param, EventEntity.class);
        List<EventEntity> entities = new ArrayList<>();
        entities.add(entity);
        when(eventMapper.create(entity)).thenReturn(count);
        when(eventMapper.getList()).thenReturn(entities);
        eventService.create(param);
        EventResponseListDto events = eventService.getList();
        assertEquals(events.getEventList().get(0).getName(), entity.getName());
        assertEquals(events.getEventList().get(0).getLocation(), entity.getLocation());
    }

    @Test
    public void allEventListTest() {
        List<EventEntity> entities = new ArrayList<>();
        CreateReqParam param = new CreateReqParam();
        param.setName("Test Event");
        param.setLocation("Saitama");
        param.setTimeZone("Asia/Tokyo");
        param.setStartTime("2021-01-13 09:20");
        param.setEndTime("2021-01-13 19:20");
        EventEntity entity = ObjectMapperHelper.map(param, EventEntity.class);
        entities.add(entity);

        CreateReqParam param1 = new CreateReqParam();
        param1.setName("Test Event");
        param1.setLocation("Saitama");
        param1.setTimeZone("Asia/Tokyo");
        param1.setStartTime("2021-01-13 09:20");
        param1.setEndTime("2021-01-13 19:20");
        EventEntity entity1 = ObjectMapperHelper.map(param, EventEntity.class);
        entities.add(entity1);

        when(eventMapper.getList()).thenReturn(entities);
        EventResponseListDto events = eventService.getList();
        assertEquals(events.getEventList().size(), 2);
        assertEquals(events.getEventList().get(0).getLocation(), entity.getLocation());
    }

    @Test
    public void getUserInvolvedEventsTest() {
        int count = 1;
        List<EventEntity> entities = new ArrayList<>();
        // Creating user
        UserEntity entity = new UserEntity();
        entity.setUserId(1L);
        entity.setName("Test Event User");
        entity.setEmail("test.event.user@test.com");
        entity.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        when(userMapper.create(entity)).thenReturn(count);

        // creating event
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);
        eventEntity.setName("Test Event");
        eventEntity.setLocation("Saitama");
        eventEntity.setTimeZone("Asia/Tokyo");
        eventEntity.setStartTime("2021-01-13 09:20");
        eventEntity.setEndTime("2021-01-13 19:20");
        when(eventMapper.create(eventEntity)).thenReturn(count);

        entities.add(eventEntity);

        // creating user_event
        when(userMapper.createUserEvent(eventEntity.getEventId(), entity.getEmail())).thenReturn(count);
        // Get user event
        when(eventMapper.getEventListByEmail(entity.getEmail())).thenReturn(entities);

        EventResponseListDto userEnventList = eventService.getEventList(entity.getEmail());
        assertEquals(userEnventList.getEventList().get(0).getEventId(), eventEntity.getEventId());
        assertEquals(userEnventList.getEventList().get(0).getName(), eventEntity.getName());
    }

    @Test
    public void getUserListInEventTest() {
        int count = 1;
        List<UserEntity> entities = new ArrayList<>();
        // Creating user
        UserEntity entity = new UserEntity();
        entity.setUserId(1L);
        entity.setName("Test Event User");
        entity.setEmail("test.event.user@test.com");
        entity.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        when(userMapper.create(entity)).thenReturn(count);
        entities.add(entity);

        UserEntity entity1 = new UserEntity();
        entity1.setUserId(2L);
        entity1.setName("Test Event User2");
        entity1.setEmail("test.event.user2@test.com");
        entity1.setPassword("16d7a4fca7442dda3ad93c9a726597e4");
        when(userMapper.create(entity)).thenReturn(count);
        entities.add(entity1);

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
        when(userMapper.createUserEvent(eventEntity.getEventId(), entity.getEmail())).thenReturn(count);
        when(userMapper.createUserEvent(eventEntity.getEventId(), entity1.getEmail())).thenReturn(count);
        // Get user event
        when(userMapper.getListByEnventId(eventEntity.getEventId())).thenReturn(entities);

        UserResponseListDto userList = eventService.getUserList(eventEntity.getEventId());
        assertEquals(userList.getUserList().get(0).getUserId(), entity.getUserId());
        assertEquals(userList.getUserList().get(1).getName(), entity1.getName());
    }

    @Test
    public void deleteEventTest() {
        // creating event
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);
        eventEntity.setName("Test Event");
        eventEntity.setLocation("Saitama");
        eventEntity.setTimeZone("Asia/Tokyo");
        eventEntity.setStartTime("2021-01-13 09:20");
        eventEntity.setEndTime("2021-01-13 19:20");
        when(eventMapper.create(eventEntity)).thenReturn(1);

        // Soft delete event
        when(eventMapper.update(eventEntity.getEventId())).thenReturn(1);
        eventService.deleteEvent(eventEntity.getEventId());
    }

    @Test
    public void updateEventTest() {
        // creating event
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventId(1L);
        eventEntity.setName("Test Event");
        eventEntity.setLocation("Saitama");
        eventEntity.setTimeZone("Asia/Tokyo");
        eventEntity.setStartTime("2021-01-13 09:20");
        eventEntity.setEndTime("2021-01-13 19:20");
        when(eventMapper.create(eventEntity)).thenReturn(1);

        when(eventMapper.getById(eventEntity.getEventId())).thenReturn(eventEntity);

        // Preparing update param
        UpdateReqParam param = new UpdateReqParam();
        param.setEventId(eventEntity.getEventId());
        param.setLocation("Chiba");
        param.setEndTime("2021-01-14 18:20");

        // update Event
        eventService.updateEvent(param);
        EventEntity e = eventService.getById(eventEntity.getEventId());
        assertEquals(e.getLocation(), param.getLocation());
        assertEquals(e.getEndTime(), param.getEndTime());
    }
}


