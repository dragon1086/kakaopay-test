package com.kakaopay.moneyapi.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import com.kakaopay.moneyapi.constants.ResultCode;
import com.kakaopay.moneyapi.entity.SpreadInfo;
import com.kakaopay.moneyapi.exception.MoneyApiException;
import com.kakaopay.moneyapi.model.ApiResponse;
import com.kakaopay.moneyapi.model.SpreadMoneyVO;
import com.kakaopay.moneyapi.service.MoneyApiService;

@SpringBootTest
public class MoneyApiControllerTest {
	
	@Autowired
	MoneyApiService moneyApiService;
	
	
	private MoneyApiController moneyApiContoller;
	
	@BeforeEach
	void init() {
		moneyApiContoller = new MoneyApiController(moneyApiService);
	}
	
	@Test
	void moneyApi_spread_success() {
		
		SpreadMoneyVO spreadMoney = SpreadMoneyVO.builder()
				.roomId("abc")
				.userId(123)
				.totalCnt(10)
				.amount(100)
				.build();
		
		ApiResponse apiResponse = moneyApiContoller.throwMoney(123, "abc", spreadMoney);
		assertEquals(apiResponse.getDescription(), "SUCCESS");
	}
	
	@Test
	void moneyApi_spread_fail1() {
		
		SpreadMoneyVO spreadMoney = SpreadMoneyVO.builder()
				.roomId("abc")
				.userId(123)
				.totalCnt(0)
				.amount(1000)
				.build();
		
		try {
			ApiResponse apiResponse = moneyApiContoller.throwMoney(123, "abc", spreadMoney);
		} catch (MoneyApiException e) {
			assertEquals(e.getDescription(), ResultCode.E1000.getDescription());
		}
	}
	
	@Test
	void moneyApi_spread_fail2() {
		SpreadMoneyVO spreadMoney = SpreadMoneyVO.builder()
				.roomId("abc")
				.userId(123)
				.totalCnt(1000)
				.amount(100)
				.build();
		
		try {
			ApiResponse apiResponse = moneyApiContoller.throwMoney(123, "abc", spreadMoney);
		} catch (MoneyApiException e) {
			assertEquals(e.getDescription(), ResultCode.E1001.getDescription());
		}
	}
	
	@Test
	void moneyApi_takeMoney_fail() {
		try {
			ApiResponse apiResponse = moneyApiContoller.takeMoneyByToken(123, "abc", "tmp");
		} catch (MoneyApiException e) {
			assertEquals(e.getDescription(), ResultCode.E1100.getDescription());
		}
	}
	
	@Test
	void moneyApi_spreadInfo_fail() {
		try {
			ApiResponse apiResponse = moneyApiContoller.throwMoneyInfo(123, "abc", "tmp");
		} catch (MoneyApiException e) {
			assertEquals(e.getDescription(), ResultCode.E1200.getDescription());
		}
	}
}
