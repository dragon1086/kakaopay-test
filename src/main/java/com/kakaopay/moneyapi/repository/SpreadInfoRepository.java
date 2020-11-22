package com.kakaopay.moneyapi.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.moneyapi.entity.SpreadInfo;

public interface SpreadInfoRepository extends JpaRepository<SpreadInfo, Long>{
	
	Optional<SpreadInfo> findByTokenAndRoomIdAndCreatedTimeAfter(String token, String roomId, LocalDateTime createdTime);
	Optional<SpreadInfo> findByTokenAndRoomIdAndOwnerIdAndCreatedTimeAfter(String token, String roomId, Long ownerId, LocalDateTime createdTime);
	int countByTokenAndRoomId(String token, String roomId);
}
