package com.cusonar.ib.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.core.exception.NotFoundException;
import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.mapper.DeviceUsageRateMapper;
import com.cusonar.ib.util.prediction.LinearRegression;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceUsageRateService {
	private final static Integer PREDICT_YEAR = 2019;
    private final DeviceUsageRateMapper deviceUsageRateMapper;
    
    @Cacheable(value = "deviceUsageRateList")
    public List<DeviceUsageRate> getDeviceUsageRateListWithDevice() {
        return deviceUsageRateMapper.selectDeviceUsageRateListWithDevice();
    }
    
    @Transactional(readOnly = true)
    public List<DeviceUsageRate> getMostConnectedDeviceListByYear() {
        List<DeviceUsageRate> maxList = new ArrayList<>();
        List<Integer> years = getDeviceUsageRateListWithDevice().stream()
                .map(rate -> rate.getDeviceUsageRateId().getYear())
                .distinct().collect(Collectors.toList());
        for (int year : years) {
            maxList.add(getMostConnectedDeviceByYear(year));
        }
        return maxList;
    }
    
    @Transactional(readOnly = true)
    public DeviceUsageRate getMostConnectedDeviceByYear(int year) {
        DeviceUsageRate max = getDeviceUsageRateListWithDevice().stream()
                .filter(rate -> rate.getDeviceUsageRateId().getYear() == year)
                .max(Comparator.comparing(DeviceUsageRate::getRate))
                .orElseThrow(NotFoundException::new);
        return max;
    }

    @Transactional(readOnly = true)
	public DeviceUsageRate getMostConnectedYearByDeviceId(Integer deviceId) {
    	DeviceUsageRate max = getDeviceUsageRateListWithDevice().stream()
    			.filter(rate -> rate.getDeviceUsageRateId().getDevice().getDeviceId() == deviceId)
    			.max(Comparator.comparing(DeviceUsageRate::getRate))
    			.orElseThrow(NotFoundException::new);
		return max;
	}
    
    public DeviceUsageRate predictConnectedRateByDeviceId(Integer deviceId) {
    	List<DeviceUsageRate> rateList = getDeviceUsageRateListWithDevice();
    	Double[] xArray = getFilteredStream(rateList, deviceId)  
    			.map(rate -> new Double(rate.getDeviceUsageRateId().getYear()))
    			.toArray(Double[]::new);
    	Double[] yArray = getFilteredStream(rateList, deviceId)
    			.map(rate -> new Double(rate.getRate()))
    			.toArray(Double[]::new);
    	
    	Device device = getFilteredStream(rateList, deviceId)
    			.findFirst()
    			.map(rate -> rate.getDeviceUsageRateId().getDevice())
    			.orElseThrow(NotFoundException::new);
		DeviceUsageRate.DeviceUsageRateId deviceUsageRateId = 
				new DeviceUsageRate.DeviceUsageRateId(PREDICT_YEAR, device);
		float predicted = predictConnectedRateByLinearRegression(xArray, yArray, PREDICT_YEAR);
		return new DeviceUsageRate(deviceUsageRateId, predicted);
    }
    
    private Stream<DeviceUsageRate> getFilteredStream(List<DeviceUsageRate> rateList, Integer deviceId) {
    	return rateList.stream()
    			.filter(rate -> rate.getDeviceUsageRateId().getDevice().getDeviceId() == deviceId);
    }
    
    private float predictConnectedRateByLinearRegression(Double[] x, Double[] y, int year) {
    	LinearRegression lr = new LinearRegression(x, y);
    	float predicted = (float) lr.predict((double) year);
    	if (predicted < 0) predicted = 0.0f;
    	if (predicted > 100)  predicted = 100.0f;
    	return predicted;
    }
}
