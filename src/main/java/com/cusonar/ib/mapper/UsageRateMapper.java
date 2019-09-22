package com.cusonar.ib.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.UsageRate;

@Mapper
public interface UsageRateMapper {
    
	UsageRate selectUsageRate(int year);
    int insertUsageRate(UsageRate usageRate);
}
