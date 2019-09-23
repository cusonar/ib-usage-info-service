package com.cusonar.ib.domain;

import java.io.Serializable;

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
public class DeviceUsageRate implements Serializable {

	private static final long serialVersionUID = -2270737672039746179L;
	
	private DeviceUsageRateId deviceUsageRateId;
    private float rate;
    
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
