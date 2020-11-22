package com.kakaopay.moneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TakeMoneyInfoVO {
	
	private Long takeAmount;
	private Long receiverId;

}
