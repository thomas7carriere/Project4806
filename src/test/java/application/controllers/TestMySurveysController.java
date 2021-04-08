package application.controllers;

import application.models.dto.EditDTO;
import application.models.dto.QuestionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestMySurveysController {
    private static Long surveyId = 10L;

    @Autowired
    private MockMvc mockMvc;
    private QuestionDTO q1;
    private QuestionDTO q2;
    private EditDTO editDTO;
    private HashMap<Long, String> editedQuestions;
    private Collection<QuestionDTO> newQuestions;
    private String editedName;


    @InjectMocks
    private SurveyController mySurveysController;

    @BeforeEach
    public void setUp() {
        newQuestions = new ArrayList<>();
        q1 = new QuestionDTO();
        q2 = new QuestionDTO();
        q1.setQuestionType(QuestionDTO.OPENENDED);
        q2.setQuestionType(QuestionDTO.OPENENDED);
        q1.setQuestion("Test Q1");
        q2.setQuestion("Test Q2");
        q1.setID(1L);
        q2.setID(2L);
        newQuestions.add(q1);
        newQuestions.add(q2);
        editedName = "Edited Survey Name";
        editedQuestions = new HashMap<>();
        editDTO = new EditDTO();
        editDTO.setEdited(editedQuestions);
        editDTO.setNewQuestions(newQuestions);
        editDTO.setSurveyName(editedName);
        editDTO.setSurveyID(1);
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
    public void editSurvey() throws Exception {
        mockMvc.perform(get("/mysurveys/edit/4050")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/mysurveys/edit/1")).andExpect(status().isOk()).andExpect(view().name("editSurvey"));
        mockMvc.perform(patch("/mysurveys/edit").content(objectMapper.writeValueAsString(editDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(5)
    public void closeSurvey() throws Exception {
        // 404 first due to survey not exist
        mockMvc.perform(get("/mysurveys/close/9999")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        mockMvc.perform(get("/mysurveys/close/1")).andExpect(status().isOk())
                .andExpect(view().name("mySurveys"));
    }

    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(6)
    public void exportSurvey() throws Exception {
        // 404 first due to survey not exist
        mockMvc.perform(get("/mysurveys/export/9999.csv")).andExpect(status().isNotFound());
        mockMvc.perform(get("/cheat")).andExpect(status().isOk());
        mockMvc.perform(get("/mysurveys/export/1.csv")).andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }
}
