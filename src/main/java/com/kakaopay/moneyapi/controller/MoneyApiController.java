package com.kakaopay.moneyapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.moneyapi.entity.SpreadInfo;
import com.kakaopay.moneyapi.entity.TakeMoneyInfo;
import com.kakaopay.moneyapi.model.ApiResponse;
import com.kakaopay.moneyapi.model.ResponseData;
import com.kakaopay.moneyapi.model.SpreadMoneyVO;
import com.kakaopay.moneyapi.service.MoneyApiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@Slf4j
public class MoneyApiController {
	
//	@Autowired
//	private MoneyApiService moneyApiService;
	
	private final MoneyApiService moneyApiService;
	
	

	@PostMapping("/spread")
	public ApiResponse throwMoney(
			@RequestHeader(value="X-USER-ID") long userId,
			@RequestHeader(value="X-ROOM-ID") String roomId,
			@RequestBody SpreadMoneyVO spreadMoney
			)
	
	{
		spreadMoney.setUserId(userId);
		spreadMoney.setRoomId(roomId);

		ResponseData responseData = new ResponseData(moneyApiService.spread(spreadMoney));
		
		log.info("CREATE TOKEN : {}", responseData.getToken());
		return new ApiResponse(responseData);
	}
	
	@PutMapping("/take/{token}")
	public ApiResponse takeMoneyByToken(
			@RequestHeader(value="X-USER-ID") long userId,
			@RequestHeader(value="X-ROOM-ID") String roomId,
			@PathVariable("token") String token
			){
		
		TakeMoneyInfo takeMoneyInfo = moneyApiService.takeMoneyByToken(userId, roomId, token);
		
		ResponseData responseData = new ResponseData(takeMoneyInfo.getTakeAmount());
		
		log.info("TAKE MONEY : {} {} {}", responseData.getToken(), takeMoneyInfo.getReceiverId(), responseData.getTakeAmount());
		return new ApiResponse(responseData);
	}
	
	@GetMapping("/spread/{token}")
	public ApiResponse throwMoneyInfo(
			@RequestHeader(value="X-USER-ID") long userId,
			@RequestHeader(value="X-ROOM-ID") String roomId,
			@PathVariable("token") String token) {
		
		SpreadInfo spreadInfo = moneyApiService.getSpreadInfo(userId, roomId, token);	
		
		ResponseData responseData = new ResponseData(spreadInfo);
		
		log.info("SPREAD INFO : {} {} {} {}", spreadInfo.getToken(), spreadInfo.getRoomId(), spreadInfo.getOwnerId(), spreadInfo.getCreatedTime());
		return new ApiResponse(responseData);
	}
}
