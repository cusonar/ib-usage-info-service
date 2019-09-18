package com.cusonar.ib.deviceusagerate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.core.exception.NotFoundException;
import com.cusonar.ib.domain.DeviceUsageRate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceUsageRateService {

    private final DeviceUsageRateMapper deviceUsageRateMapper;
    
    @Transactional(readOnly = true)
    public List<DeviceUsageRate> getDeviceUsageRateList() {
        return deviceUsageRateMapper.selectDeviceUsageRateWithDevice();
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveDeviceUsageRateList(List<DeviceUsageRate> deviceUsageRateList) {
        for (DeviceUsageRate deviceUsageRate : deviceUsageRateList) {
            deviceUsageRateMapper.insertDeviceUsageRate(deviceUsageRate);
        }
    }
    
    @Transactional(readOnly = true)
    public List<DeviceUsageRate> getMostConnectedDeviceListByYear() {
        List<DeviceUsageRate> maxList = new ArrayList<>();
        List<Integer> years = getDeviceUsageRateList().stream()
                .map(rate -> rate.getYear())
                .distinct().collect(Collectors.toList());
        for (int year : years) {
            maxList.add(getMostConnectedDeviceListByYear(year));
        }
        return maxList;
    }
    
    @Transactional(readOnly = true)
    public DeviceUsageRate getMostConnectedDeviceListByYear(int year) {
        DeviceUsageRate max = getDeviceUsageRateList().stream()
                .filter(rate -> rate.getYear() == year)
                .max(Comparator.comparing(DeviceUsageRate::getRate))
                .orElseThrow(NotFoundException::new);
        return max;
    }

    @Transactional(readOnly = true)
	public DeviceUsageRate getMostConnectedYearByDeviceId(int deviceId) {
    	DeviceUsageRate max = getDeviceUsageRateList().stream()
    			.filter(rate -> rate.getDevice().getDeviceId() == deviceId)
    			.max(Comparator.comparing(DeviceUsageRate::getRate))
    			.orElseThrow(NotFoundException::new);
		return max;
	}
}
