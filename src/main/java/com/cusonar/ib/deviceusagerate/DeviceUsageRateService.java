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
    public List<DeviceUsageRate> getMaxDeviceUsageRateListByYear() {
        List<DeviceUsageRate> maxList = new ArrayList<>();
        List<DeviceUsageRate> deviceUsageRateList = getDeviceUsageRateList();
        List<Integer> years = deviceUsageRateList.stream()
                .map(Rate -> Rate.getYear())
                .distinct().collect(Collectors.toList());
        for (int year : years) {
            maxList.add(getMaxDeviceUsageRateByYear(year));
        }
        return maxList;
    }
    
    @Transactional(readOnly = true)
    public DeviceUsageRate getMaxDeviceUsageRateByYear(int year) {
        DeviceUsageRate max = getDeviceUsageRateList().stream()
                .filter(Rate -> Rate.getYear() == year)
                .max(Comparator.comparing(DeviceUsageRate::getRate))
                .orElseThrow(NotFoundException::new);
        return max;
    }
}
