package com.cusonar.ib.usage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsageInfo {

	private Integer year;
	private float rateOfTotalUsage;
	private float rateOfSmartphone;
	private float rateOfDesktop;
	private float rateOfLaptop;
	private float rateOfEtc;
	private float rateOfSmartpad;
}