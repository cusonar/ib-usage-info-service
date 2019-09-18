package com.cusonar.ib.deviceusagerate;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.DeviceUsageRate;

@Mapper
public interface DeviceUsageRateMapper {

    List<DeviceUsageRate> selectDeviceUsageRateWithDevice();
    int insertDeviceUsageRate(DeviceUsageRate deviceUsageRate);
}
