package com.cusonar.ib.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Device implements Serializable {

	private static final long serialVersionUID = -8438002032213431604L;
	
	@JsonProperty("device_id")
    private Integer deviceId;
    @JsonProperty("device_name")
    private String deviceName;
    
    public Device(Integer deviceId) {
        this.deviceId = deviceId;
    }
    
    public Device(String deviceName) {
        this.deviceName = deviceName;
    }
    
}
