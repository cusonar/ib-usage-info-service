package com.cusonar.ib.service;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.mapper.DeviceUsageRateMapper;
import com.cusonar.ib.util.prediction.LinearRegression;

public class DeviceUsageRateServiceTest {

	DeviceUsageRate rate11, rate12, rate13, rate21, rate22, rate23;
	List<DeviceUsageRate> rateList;
	Device device1, device2;

	@Mock
	DeviceUsageRateMapper mapper;
	@Mock
	LinearRegression linearRegression;
	
	@InjectMocks
	DeviceUsageRateService service;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		device1 = new Device(1, "device1");
		device2 = new Device(2, "device2");
		
		rate11 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2019, device1), 10.02f);
		rate12 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2018, device1), 20.06f);
		rate13 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2017, device1), 50.05f);
		rate21 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2019, device2), 17.02f);
		rate22 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2018, device2), 41.08f);
		rate23 = new DeviceUsageRate(
					new DeviceUsageRate.DeviceUsageRateId(2017, device2), 19.02f);
		
		rateList = Arrays.asList(rate11, rate12, rate13, rate21, rate22, rate23);
	}
	
	@Test
	public void getDeviceUsageRateListWithDeviceTest() {
		when(mapper.selectDeviceUsageRateListWithDevice()).thenReturn(rateList);
		
		List<DeviceUsageRate> selected = service.getDeviceUsageRateListWithDevice();
		assertArrayEquals(rateList.toArray(), selected.toArray());
	}
	
	@Test
	public void getMostConnectedDeviceListByYearTest() {
		when(mapper.selectDeviceUsageRateListWithDevice()).thenReturn(rateList);
		
		List<DeviceUsageRate> mostConnectedDeviceList = service.getMostConnectedDeviceListByYear();
		assertEquals(3, mostConnectedDeviceList.size());
		assertThat(mostConnectedDeviceList, hasItem(rate21));
		assertThat(mostConnectedDeviceList, hasItem(rate22));
		assertThat(mostConnectedDeviceList, hasItem(rate13));
	}
	
	@Test
	public void getMostConnectedDeviceByYearTest() {
		when(mapper.selectDeviceUsageRateListWithDevice()).thenReturn(rateList);
		
		DeviceUsageRate mostConnectedDevice = service.getMostConnectedDeviceByYear(2019);
		assertEquals(rate21, mostConnectedDevice);
	}
	
	@Test
	public void getMostConnectedYearByDeviceIdTest() {
		when(mapper.selectDeviceUsageRateListWithDevice()).thenReturn(rateList);
		DeviceUsageRate mostConnectedYear = service.getMostConnectedYearByDeviceId(device1.getDeviceId());
		assertEquals(rate13, mostConnectedYear);
		mostConnectedYear = service.getMostConnectedYearByDeviceId(device2.getDeviceId());
		assertEquals(rate22, mostConnectedYear);
	}
	
	@Test
	public void predictConnectedRateByDeviceIdTest() {
		Integer predictYear = 2019;
		when(mapper.selectDeviceUsageRateListWithDevice()).thenReturn(rateList);
		DeviceUsageRate predicted = service.predictConnectedRateByDeviceId(device1.getDeviceId());
		assertEquals((double) predictYear, predicted.getDeviceUsageRateId().getYear(), Double.MIN_VALUE);
		assertEquals(device1.getDeviceId(), 
				predicted.getDeviceUsageRateId().getDevice().getDeviceId());
		assertEquals(device1.getDeviceName(),
				predicted.getDeviceUsageRateId().getDevice().getDeviceName());
		assertNotNull(predicted.getRate());
	}
}
