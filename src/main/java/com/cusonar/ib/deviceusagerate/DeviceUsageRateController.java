package com.cusonar.ib.deviceusagerate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.deviceusagerate.dto.DeviceUsageRateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/device-usage-rate")
@RequiredArgsConstructor
public class DeviceUsageRateController {

    private final DeviceUsageRateService deviceUsageRateService;
    
    @GetMapping("max-rate/{year}")
    public DeviceUsageRateDto.ResponseOne getMaxDeviceUsageRateByYear(@PathVariable int year) {
        return new DeviceUsageRateDto.ResponseOne(
                DeviceUsageRateDto.Response.of(
                        deviceUsageRateService.getMaxDeviceUsageRateByYear(year)));
    }
    
    @GetMapping("max-rate")
    public DeviceUsageRateDto.ResponseList getMaxDeviceUsageRateByYear() {
        return new DeviceUsageRateDto.ResponseOne(
                DeviceUsageRateDto.Response.of(
                        deviceUsageRateService.getMaxDeviceUsageRateListByYear()));
    }
}
