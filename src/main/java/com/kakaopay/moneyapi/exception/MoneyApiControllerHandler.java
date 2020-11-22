package com.kakaopay.moneyapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kakaopay.moneyapi.controller.MoneyApiController;
import com.kakaopay.moneyapi.model.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class MoneyApiControllerHandler {
	
	@ExceptionHandler(MoneyApiException.class)
	public ResponseEntity<ApiResponse> handleMoneyApiException(MoneyApiException e){
		log.error("MoneyApiControllerHandler Exception ] {}", e.getDescription());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.of(e));
	}
	
	public ResponseEntity<ApiResponse> handlerEtcException(Exception e){
		log.error("MoneyApiControllerHandler etc Excpetion ] {}", e.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.of(e));
	}
}
