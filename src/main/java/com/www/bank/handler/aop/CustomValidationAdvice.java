package com.www.bank.handler.aop;

import com.www.bank.dto.ResponseDto;
import com.www.bank.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component //메모리띄우고
@Aspect // 관점
public class CustomValidationAdvice {


    /// TODO : 어디 메서더를 자동으로 구현하고 싶을때 ?  Pointcut
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    /// TODO : 어디 메서더를 자동으로 구현하고 싶을때 ?  Pointcut
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){}
    
    @Around("postMapping() || putMapping()") /// TODO : 2군데에다가 ADVICE 하겟다. @before,@after 가능
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); /// TODO : args = > joinpoint 매개변수 읽기
        for (Object arg : args) {

            if (arg instanceof BindingResult) { /// TODO : Valid 에서 실패하면 BindResult 에 값이 들어오니깐
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String,String> erroMap = new HashMap<>();

                    for (FieldError error : bindingResult.getFieldErrors()) {
                        erroMap.put(error.getField(), error.getDefaultMessage());
                    }
                        throw new CustomValidationException("유효성 검사 실패",erroMap);
                }
            }

        }
            return proceedingJoinPoint.proceed(); /// TODO : 정상적으로 해당 메서드 실행
    }
}

/**
 * get,delete,post (body),put(body)
 */
