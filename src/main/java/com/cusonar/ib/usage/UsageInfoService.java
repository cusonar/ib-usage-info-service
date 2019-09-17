package com.cusonar.ib.usage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
public class UsageInfoService {

	@Autowired private UsageInfoMapper usageInfoMapper;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void saveUsageInfos(List<UsageInfo> usageInfos) {
		for (UsageInfo usageInfo : usageInfos) {
			usageInfoMapper.insertUsageInfo(usageInfo);
		}
	}
	
}
