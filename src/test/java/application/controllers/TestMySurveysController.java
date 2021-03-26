package application.controllers;

import application.models.*;
import application.repositories.SurveyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestMySurveysController {
    @Autowired
    private MockMvc mockMvc;
    private OpenEndedQuestion q1;
    private QuestionDTO q2;
    private EditDTO editDTO;
    private Collection<QuestionDTO> newQuestions;
    private HashMap<Long, String> editedQuestions;

    @InjectMocks
    private SurveyController mySurveysController;

    @Before
    public void setUp() {
        q1= new OpenEndedQuestion();
        q1.setQuestion("Test Multi");
        q1.setId(6L);
        q2 = new QuestionDTO();
        q2.setQuestionType(QuestionDTO.OPENENDED);
        q2.setQuestion("OpenEnded Question?");
        q2.setID(1L);

        newQuestions = new ArrayList<>();
        newQuestions.add(q2);

        String surveyName = "Test Survey";
        Long surveyId = 10L;
        editedQuestions = new HashMap<>();
        editedQuestions.put(q1.getId(), "edit1");
        editDTO = new EditDTO(surveyId, newQuestions, editedQuestions, surveyName);
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

    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(3)
    public void getResult() throws Exception {
        // should be 404 for non-existing survey
        mockMvc.perform(get("/mysurveys/results/9999")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        // should return the view
        mockMvc.perform(get("/mysurveys/results/1")).andExpect(status().isOk())
                .andExpect(view().name("viewResult"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("https://www.gstatic.com/charts/loader.js")));
        // view specific script should contain calls to Google Charts API
        mockMvc.perform(get("/scripts/viewResult.js")).andExpect(status().isOk())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString("var dataTable = new google.visualization.DataTable();")));
    }

    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(4)
    public void closeSurvey() throws Exception {
        // 404 first due to survey not exist
        mockMvc.perform(get("/mysurveys/close/9999")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        mockMvc.perform(get("/mysurveys/close/1")).andExpect(status().isOk())
                .andExpect(view().name("mySurveys"));
    }
    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(5)
    public void editSurvey() throws Exception {
        mockMvc.perform(get("/mysurveys/edit/4050")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        mockMvc.perform(get("/mysurveys/edit/1")).andExpect(status().isOk()).andExpect(view().name("editSurvey"));
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(patch("/mysurveys/edit").content(objectMapper.writeValueAsString(editDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict()).andReturn();
    }

}
