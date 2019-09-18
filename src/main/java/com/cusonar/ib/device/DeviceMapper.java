package com.cusonar.ib.device;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.Device;

@Mapper
public interface DeviceMapper {

    List<Device> selectDeviceList();
    int insertDevice(Device device);
}
