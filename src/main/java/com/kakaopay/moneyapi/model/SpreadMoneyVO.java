package com.kakaopay.moneyapi.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
public class SpreadMoneyVO {

	private long userId;
	private String roomId;
	private long amount;
	private long totalCnt;
	
}
