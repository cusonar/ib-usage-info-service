package com.cusonar.ib.usagerate;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusonar.ib.domain.UsageRate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsageRateService {
    
    private final UsageRateMapper usageRateMapper;

    @Transactional
    public void saveUsageRateList(List<UsageRate> usageRateList) {
        for (UsageRate usageRate : usageRateList) {
            usageRateMapper.insertUsageRate(usageRate);
        }
    }
}
