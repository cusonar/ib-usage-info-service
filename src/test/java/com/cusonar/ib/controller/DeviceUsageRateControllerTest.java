package com.cusonar.ib.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cusonar.ib.core.aop.ExceptionController;
import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.service.DeviceUsageRateService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceUsageRateControllerTest {

	static final String DEVICE_USAGE_RATE_URI = "/api/device-usage-rates";
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@Mock
    DeviceUsageRateService deviceUsageRateService;
	
    @InjectMocks
    DeviceUsageRateController deviceUsageRateController;
    
    MockMvc mockMvc;
    
    DeviceUsageRate rate11, rate12, rate13, rate21, rate22, rate23;
    Device device1, device2;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deviceUsageRateController)
				.setControllerAdvice(new ExceptionController())
				.alwaysDo(print())
				.build();
		
		device1 = new Device(1, "device1");
		device2 = new Device(2, "device2");
		
		rate11 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2018, device1), 10.02f);
		rate12 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2017, device1), 20.06f);
		rate13 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2016, device1), 50.05f);
		rate21 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2018, device2), 17.02f);
		rate22 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2017, device2), 41.08f);
		rate23 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2016, device2), 19.02f);
	}
	
	@Test
	public void getMostConnectedDeviceListByYearTest() throws Exception {
		List<DeviceUsageRate> rateList = Arrays.asList(rate21, rate22, rate13);
		when(deviceUsageRateService.getMostConnectedDeviceListByYear()).thenReturn(rateList);
		ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get(DEVICE_USAGE_RATE_URI + "/most-connected-devices")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.devices", hasSize(rateList.size())));
		for (int i=0; i<rateList.size(); i++) {
			ra.andExpect(jsonPath(String.format("$.devices[%d].year", i))
					.value(rateList.get(i).getDeviceUsageRateId().getYear()))
				.andExpect(jsonPath(String.format("$.devices[%d].device_id", i))
					.value(rateList.get(i).getDeviceUsageRateId().getDevice().getDeviceId()))
				.andExpect(jsonPath(String.format("$.devices[%d].device_name", i))
					.value(rateList.get(i).getDeviceUsageRateId().getDevice().getDeviceName()))
				.andExpect(jsonPath(String.format("$.devices[%d].rate", i))
					.value(rateList.get(i).getRate()));
		}
	}
	
	@Test
	public void getMostConnectedDeviceByYearTest() throws Exception {
		int year = 2018;
		when(deviceUsageRateService.getMostConnectedDeviceByYear(year)).thenReturn(rate21);
		mockMvc.perform(MockMvcRequestBuilders.get(DEVICE_USAGE_RATE_URI + "/most-connected-devices/" + year)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result.year")
				.value(year))
		.andExpect(jsonPath("$.result.device_name")
				.value(rate21.getDeviceUsageRateId().getDevice().getDeviceName()))
		.andExpect(jsonPath("$.result.rate")
				.value(rate21.getRate()))
		;
	}
	
	@Test
	public void getMostConnectedYearByDeviceIdTest() throws Exception {
		Integer deviceId = 2;
		when(deviceUsageRateService.getMostConnectedYearByDeviceId(deviceId)).thenReturn(rate22);
		mockMvc.perform(MockMvcRequestBuilders.get(DEVICE_USAGE_RATE_URI + "/most-connected-years/" + deviceId)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result.year")
				.value(rate22.getDeviceUsageRateId().getYear()))
		.andExpect(jsonPath("$.result.device_name")
				.value(rate22.getDeviceUsageRateId().getDevice().getDeviceName()))
		.andExpect(jsonPath("$.result.rate")
				.value(rate22.getRate()))
		;
	}
	
	@Test
	public void predictConnectedRateTest() throws Exception {
	    int year = 2019;
	    Integer deviceId = 2;
	    DeviceUsageRate predicted = new DeviceUsageRate(
                new DeviceUsageRate.DeviceUsageRateId(year, device2), 90.01f);
        when(deviceUsageRateService.predictConnectedRate(year, deviceId)).thenReturn(predicted);
        mockMvc.perform(MockMvcRequestBuilders.get(DEVICE_USAGE_RATE_URI + "/predictions/" + year + "/" + deviceId)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.year")
                .value(predicted.getDeviceUsageRateId().getYear()))
        .andExpect(jsonPath("$.device_name")
                .value(predicted.getDeviceUsageRateId().getDevice().getDeviceName()))
        .andExpect(jsonPath("$.rate")
                .value(predicted.getRate()))
        ;
	}
}
