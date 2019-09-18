package com.cusonar.ib.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.device.DeviceService;
import com.cusonar.ib.deviceusagerate.DeviceUsageRateService;
import com.cusonar.ib.domain.Device;
import com.cusonar.ib.domain.DeviceUsageRate;
import com.cusonar.ib.domain.UsageRate;
import com.cusonar.ib.usagerate.UsageRateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreLoadService {

    @Value("${ib.pre.loader.data.path}") private String IB_PRE_LOADER_DATA_PATH;
    private static final String COMMA_DELIMITER = ",";
    
    private final DeviceService deviceService;
    private final UsageRateService usageRateService;
    private final DeviceUsageRateService deviceUsageRateService;
    
    @Transactional
    public void loadData() throws IOException {
      try (BufferedReader br = new BufferedReader(new FileReader(IB_PRE_LOADER_DATA_PATH))) {
          String line;
          line = br.readLine();
          
          String[] values = line.split(COMMA_DELIMITER);
          List<Device> deviceList = new ArrayList<>();
          for (int i=2; i<values.length; i++) {
              deviceList.add(new Device(values[i]));
          }
          deviceService.saveDeviceList(deviceList);
          
          List<UsageRate> usageRateList = new ArrayList<>();
          List<DeviceUsageRate> deviceUsageRateList = new ArrayList<>();
          while ((line = br.readLine()) != null) {
              line = line.replaceAll("-", "0.0");
              values = line.split(COMMA_DELIMITER);
              int year = Integer.parseInt(values[0]);
              usageRateList.add(new UsageRate(year, Float.parseFloat(values[1])));
              //수정필요
              deviceUsageRateList.add(new DeviceUsageRate(year, new Device(1), Float.parseFloat(values[2])));
              deviceUsageRateList.add(new DeviceUsageRate(year, new Device(2), Float.parseFloat(values[3])));
              deviceUsageRateList.add(new DeviceUsageRate(year, new Device(3), Float.parseFloat(values[4])));
              deviceUsageRateList.add(new DeviceUsageRate(year, new Device(4), Float.parseFloat(values[5])));
              deviceUsageRateList.add(new DeviceUsageRate(year, new Device(5), Float.parseFloat(values[6])));
          }
          usageRateService.saveUsageRateList(usageRateList);
          deviceUsageRateService.saveDeviceUsageRateList(deviceUsageRateList);          
      }
    }
}
