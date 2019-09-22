package com.cusonar.ib.service.dto;

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
        private Integer deviceId;
        @JsonProperty("device_name")
        private String deviceName;
        private double rate;
        
        public static Response of(DeviceUsageRate deviceUsageRate) {
            return Response.builder()
                    .year(deviceUsageRate.getDeviceUsageRateId().getYear())
                    .deviceId(deviceUsageRate.getDeviceUsageRateId().getDevice().getDeviceId())
                    .deviceName(deviceUsageRate.getDeviceUsageRateId().getDevice().getDeviceName())
                    .rate(deviceUsageRate.getRate()).build();
        }
    }

    @Data
    @Builder
    public static class ResponseOne {
    	private int year;
    	@JsonProperty("device_name")
        private String deviceName;
        private double rate;
        
        public static ResponseOne of(DeviceUsageRate deviceUsageRate) {
            return ResponseOne.builder()
                    .year(deviceUsageRate.getDeviceUsageRateId().getYear())
                    .deviceName(deviceUsageRate.getDeviceUsageRateId().getDevice().getDeviceName())
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
