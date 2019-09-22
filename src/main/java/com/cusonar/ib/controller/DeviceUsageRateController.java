package com.cusonar.ib.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.response.ResponseOne;
import com.cusonar.ib.service.DeviceUsageRateService;
import com.cusonar.ib.service.dto.DeviceUsageRateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/device-usage-rates")
@RequiredArgsConstructor
public class DeviceUsageRateController {

    private final DeviceUsageRateService deviceUsageRateService;
    
    @GetMapping("most-connected-devices/{year}")
    public ResponseOne<DeviceUsageRateDto.ResponseOne> getMostConnectedDeviceListByYear(@PathVariable int year) {
        return new ResponseOne<>(
                DeviceUsageRateDto.ResponseOne.of(
                        deviceUsageRateService.getMostConnectedDeviceByYear(year)));
    }
    
    @GetMapping("most-connected-devices")
    public DeviceUsageRateDto.ResponseList getMostConnectedDeviceByYear() {
    	List<DeviceUsageRateDto.Response> responseList = deviceUsageRateService.getMostConnectedDeviceListByYear()
    			.stream()
    			.map(DeviceUsageRateDto.Response::of)
    			.collect(Collectors.toList());
        return new DeviceUsageRateDto.ResponseList(responseList);
    }
    
    @GetMapping("most-connected-years/{deviceId}")
    public ResponseOne<DeviceUsageRateDto.ResponseOne> getMostConnectedYearByDeviceId(@PathVariable Integer deviceId) {
    	return new ResponseOne<>(
    			DeviceUsageRateDto.ResponseOne.of(
    					deviceUsageRateService.getMostConnectedYearByDeviceId(deviceId)));
    }
    
    @GetMapping("predictions/{deviceId}")
    public DeviceUsageRateDto.ResponseOne predictConnectedRateByDeviceId(@PathVariable Integer deviceId) {
    	DeviceUsageRate rate = deviceUsageRateService.predictConnectedRateByDeviceId(deviceId);
		return DeviceUsageRateDto.ResponseOne.of(rate);
    }
}
