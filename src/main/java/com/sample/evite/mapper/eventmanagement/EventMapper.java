package com.sample.evite.mapper.eventmanagement;

import com.sample.evite.entity.EventEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventMapper {

    List<EventEntity> getList();

    List<EventEntity> getEventListByEmail(String email);

    EventEntity getById(long id);

    List<EventEntity> getByName(@Param("player") String player);

    Integer create(EventEntity entity);

    Integer update(@Param("id") long id);

    Integer updateEvent(EventEntity entity);
}
