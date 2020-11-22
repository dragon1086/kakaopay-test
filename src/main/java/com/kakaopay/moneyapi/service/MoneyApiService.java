package com.kakaopay.moneyapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.moneyapi.constants.ResultCode;
import com.kakaopay.moneyapi.constants.VariableConst;
import com.kakaopay.moneyapi.entity.SpreadInfo;
import com.kakaopay.moneyapi.entity.TakeMoneyInfo;
import com.kakaopay.moneyapi.exception.MoneyApiException;
import com.kakaopay.moneyapi.model.SpreadMoneyVO;
import com.kakaopay.moneyapi.repository.SpreadInfoRepository;
import com.kakaopay.moneyapi.repository.TakeMoneyInfoRepository;
import com.kakaopay.moneyapi.utils.RandomUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MoneyApiService {
	
//	@Autowired
//	private SpreadInfoRepository spreadInfoRepository;
//	
//	@Autowired
//	private TakeMoneyInfoRepository takeMoneyInfoRepository;
	
	private final SpreadInfoRepository spreadInfoRepository;
	private final TakeMoneyInfoRepository takeMoneyInfoRepository;

	@Transactional
	public String spread(SpreadMoneyVO spreadMoney) throws MoneyApiException{
		
		if(spreadMoney.getAmount() < 1 || spreadMoney.getTotalCnt() < 1) {
			throw new MoneyApiException(ResultCode.E1000);
		} else if(spreadMoney.getAmount() < spreadMoney.getTotalCnt()) {
			throw new MoneyApiException(ResultCode.E1001);
		}
		
		String token = createToken(spreadMoney);
		SpreadInfo spreadInfo = new SpreadInfo(token, spreadMoney);
		spreadInfo = spreadInfoRepository.save(spreadInfo);
		log.info("SAVED TOKEN INFO {}" , spreadInfo.toString());
		
		takeMoneyInfoRepository.saveAll(divideMoney(spreadInfo));
		
		return token;
	}
	

	@Transactional
	public TakeMoneyInfo takeMoneyByToken(long userId, String roomId, String token) {
		
		LocalDateTime time = LocalDateTime.now();
		time = time.minusMinutes(VariableConst.TOKEN_LIMIT_MINUTES);
		
		Optional<SpreadInfo> spreadInfo = spreadInfoRepository.findByTokenAndRoomIdAndCreatedTimeAfter(token, roomId, time);
		
		// Token does not exist
		spreadInfo.orElseThrow(() -> new MoneyApiException(ResultCode.E1100));
		
		// Token owner and receiver are the same
		if(spreadInfo.get().getOwnerId().equals(userId)) {
			throw new MoneyApiException(ResultCode.E1101);
		}
		
		TakeMoneyInfo takeMoneyInfo = null;
		for(TakeMoneyInfo take : spreadInfo.get().getTakeMoneyList()) {
			if(take.getReceiverId() != null && take.getReceiverId().equals(userId)) {
				// Already Received the money
				throw new MoneyApiException(ResultCode.E1102);
			} else if (take.getReceiverId() == null && takeMoneyInfo == null) {
				takeMoneyInfo = take;
			}
		}

		if(takeMoneyInfo == null) {
			// All of amount are taken
			throw new MoneyApiException(ResultCode.E1103);
		}
		
		takeMoneyInfo.take(userId);
		log.info("{} get take {} , token : {}", takeMoneyInfo.getReceiverId(), takeMoneyInfo.getTakeAmount(), takeMoneyInfo.getSpreadInfo().getToken());
		
		return takeMoneyInfo;
	}



	public SpreadInfo getSpreadInfo(long userId, String roomId, String token) {

		LocalDateTime time = LocalDateTime.now();
		time = time.minusDays(VariableConst.SEARCH_LIMIT_DAYS);
		
		log.info("FIND BY PARAMS :: {} {} {} {}", token, roomId, userId, time);
		
		Optional<SpreadInfo> info = spreadInfoRepository.findByTokenAndRoomIdAndOwnerIdAndCreatedTimeAfter(token, roomId, userId, time);
		
		info.orElseThrow(()-> new MoneyApiException(ResultCode.E1200));
		
		return info.get();
	}
	
	
    public String createToken(SpreadMoneyVO spreadMoney){

        String token = RandomUtil.getRandomString(VariableConst.TOKEN_LENGTH);

        while(spreadInfoRepository.countByTokenAndRoomId(token, spreadMoney.getRoomId()) > 0) {
        	log.info("DUPLICATE TOKEN CHECK : {}",token);
        	token = RandomUtil.getRandomString(VariableConst.TOKEN_LENGTH);
        }
        
        return token;
    }
    
    public List<TakeMoneyInfo> divideMoney(SpreadInfo spreadInfo){

        List<TakeMoneyInfo> divs = new ArrayList<TakeMoneyInfo>();
        Long amount = spreadInfo.getAmount() - spreadInfo.getTotalCnt();
        Long spendAmount = 0l;

        for(long i=0; i<spreadInfo.getTotalCnt()-1; i++){
            Long takeAmount = 1l;
            if(amount-spendAmount > 2){
            	takeAmount += RandomUtil.getRandLong(amount-spendAmount);
            }
            divs.add(new TakeMoneyInfo(spreadInfo, null, takeAmount));
            spendAmount += takeAmount;
        }
        divs.add(new TakeMoneyInfo(spreadInfo, null, spreadInfo.getAmount() - spendAmount));

        return divs;
    }
}
