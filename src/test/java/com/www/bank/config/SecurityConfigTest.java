package com.www.bank.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.Id;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc /// TODO : 가짜 환경에서 MockMvc 등록됨
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)  /// TODO : 가짜 환경에서 테스트한다.
public class SecurityConfigTest {

    /// TODO : 가짜 환경에 등록된 MockMvc 를 DI 함
    @Autowired
    private MockMvc mvc;

    // 서버는 일관성있게 에러 리턴
    // 내가 모르는 에러가 프론트한테 날라가지 않게 , 내가 다 직접 제어
    @Test
    public void 인증() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));
        String ResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        int status = resultActions.andReturn().getResponse().getStatus();
        System.out.println("ResponseBody = " + ResponseBody);
        System.out.println("status = " + status);

        //then

        assertThat(status).isEqualTo(401);
    }


    @Test
    public void 권한() throws Exception {
        //given

        //when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello"));
        String ResponseBody = resultActions.andReturn().getResponse().getContentAsString();
        int status = resultActions.andReturn().getResponse().getStatus();
        System.out.println("ResponseBody = " + ResponseBody);
        System.out.println("status = " + status);



        //then
        assertThat(status).isEqualTo(401);
    }
}
