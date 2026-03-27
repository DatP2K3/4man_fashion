package com.fourman.notification.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.notification.domain.UserTopic;
import com.fourman.notification.infrastructure.persistence.entity.UserTopicEntity;

@Mapper(componentModel = "Spring")
public interface UserTopicEntityMapper extends EntityMapper<UserTopic, UserTopicEntity> {}
