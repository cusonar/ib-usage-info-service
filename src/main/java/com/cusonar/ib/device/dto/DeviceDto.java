package com.cusonar.ib.device.dto;

import java.util.List;

import com.cusonar.ib.domain.Device;

import lombok.AllArgsConstructor;
import lombok.Data;

public class DeviceDto {
        
    @Data
    @AllArgsConstructor
    public static class ResponseList {
        private List<Device> devices;
    }
}
