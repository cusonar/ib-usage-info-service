package com.cusonar.ib.deviceusagerate.dto;

import java.util.List;

import com.cusonar.ib.domain.DeviceUsageRate;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

public class DeviceUsageRateDto {
    
    @Data
    @Builder
    public static class Response {
        private int year;
        @JsonProperty("device_id")
        private int deviceId;
        @JsonProperty("device_name")
        private String deviceName;
        private float rate;
        
        public static Response of(DeviceUsageRate deviceUsageRate) {
            return Response.builder()
                    .year(deviceUsageRate.getYear())
                    .deviceId(deviceUsageRate.getDevice().getDeviceId())
                    .deviceName(deviceUsageRate.getDevice().getDeviceName())
                    .rate(deviceUsageRate.getRate()).build();
        }
    }

    @Data
    @Builder
    public static class ResponseOne {
    	private int year;
    	@JsonProperty("device_name")
        private String deviceName;
        private float rate;
        
        public static ResponseOne of(DeviceUsageRate deviceUsageRate) {
            return ResponseOne.builder()
                    .year(deviceUsageRate.getYear())
                    .deviceName(deviceUsageRate.getDevice().getDeviceName())
                    .rate(deviceUsageRate.getRate()).build();
        }
    }
    
    @Data
    public static class ResponseList {
        private List<Response> devices;
        public ResponseList(List<Response> responseList) {
        	this.devices = responseList;
        }
    }
}
