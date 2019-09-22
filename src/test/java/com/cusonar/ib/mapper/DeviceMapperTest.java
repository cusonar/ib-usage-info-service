package com.cusonar.ib.mapper;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cusonar.ib.MapperTest;
import com.cusonar.ib.domain.Device;

public class DeviceMapperTest extends MapperTest {
	
	Device device1, device2, device3;

	@Autowired DeviceMapper mapper;
	
	@Before
	public void setup() {
		device1 = new Device("device1");
		device2 = new Device("device2");
		device3 = new Device("device3");
	}
	
	@Test
	public void selectDeviceListTest() {
		mapper.insertDevice(device1);
		mapper.insertDevice(device2);
		mapper.insertDevice(device3);
		
		List<Device> deviceList = mapper.selectDeviceList();
		assertEquals(3, deviceList.size());
		assertThat(deviceList, hasItem(device1));
		assertThat(deviceList, hasItem(device2));
		assertThat(deviceList, hasItem(device3));
	}
	
	@Test
	public void selectDeviceTest() {
		mapper.insertDevice(device1);
		Device selected = mapper.selectDevice(device1.getDeviceId());
		assertEquals(device1, selected);
	}
	
	@Test
	public void insertDeviceTest() {
		mapper.insertDevice(device1);
		
		assertNotNull(device1.getDeviceId());
		
		Device inserted = mapper.selectDevice(device1.getDeviceId());
		assertEquals(device1, inserted);
	}
}
