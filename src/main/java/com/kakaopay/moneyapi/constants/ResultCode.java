package com.kakaopay.moneyapi.constants;

import lombok.Getter;

@Getter
public enum ResultCode {
    E1000(1000, "잘못된 요청 입니다."),
    E1001(1001, "금액이 인원수 보다 작습니다."),

    E1100(1100, "존재하지 않거나 이미 종료된 뿌리기 입니다."),
    E1101(1101, "본인이 한 뿌리기는 받을 수 없습니다."),
    E1102(1102, "이미 받았습니다."),
    E1103(1103, "뿌리기가 모두 종료되었습니다."),
    
    E1200(1200, "유효하지 않는 토큰입니다."),
    
    E9999(9999, "관리자에게 문의해주세요.")
    ;

    private int code;
    private String description;

    ResultCode(int code, String description){
        this.code = code;
        this.description = description;
    }
}
