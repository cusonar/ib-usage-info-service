package com.cusonar.ib.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cusonar.ib.domain.Device;
import com.cusonar.ib.mapper.DeviceMapper;

public class DeviceServiceTest {
	
	private Device device1, device2, device3;

	@Mock
	private DeviceMapper mapper;
	
	@InjectMocks
	private DeviceService service;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		device1 = new Device("device1");
		device2 = new Device("device2");
		device3 = new Device("device3");
	}
	
	@Test
	public void getDeviceListTest() {
		when(mapper.selectDeviceList()).thenReturn(Arrays.asList(device1, device2, device3));
		
		List<Device> deviceList = service.getDeviceList();
		
		assertEquals(3, deviceList.size());
		assertThat(deviceList, hasItem(device1));
		assertThat(deviceList, hasItem(device2));
		assertThat(deviceList, hasItem(device3));
	}
}
