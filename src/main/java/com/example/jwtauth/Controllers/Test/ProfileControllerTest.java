package com.example.jwtauth.Controllers.Test;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.Services.InfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InfoService infoService;

    @Test
    public void testShowProfile() throws Exception {
        User user = new User();
        user.setFullName("Abigale Kiebes");
        user.setEmail("muha@gmail.com");
        user.setUserRole("student");
        user.setId(90L);
        user.setPassword("$2a$10$6LnrPlm/jAfIe6QK9zuoMugAoEsSW/7dku/6VNOS5nfmGst5jKxAW");
        user.setPhoneNumber("8-707-777-7070");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OTAsInN1YiI6Im11aGFAZ21haWwuY29tIiwiaWF0IjoxNzE1NDQzMDQ4LCJleHAiOjE3MTU0NDQ4NDh9.7hOfjW-tIyAZJiEEDyzUomHRAm19ScEBZdFarSNhgAY";

        mockMvc.perform(get("/api/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token)) // add this line
                .andExpect(status().isOk());
    }
}
