package com.kakaopay.moneyapi.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "unique_spreadInfo_id_receiverId", columnList = "spreadInfo_id, receiverId", unique = true)
})
public class TakeMoneyInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spreadInfo_id")
	private SpreadInfo spreadInfo;
	
	@Column(nullable = false)
	private long takeAmount;
	
	private Long receiverId;
	private LocalDateTime receiveTime;
	
	public TakeMoneyInfo(SpreadInfo spreadInfo, Long receiverId, Long amount) {
		this.spreadInfo = spreadInfo;
		this.receiverId = receiverId;
		this.takeAmount = amount;
	}
	
	public long take(long receiverId) {
		this.receiverId = receiverId;
		this.receiveTime = LocalDateTime.now();
		return takeAmount;
	}
	
	
}
