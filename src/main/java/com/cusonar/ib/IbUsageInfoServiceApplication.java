package com.cusonar.ib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.domain.UsageRate;
import com.cusonar.ib.mapper.DeviceMapper;
import com.cusonar.ib.mapper.DeviceUsageRateMapper;
import com.cusonar.ib.mapper.UsageRateMapper;
import com.cusonar.ib.util.csv.CsvUtil;

@SpringBootApplication
@EnableCaching
public class IbUsageInfoServiceApplication {
	
	@Value("${ib.pre.loader.data.path}") private String IB_PRE_LOADER_DATA_PATH;
		
	public static void main(String[] args) {
		SpringApplication.run(IbUsageInfoServiceApplication.class, args);
	}
	
	@Bean
	@Profile("!test")
	@Transactional
	public CommandLineRunner setup(
			DeviceMapper deviceMapper,
			UsageRateMapper usageRateMapper,
			DeviceUsageRateMapper deviceUsageRateMapper) {
		return (args) -> {
			CsvUtil csvUtil = new CsvUtil(IB_PRE_LOADER_DATA_PATH);
			List<Integer> deviceIdList = new ArrayList<>();
			
			csvUtil.readAndDoSomething(values -> {
				Arrays.asList(values).stream()
					.skip(2)
					.map(value -> new Device(value))
					.forEach(device -> {
						deviceMapper.insertDevice(device);
						deviceIdList.add(device.getDeviceId());
					});
			}, values -> {
				for (int i=0; i<values.length; i++) values[i] = values[i].replaceAll("-", "0.0");
				int year = Integer.parseInt(values[0]);
				usageRateMapper.insertUsageRate(new UsageRate(year, Float.parseFloat(values[1])));
				
				for (int i=0; i<values.length-2; i++) {
					DeviceUsageRate.DeviceUsageRateId id = new DeviceUsageRate.DeviceUsageRateId(year, new Device(deviceIdList.get(i)));
					deviceUsageRateMapper.insertDeviceUsageRate(
							new DeviceUsageRate(id, Float.parseFloat(values[i+2])));
				}
			});
		};
	}
}
