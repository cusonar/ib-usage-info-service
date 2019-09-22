package com.cusonar.ib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.DeviceUsageRate;

@Mapper
public interface DeviceUsageRateMapper {

    List<DeviceUsageRate> selectDeviceUsageRateListWithDevice();
    DeviceUsageRate selectDeviceUsageRateWithDevice(DeviceUsageRate.DeviceUsageRateId deviceUsageRateId);
    int insertDeviceUsageRate(DeviceUsageRate deviceUsageRate);
}
