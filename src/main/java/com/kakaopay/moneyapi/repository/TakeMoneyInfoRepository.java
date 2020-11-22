package com.kakaopay.moneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kakaopay.moneyapi.entity.TakeMoneyInfo;

public interface TakeMoneyInfoRepository extends JpaRepository<TakeMoneyInfo, Long> {

}
