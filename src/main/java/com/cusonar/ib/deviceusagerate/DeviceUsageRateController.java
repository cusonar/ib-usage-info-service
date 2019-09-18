package com.cusonar.ib.deviceusagerate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusonar.ib.deviceusagerate.dto.DeviceUsageRateDto;
import com.cusonar.ib.response.ResponseOne;

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
                        deviceUsageRateService.getMostConnectedDeviceListByYear(year)));
    }
    
    @GetMapping("most-connected-devices")
    public DeviceUsageRateDto.ResponseList getMostConnectedDeviceListByYear() {
    	List<DeviceUsageRateDto.Response> responseList = deviceUsageRateService.getMostConnectedDeviceListByYear()
    		.stream()
    		.map(DeviceUsageRateDto.Response::of)
    		.collect(Collectors.toList());
        return new DeviceUsageRateDto.ResponseList(responseList);
    }
    
    @GetMapping("most-connected-year/{deviceId}")
    public ResponseOne<DeviceUsageRateDto.ResponseOne> getMostConnectedYearByDeviceId(@PathVariable int deviceId) {
    	return new ResponseOne<>(
    			DeviceUsageRateDto.ResponseOne.of(
    					deviceUsageRateService.getMostConnectedYearByDeviceId(deviceId)));
    }
}
