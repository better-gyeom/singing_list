package com.singinglist.api.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class) //web에 집중할 수 있는 어노테이션
public class HelloControllerTest {

    @Autowired //스프링이 관리하는 빈(Bean) 주입받음
    private MockMvc mvc; //웹api를 테스트할 때 사용, 스프링 MVC테스트의 시작점

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) //MockMvc를 통해 /hello 주소로 get 요청
                .andExpect(status().isOk()) //[mvc.perform의 결과 검증] status 검증, 즉 ok (200인지 아닌지)검증
                .andExpect(content().string(hello)); //응답 본문의 내용 검증, 즉 리턴하는게 hello가 맞는지
    }
    // File - project structure 에서 버전이 맞아야 한다.

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name) //api test할 때 사용될 요청 파라미터 설정, 단 String값만 허용
                        .param("amount", String.valueOf(amount)) //그래서 int형을 String으로 바꿔준 것
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name))) //JSON 응답값을 필드별로 검증할 수 있는 메서드
                .andExpect(jsonPath("$.amount", is(amount))); //$를 기준으로 필드명 명시
    }
}
