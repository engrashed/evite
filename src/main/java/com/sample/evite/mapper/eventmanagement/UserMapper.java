package com.sample.evite.mapper.eventmanagement;

import com.sample.evite.entity.UserEntity;
import com.sample.evite.entity.UserEventEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<UserEntity> getListByEnventId(long eventId);

    Integer create(UserEntity entity);

    UserEntity getByEmail(String email);

    Integer createUserEvent(long eventId, String email);

    UserEventEntity getUserEventByEmail(String email, long eventId);

    Integer deleteUserEvent(String email, long eventId);
}
