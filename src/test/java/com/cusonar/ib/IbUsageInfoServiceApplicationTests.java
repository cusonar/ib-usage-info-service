package com.cusonar.ib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cusonar.ib.core.exception.NotFoundException;
import com.cusonar.ib.deviceusagerate.DeviceUsageRateService;
import com.cusonar.ib.domain.DeviceUsageRate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IbUsageInfoServiceApplicationTests {

    @Autowired private DeviceUsageRateService service;
    
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void someTest() {
	    
	}
	
	

}
