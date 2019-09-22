package com.cusonar.ib.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceUsageRate {

    private DeviceUsageRateId deviceUsageRateId;
    private double rate;
    
    @Getter
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeviceUsageRateId {
    	private int year;
    	private Device device;
    }
}
