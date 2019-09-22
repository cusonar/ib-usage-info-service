package com.cusonar.ib.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cusonar.ib.MapperTest;
import com.cusonar.ib.domain.UsageRate;

public class UsageRateMapperTest extends MapperTest {

	UsageRate usageRate;
	@Autowired UsageRateMapper mapper;
	
	@Before
	public void setup() {
		usageRate = new UsageRate(2019, 50.05f);
	}
	
	@Test
	public void selectUsageRateTest() {
		mapper.insertUsageRate(usageRate);
		UsageRate selected = mapper.selectUsageRate(usageRate.getYear());
		assertEquals(usageRate, selected);
	}
	
	@Test
	public void insertUsageRateTest() {
		mapper.insertUsageRate(usageRate);
		UsageRate inserted = mapper.selectUsageRate(usageRate.getYear());
		assertEquals(usageRate, inserted);
	}
}
