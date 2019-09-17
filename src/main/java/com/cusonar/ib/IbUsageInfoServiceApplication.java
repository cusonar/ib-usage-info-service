package com.cusonar.ib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cusonar.ib.usage.UsageInfo;
import com.cusonar.ib.usage.UsageInfoMapper;
import com.cusonar.ib.usage.UsageInfoService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class IbUsageInfoServiceApplication implements CommandLineRunner {

	@Value("${ib.pre.loader.data.path}") private String IB_PRE_LOADER_DATA_PATH;
	private static final String COMMA_DELIMITER = ",";
	
	@Autowired private UsageInfoMapper usageInfoMapper;
	
	public static void main(String[] args) {
		SpringApplication.run(IbUsageInfoServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UsageInfo.UsageInfoBuilder builder = UsageInfo.builder();
		List<UsageInfo> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(IB_PRE_LOADER_DATA_PATH))) {
		    String line;
		    br.readLine();
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(COMMA_DELIMITER);
		        UsageInfo usageInfo = builder.year(Integer.parseInt(values[0]))
		        		.rateOfTotalUsage(Float.parseFloat(values[1]))
		        		.rateOfSmartphone(Float.parseFloat(values[2]))
		        		.rateOfDesktop(Float.parseFloat(values[3]))
		        		.rateOfLaptop(Float.parseFloat(values[4]))
		        		.rateOfEtc(Float.parseFloat(values[5]))
		        		.rateOfSmartpad(Float.parseFloat(values[2])).build();
		        System.out.println(usageInfo);
//		        records.add(usageInfo);
		        usageInfoMapper.insertUsageInfo(usageInfo);
		    }
//		    usageInfoService.saveUsageInfos(records);
		}
	}
	
//	public <T> List<T> loadObjectList(Class<T> type, String fileName) {
//	    try {
//	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
//	        CsvMapper mapper = new CsvMapper();
//	        File file = new File(IB_PRE_LOADER_DATA_PATH);
//	        MappingIterator<T> readValues = mapper.reader(bootstrapSchema).readValues(file);
//	        return readValues.readAll();
//	    } catch (Exception e) {
//	        log.error("Error occurred while loading object list from file " + fileName, e);
//	        return Collections.emptyList();
//	    }
//	}

}
