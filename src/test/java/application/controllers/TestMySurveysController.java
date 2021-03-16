package application.controllers;

import application.models.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestMySurveysController {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SurveyController mySurveysController;

    @Before
    public void setUp() {
    }

    @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(mySurveysController).isNotNull();
    }

    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(2)
    public void getMySurveys() throws Exception {
        mockMvc.perform(get("/mysurveys")).andExpect(status().isOk())
                .andExpect(view().name("mySurveys"));
    }
}
