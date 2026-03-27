package com.fourman.notification.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.fourman.common.mapper.EntityMapper;
import com.fourman.notification.domain.DeviceRegistration;
import com.fourman.notification.infrastructure.persistence.entity.DeviceRegistrationEntity;

@Mapper(componentModel = "Spring")
public interface DeviceRegistrationEntityMapper extends EntityMapper<DeviceRegistration, DeviceRegistrationEntity> {}
