package com.example.jwtauth.Controllers.Test;

import com.example.jwtauth.dto.GetClass;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAttendance() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OTAsInN1YiI6Im11aGFAZ21haWwuY29tIiwiaWF0IjoxNzE1NDQzMTA2LCJleHAiOjE3MTU0NDQ5MDZ9.tH_eXRwHUFNAZj8rXaxNkp5qnKRFR05aQW0KuTcUI1I"; // replace with your actual token

        MvcResult result = mockMvc.perform(get("/api/attendance")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ObjectNode attendance = new ObjectMapper().readValue(response, ObjectNode.class);

        assertNotNull(attendance);
    }

    @Test
    public void testGetClassAttendance() throws Exception {
        GetClass getClass = new GetClass();
        getClass.setClassId("H02");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(getClass);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6OTAsInN1YiI6Im11aGFAZ21haWwuY29tIiwiaWF0IjoxNzE1NDQzMTA2LCJleHAiOjE3MTU0NDQ5MDZ9.tH_eXRwHUFNAZj8rXaxNkp5qnKRFR05aQW0KuTcUI1I";

        MvcResult result = mockMvc.perform(post("/api/attendance/class")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<ObjectNode> classAttendance = objectMapper.readValue(response, new TypeReference<List<ObjectNode>>(){});

        assertNotNull(classAttendance);
        assertFalse(classAttendance.isEmpty());
    }
}