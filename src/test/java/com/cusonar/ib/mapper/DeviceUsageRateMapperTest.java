package com.cusonar.ib.mapper;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cusonar.ib.MapperTest;
import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;

public class DeviceUsageRateMapperTest extends MapperTest {

	DeviceUsageRate rate1, rate2, rate3;
	Device device1;
	@Autowired DeviceUsageRateMapper mapper;
	@Autowired DeviceMapper deviceMapper;
	
	@Before
	public void setup() {
		device1 = new Device("device1");
		deviceMapper.insertDevice(device1);
		
		rate1 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2019, device1), 10.02f);
		rate2 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2018, device1), 20.06f);
		rate3 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2017, device1), 50.05f);
	}
	
	@Test
	public void selectDeviceUsageRateListWithDeviceTest() {
		mapper.insertDeviceUsageRate(rate1);
		mapper.insertDeviceUsageRate(rate2);
		mapper.insertDeviceUsageRate(rate3);
		
		List<DeviceUsageRate> list = mapper.selectDeviceUsageRateListWithDevice();
		assertEquals(3, list.size());
		assertThat(list, hasItem(rate1));
		assertThat(list, hasItem(rate2));
		assertThat(list, hasItem(rate3));
	}
	
	@Test
	public void selectDeviceUsageRateWithDeviceTest() {
		mapper.insertDeviceUsageRate(rate1);
		DeviceUsageRate selected = mapper.selectDeviceUsageRateWithDevice(
				rate1.getDeviceUsageRateId());
		assertEquals(rate1, selected);
	}
	
	@Test
	public void insertDeviceUsageRateTest() {
		mapper.insertDeviceUsageRate(rate1);
		DeviceUsageRate inserted = mapper.selectDeviceUsageRateWithDevice(
				rate1.getDeviceUsageRateId());
		assertEquals(rate1, inserted);
	}
}
