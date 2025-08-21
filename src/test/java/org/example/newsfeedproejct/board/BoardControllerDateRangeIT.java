package org.example.newsfeedproejct.board;

import jakarta.transaction.Transactional;
import org.example.newsfeedproejct.global.config.PasswordEncoder;
import org.example.newsfeedproejct.user.entity.User;
import org.example.newsfeedproejct.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BoardControllerDateRangeIT {

    @Autowired MockMvc mvc;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @BeforeEach
    void seed() {
        String rawPw = "Password11!!";
        String email = "tester@example.com";
        String encoded = passwordEncoder.encode(rawPw);
        userRepository.save(new User("tester", email, encoded));
    }
    @AfterEach
    void tearDown() { userRepository.deleteAll(); }

    @Test
    @Transactional
    void invalid_range_returns_error_with_session() throws Exception {
        // 1) 로그인해서 세션 받기
        String body = """
{"email":"tester@example.com","password":"Password11!!"}
""";
        MvcResult loginResult = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);

        // 2) 같은 세션으로 API 호출
        mvc.perform(get("/boards")
                        .session(session)
                        .param("page", "0").param("size", "10")
                        .param("startDate", "2025-08-22").param("endDate", "2025-08-21")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    void same_day_ok_with_session() throws Exception {
        // 1) 로그인해서 세션 받기
        String body = """
{"email":"tester@example.com","password":"Password11!!"}
""";
        MvcResult loginResult = mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();


        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);

        // 2) 같은 세션으로 API 호출
        mvc.perform(get("/boards")
                        .session(session)
                        .param("page", "0")
                        .param("size", "10")
                        .param("startDate", "2025-08-21")
                        .param("endDate",   "2025-08-21"))
                .andExpect(status().isOk());
    }
}
