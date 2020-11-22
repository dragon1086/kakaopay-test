package com.kakaopay.moneyapi.model;

import java.util.ArrayList;
import java.util.List;

import com.kakaopay.moneyapi.entity.SpreadInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResponseData {
	
	private String token;
	private Long takeAmount;
	private String createdTime;
	private Long amount;
	private List<TakeMoneyInfoVO> takeMoneyInfos;
	
	public ResponseData(String token) {
		this.token = token;
	}
	
	public ResponseData(Long takeAmount) {
		this.takeAmount = takeAmount;
	}

	public ResponseData(SpreadInfo spreadInfo) {
		this.createdTime = spreadInfo.getCreatedTime().toString();
		this.amount = spreadInfo.getAmount();
		this.takeAmount = 0L;
		
		this.takeMoneyInfos = new ArrayList<TakeMoneyInfoVO>();
		spreadInfo.getTakeMoneyList().forEach((t) -> {
			if(t.getReceiverId() != null) {
				this.takeAmount += t.getTakeAmount();
				takeMoneyInfos.add(new TakeMoneyInfoVO(t.getTakeAmount(), t.getReceiverId()));
			}
		});
	}
}
