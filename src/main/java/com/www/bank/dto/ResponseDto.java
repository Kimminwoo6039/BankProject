package com.www.bank.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResponseDto<T> {

    private final Integer code; /// TDOO : 1 성공 , -1 실패
    private final String msg;
    private final T data;

}
