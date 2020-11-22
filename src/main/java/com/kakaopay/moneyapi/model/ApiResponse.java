package com.kakaopay.moneyapi.model;

import com.kakaopay.moneyapi.constants.ResultCode;
import com.kakaopay.moneyapi.exception.MoneyApiException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ApiResponse {
	
	private boolean result;
	private String description;
	private ResponseData data;
	
    public ApiResponse(ResultCode code){
        result = code.getCode()==1000;
        description = code.getDescription();
    }
    
    public ApiResponse(MoneyApiException exp){
        result = exp.getExceptionCode() == 1000;
        description = exp.getDescription();
    }

    public ApiResponse(ResponseData data){
        result = true;
        description = "SUCCESS";
        this.data = data;
    }
    
    public static ApiResponse of(MoneyApiException e) {
    	return new ApiResponse(e);
    }
    
    public static ApiResponse of(Exception e) {
    	return new ApiResponse(ResultCode.E9999);
    }
}
