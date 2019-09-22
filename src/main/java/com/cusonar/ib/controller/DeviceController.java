package com.cusonar.ib.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.service.DeviceService;
import com.cusonar.ib.service.dto.DeviceDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    
    @GetMapping
    public DeviceDto.ResponseList getDeviceList() {
    	List<DeviceDto.Response> responseList = deviceService.getDeviceList()
    			.stream()
    			.map(DeviceDto.Response::of)
    			.collect(Collectors.toList());
    	return new DeviceDto.ResponseList(responseList);
    }
}
