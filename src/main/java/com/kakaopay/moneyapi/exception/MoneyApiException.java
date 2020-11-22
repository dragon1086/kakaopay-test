package com.kakaopay.moneyapi.exception;

import com.kakaopay.moneyapi.constants.ResultCode;

import lombok.Getter;

@Getter
public class MoneyApiException extends RuntimeException{
	
	private int exceptionCode;
	private String description;
	
	
	public MoneyApiException(ResultCode resultCode) {
		this.exceptionCode = resultCode.getCode();
		this.description = resultCode.getDescription();
	}
}
