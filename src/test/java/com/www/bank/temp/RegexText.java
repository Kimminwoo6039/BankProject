package com.www.bank.temp;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

//java.util.regex.Pattern
public class RegexText {

    @Test
    public void 한글만된다() throws Exception {

        String value = "한글";
        boolean matches = Pattern.matches("^[가-힣]+$", value);  /// TODO [] 바깥에 ^ =시작 , $ = 끝
        System.out.println("matches = " + matches);

    }

    @Test
    public void 한글은_안된다() throws Exception {

        String value = "abc";
        boolean matches = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value); /// TODO [] 안에 ^ = 반대 (부정), 아무것도 없을때 + 대신 * 수정
        System.out.println("matches = " + matches);
    }

    @Test
    public void 영어만된다() throws Exception {

        String value = "test";
        boolean matches = Pattern.matches("^[a-zA-Z]+$", value); /// TODO [] 안에 ^ = 반대 (부정), 아무것도 없을때 + 대신 * 수정
        System.out.println("matches = " + matches);

    }

    @Test
    public void 영어는안된다() throws Exception {

        String value = "가가213213dsa";
        boolean matches = Pattern.matches("^[^a-zA-Z]*$", value); /// TODO [] 안에 ^ = 반대 (부정), 아무것도 없을때 + 대신 * 수정
        System.out.println("matches = " + matches);
    }

    @Test
    public void 영어와숫자만된다() throws Exception {

        String value = "dasd213";
        boolean matches = Pattern.matches("^[a-zA-Z0-9]+$", value); /// TODO [] 안에 ^ = 반대 (부정), 아무것도 없을때 + 대신 * 수정
        System.out.println("matches = " + matches);

    }

    @Test
    public void 영어만되고_길이는최소2최대4() throws Exception {

        String value = "s2";
        boolean matches = Pattern.matches("^[a-zA-Z]{2,4}$", value); /// TODO [] 안에 ^ = 반대 (부정), 아무것도 없을때 + 대신 * 수정
        System.out.println("matches = " + matches);
    }

    // username,email,fullname

    @Test
    public void user_name테스트() throws Exception {
        String username = "";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("result = " + result);
    }

    @Test
    public void full_name테스트() throws Exception {
        String fullname = "테스트";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", fullname);
        System.out.println("result = " + result);
    }

    @Test
    public void email_테스트() throws Exception {
        String email = "TEST@naver.com"; // ac.kr , or.kr
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,6}@[a-zA-Z0-9]{2,6}\\.[a-zA-Z]{2,3}$", email); /// TODO : . 앞에 \\ 붙여야함.
        System.out.println("result = " + result);
    }
}
