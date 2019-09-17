package com.cusonar.ib.usage;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsageInfoMapper {

	@Insert("INSERT INTO usage_info VALUES (#{year}, #{rateOfTotalUsage}, #{rateOfSmartphone}, #{rateOfDesktop}, #{rateOfLaptop}, #{rateOfEtc}, #{rateOfSmartpad})")
	void insertUsageInfo(UsageInfo usageInfo);
}
