package com.cusonar.ib;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cusonar.ib.common.PreLoadService;
import com.cusonar.ib.device.DeviceService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class IbUsageInfoServiceApplication implements CommandLineRunner {
	
	private final PreLoadService preLoadService;
	
	public static void main(String[] args) {
		SpringApplication.run(IbUsageInfoServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	    preLoadService.loadData();
	}
}
