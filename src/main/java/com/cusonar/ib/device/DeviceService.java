package com.cusonar.ib.device;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.domain.Device;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceMapper deviceMapper;
    
    public List<Device> getDeviceList() {
        return deviceMapper.selectDeviceList();
    }
    
    @Transactional
    public void saveDeviceList(List<Device> deviceList) {
        for (Device device : deviceList) {
            deviceMapper.insertDevice(device);
        }
    }
}
