package com.cusonar.ib.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsageRate implements Serializable {

	private static final long serialVersionUID = 8719412859598805835L;
	
	private int year;
	private float usageRate;
}