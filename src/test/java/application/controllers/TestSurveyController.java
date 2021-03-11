package application.controllers;

import application.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestSurveyController
{
    @Autowired
    private MockMvc mockMvc;
    private QuestionDTO q1, q2, q3;
    private SurveyDTO surveyDTO;
    private Survey survey;

    @InjectMocks
    private SurveyController surveyController;

    @Before
    public void setUp()
    {
        QuestionDTO q1 = new QuestionDTO();q1.setQuestionType(QuestionDTO.OPENENDED);q1.setQuestion("OpenEnded Question?");
        QuestionDTO q2 = new QuestionDTO();q2.setQuestionType(QuestionDTO.RANGE);q2.setQuestion("Range Question?");q2.setMin(1);q2.setMax(5);
        QuestionDTO q3 = new QuestionDTO();q3.setQuestionType(QuestionDTO.MULTIPLECHOICE);q3.setQuestion("MC Question?");Collection<String> choices = new ArrayList<>();choices.add("Choice1");choices.add("Choice2");choices.add("Choice3");q3.setChoices(choices);
        Collection<QuestionDTO> questions = new ArrayList<>();questions.add(q1);questions.add(q2);questions.add(q3);
        surveyDTO = new SurveyDTO("Survey Name", questions);
        survey = dtoToSurvey(surveyDTO);
    }

    @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(surveyController).isNotNull();
    }

    @Test
    @Order(2)
    public void getCreateSurvey() throws Exception {
        mockMvc.perform(get("/survey/create")).andExpect(status().isOk())
                .andExpect(view().name("createSurvey"));
    }

    //Posts a SurveyDTO in the body of the request. Assert that the returned JSON (Survey Object) is correct
    @Test
    @Order(3)
    public void postCreateSurvey() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(post("/survey/create").content(objectMapper.writeValueAsString(surveyDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(survey));
    }

    @Test
    @Order(4)
    public void getViewSurvey() throws Exception{
        mockMvc.perform(get("/survey/view/{id}", 1L)).andExpect(status().isOk())
                .andExpect(view().name("viewSurvey"))
                .andExpect(model().attribute("surveyDto", hasProperty("name", is("Survey Name"))))
                .andExpect(model().attribute("surveyDto", hasProperty("questions", hasSize(survey.getQuestions().size()))));
    }

    @Test
    @Order(5)
    public void getViewSurveyList() throws Exception{
        mockMvc.perform(get("/survey/view")).andExpect(status().isOk())
                .andExpect(view().name("viewSurveyList"))
                .andExpect(model().attribute("surveyDtoList", hasSize(1)));
    }

    @Test
    @Order(6)
    public void createSurveyWithoutContent() throws Exception {
        mockMvc.perform(post("/survey/create")).andExpect(status().isBadRequest());
    }

    //Should probably make this a service somewhere, it's used in the Survey Controller as well
    public Survey dtoToSurvey(SurveyDTO surveyDTO){
        Collection<QuestionDTO> questions = surveyDTO.getQuestions();
        Survey survey = new Survey();
        survey.setName(surveyDTO.getName());

        for(QuestionDTO question : questions){
            switch(question.getQuestionType()){
                case QuestionDTO.OPENENDED:
                    survey.addQuestion(new OpenEndedQuestion(question.getQuestion()));
                    break;
                case QuestionDTO.RANGE:
                    survey.addQuestion(new RangeQuestion(question.getQuestion(), question.getMin(), question.getMax()));
                    break;
                case QuestionDTO.MULTIPLECHOICE:
                    survey.addQuestion(new MultipleChoiceQuestion(question.getQuestion(), question.getChoices()));
                    break;
            }
        }
        return survey;
    }
}