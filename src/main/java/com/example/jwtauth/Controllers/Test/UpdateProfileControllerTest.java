package com.example.jwtauth.Controllers.Test;

import com.example.jwtauth.Entities.User;
import com.example.jwtauth.dto.updateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UpdateProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateProfile() throws Exception {
        updateDto updateDto = new updateDto();
        updateDto.setEmail("muha@gmail.com");
        updateDto.setPhoneNumber("8-707-777-7700");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(updateDto);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OTAsInN1YiI6Im11aGFAZ21haWwuY29tIiwiaWF0IjoxNzE1NDQyOTIwLCJleHAiOjE3MTU0NDQ3MjB9.CL0Jmx3mE9ACL2v6c5ZzEcwuodQV8J6M8UWCS3Rg0lk";

        MvcResult result = mockMvc.perform(post("/api/profile/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        // Extract the updated user and new token from the response. Adjust these according to the structure of your response.
        String updatedUserEmail = JsonPath.read(response, "$.user.email");
        String updatedUserPhoneNumber = JsonPath.read(response, "$.user.phoneNumber");
        String newToken = JsonPath.read(response, "$.token");

        assertEquals("muha@gmail.com", updatedUserEmail);
        assertEquals("8-707-777-7700", updatedUserPhoneNumber);
        assertNotNull(newToken);
    }

}
