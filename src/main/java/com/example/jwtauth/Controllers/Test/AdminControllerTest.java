package com.example.jwtauth.Controllers.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAdminAttendance() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/admin/attendance"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        List<ObjectNode> attendance = new ObjectMapper().readValue(response, new TypeReference<List<ObjectNode>>(){});

        assertNotNull(attendance);
        assertFalse(attendance.isEmpty());
    }

    @Test
    public void testDownloadPermission() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/permission/download"))
                .andExpect(status().isOk())
                .andReturn();

        byte[] response = result.getResponse().getContentAsByteArray();
        assertNotNull(response);
        assertTrue(response.length > 0);
    }
}
