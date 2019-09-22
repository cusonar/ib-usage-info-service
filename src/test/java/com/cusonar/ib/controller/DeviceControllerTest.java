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
import com.cusonar.ib.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeviceControllerTest {
	static final String DEVICE_URI = "/api/devices";
	ObjectMapper jsonMapper = new ObjectMapper();
	
	@Mock
    DeviceService deviceService;
	
    @InjectMocks
    DeviceController deviceController;
    
    MockMvc mockMvc;
    
    Device device1, device2, device3;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(deviceController)
				.setControllerAdvice(new ExceptionController())
				.alwaysDo(print())
				.build();
		device1 = new Device(1, "device1");
		device2 = new Device(2, "device2");
		device3 = new Device(3, "device3");
	}
	
	@Test
	public void getDeviceListTest() throws Exception {
		List<Device> deviceList = Arrays.asList(device1, device2, device3);
		when(deviceService.getDeviceList()).thenReturn(deviceList);
		ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get(DEVICE_URI)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.devices", hasSize(deviceList.size())));
		for (int i=0; i<deviceList.size(); i++) {
			ra.andExpect(jsonPath(String.format("$.devices[%d].device_id", i))
					.value(deviceList.get(i).getDeviceId()))
				.andExpect(jsonPath(String.format("$.devices[%d].device_name", i))
					.value(deviceList.get(i).getDeviceName()));
		}
	}
}
