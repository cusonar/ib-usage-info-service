package com.cusonar.ib.service.dto;



import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;

public class DeviceUsageRateDtoTest {

	Device device1;
	DeviceUsageRate.DeviceUsageRateId id;
	DeviceUsageRate rate;
	
	@Before
	public void setup() {
		device1 = new Device(1, "device1");
		id = new DeviceUsageRate.DeviceUsageRateId(2019, device1);
		rate = new DeviceUsageRate(id, 10.08f);
	}
	
	@Test
	public void responseOfTest() {
		DeviceUsageRateDto.Response response = DeviceUsageRateDto.Response.of(rate);
		assertEquals(id.getYear(), response.getYear());
		assertEquals(device1.getDeviceId(), response.getDeviceId());
		assertEquals(device1.getDeviceName(), response.getDeviceName());
		assertEquals(rate.getRate(), response.getRate(), Float.MIN_VALUE);
	}
	
	@Test
	public void responseOneOfTest() {
		DeviceUsageRateDto.ResponseOne responseOne = DeviceUsageRateDto.ResponseOne.of(rate);
		assertEquals(id.getYear(), responseOne.getYear());
		assertEquals(device1.getDeviceName(), responseOne.getDeviceName());
		assertEquals(rate.getRate(), responseOne.getRate(), Float.MIN_VALUE);
	}
}
