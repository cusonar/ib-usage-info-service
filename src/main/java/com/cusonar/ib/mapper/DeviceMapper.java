package com.cusonar.ib.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.Device;

@Mapper
public interface DeviceMapper {

    List<Device> selectDeviceList();
    Device selectDevice(Integer deviceId);
    int insertDevice(Device device);
}
