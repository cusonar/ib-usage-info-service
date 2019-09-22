package com.cusonar.ib.service.dto;

import java.util.List;

import com.cusonar.ib.domain.Device;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class DeviceDto {
	
	@Data
    @Builder
    public static class Response {
        @JsonProperty("device_id")
        private Integer deviceId;
        @JsonProperty("device_name")
        private String deviceName;
        
        public static Response of(Device device) {
            return Response.builder()
                    .deviceId(device.getDeviceId())
                    .deviceName(device.getDeviceName()).build();
        }
    }
        
    @Data
    @AllArgsConstructor
    public static class ResponseList {
        private List<Response> devices;
    }
}
