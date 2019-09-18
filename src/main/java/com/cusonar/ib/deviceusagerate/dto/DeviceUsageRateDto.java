package com.cusonar.ib.deviceusagerate.dto;

import com.cusonar.ib.domain.DeviceUsageRate;

import lombok.Builder;
import lombok.Data;

public class DeviceUsageRateDto {
    
    @Data
    @Builder
    public static class Response {
        private int year;
        private int deviceId;
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
    public static class ResponseOne {
         private Response result;
         public ResponseOne(Response response) {
             this.result = response;
         }
    }
    
    public static class ResponseList {
        private List<>
    }
}
