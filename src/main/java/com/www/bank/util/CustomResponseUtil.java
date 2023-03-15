package com.www.bank.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.www.bank.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

public class CustomResponseUtil {

    private final static Logger log = LoggerFactory.getLogger(CustomResponseUtil.class);

    public static void unAuthentication(HttpServletResponse response,String msg) {  /// TODO : 인증이 안됨
        try {
            ObjectMapper om = new ObjectMapper(); /// TODO : JSON 형태로 변환하기 위해 ObjectMapper 를 사용
            ResponseDto<?> responseDto = new ResponseDto<>(-1,msg,null);
            String responseBody = om.writeValueAsString(responseDto); /// TODO : jSON 형태 변환
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(401);
            response.getWriter().println(responseBody); /// TODO :  예쁘게 메시지를 포장하는 공통적인 응답 Dto 를 만들어줌
        } catch (Exception e) {
            log.error("서버 파싱에러");
        }

    }


    public static void unAuthorization(HttpServletResponse response,String msg) {  /// TODO : 인증이 안됨
        try {
            ObjectMapper om = new ObjectMapper(); /// TODO : JSON 형태로 변환하기 위해 ObjectMapper 를 사용
            ResponseDto<?> responseDto = new ResponseDto<>(-1,msg,null);
            String responseBody = om.writeValueAsString(responseDto); /// TODO : jSON 형태 변환
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(403);
            response.getWriter().println(responseBody); /// TODO :  예쁘게 메시지를 포장하는 공통적인 응답 Dto 를 만들어줌
        } catch (Exception e) {
            log.error("서버 파싱에러");
        }

    }
}
