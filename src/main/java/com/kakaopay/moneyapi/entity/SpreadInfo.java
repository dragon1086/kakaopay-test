package com.kakaopay.moneyapi.entity;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

import com.kakaopay.moneyapi.model.SpreadMoneyVO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@Table(indexes= {
		@Index(name="unique_token_room_id", columnList = "token, roomId", unique = true)
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpreadInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 3)
	private String token;
	
	@Column(nullable = false)
	private String roomId;
	
	@Column(nullable = false)
	private Long ownerId;
	
	@Column(nullable = false)
	private Long amount;
	
	@Column(nullable = false)
	private Long totalCnt;
	
	@Column(nullable = false)
	private LocalDateTime createdTime;

	@OneToMany(mappedBy = "spreadInfo", cascade = CascadeType.ALL)
	private List<TakeMoneyInfo> takeMoneyList = new ArrayList<>();
	
	
	public SpreadInfo(String token, SpreadMoneyVO spreadMoney) {
		this.token = token;
		this.roomId = spreadMoney.getRoomId();
		this.ownerId = spreadMoney.getUserId();
		this.amount = spreadMoney.getAmount();
		this.totalCnt = spreadMoney.getTotalCnt();
		this.createdTime = LocalDateTime.now();
				
	}
}
