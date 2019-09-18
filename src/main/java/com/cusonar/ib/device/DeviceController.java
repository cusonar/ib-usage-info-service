package com.cusonar.ib.device;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.device.dto.DeviceDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    
    @GetMapping
    public DeviceDto.ResponseList getDeviceList() {
        return new DeviceDto.ResponseList(deviceService.getDeviceList());
    }
}
