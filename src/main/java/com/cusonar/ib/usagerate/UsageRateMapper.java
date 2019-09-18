package com.cusonar.ib.usagerate;

import org.apache.ibatis.annotations.Mapper;

import com.cusonar.ib.domain.UsageRate;

@Mapper
public interface UsageRateMapper {
    
    int insertUsageRate(UsageRate usageRate);
}
