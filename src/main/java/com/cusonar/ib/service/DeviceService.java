package com.cusonar.ib.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cusonar.ib.domain.Device;
import com.cusonar.ib.mapper.DeviceMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceMapper deviceMapper;
    
    public List<Device> getDeviceList() {
        return deviceMapper.selectDeviceList();
    }
}
