package application.controllers;

import application.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TestSurveyController
{
    @Autowired
    private MockMvc mockMvc;
    private QuestionDTO q1, q2, q3;
    private SurveyDTO surveyDTO;
    private Survey survey;
    private AnswerDTO answerDTO;

    @InjectMocks
    private SurveyController surveyController;
    private Collection<QuestionDTO> questions;

    @BeforeEach
    public void setUp()
    {
        QuestionDTO q1 = new QuestionDTO();q1.setQuestionType(QuestionDTO.OPENENDED);q1.setQuestion("OpenEnded Question?");q1.setID(1L);
        QuestionDTO q2 = new QuestionDTO();q2.setQuestionType(QuestionDTO.RANGE);q2.setQuestion("Range Question?");q2.setMin(1);q2.setMax(5);q2.setID(2L);
        QuestionDTO q3 = new QuestionDTO();q3.setQuestionType(QuestionDTO.MULTIPLECHOICE);q3.setQuestion("MC Question?");Collection<String> choices = new ArrayList<>();choices.add("Choice1");choices.add("Choice2");choices.add("Choice3");q3.setChoices(choices);q3.setID(3L);
        questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        surveyDTO = new SurveyDTO("Survey Name", questions);
        survey = dtoToSurvey(surveyDTO);survey.setId(1L);

        HashMap<Long,String> questionAnswers= new HashMap<>();
        questionAnswers.put(q1.getID(),"OpenEnded Answer");
        questionAnswers.put(q2.getID(),"4");
        questionAnswers.put(q3.getID(),"1");
        answerDTO = new AnswerDTO(survey.getId(),questionAnswers);
    }

    @Test
    @Order(1)
    public void contextLoads() throws Exception {
        assertThat(surveyController).isNotNull();
    }

    @WithMockUser(username="admin",roles={"SURVEYOR","USER"})
    @Test
    @Order(2)
    public void getCreateSurvey() throws Exception {
        mockMvc.perform(get("/survey/create")).andExpect(status().isOk())
                .andExpect(view().name("createSurvey"));
    }

    //Posts a SurveyDTO in the body of the request. Assert that the returned JSON (Survey Object) is correct
    @WithMockUser(username="admin",roles={"SURVEYOR","USER"})
    @Test
    @Order(3)
    public void postCreateSurvey() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = mockMvc.perform(post("/survey/create").content(objectMapper.writeValueAsString(surveyDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(survey));
    }

    @WithMockUser(username="admin",roles={"SURVEYOR","USER"})
    @Test
    @Order(4)
    public void getViewSurvey() throws Exception{
        mockMvc.perform(get("/survey/view/{id}", 1L)).andExpect(status().isOk())
                .andExpect(view().name("viewSurvey"))
                .andExpect(model().attribute("surveyDto", hasProperty("name", is("Survey Name"))))
                .andExpect(model().attribute("surveyDto", hasProperty("questions", hasSize(survey.getQuestions().size()))));
    }

    @WithMockUser(username="admin",roles={"SURVEYOR","USER"})
    @Test
    @Order(5)
    public void getViewSurveyList() throws Exception{
        mockMvc.perform(get("/survey/view")).andExpect(status().isOk())
                .andExpect(view().name("viewSurveyList"))
                .andExpect(model().attribute("surveyDtoList", hasSize(1)));
    }

    @WithMockUser(username="admin",roles={"SURVEYOR","USER"})
    @Test
    @Order(6)
    public void createSurveyWithoutContent() throws Exception {
        mockMvc.perform(post("/survey/create")).andExpect(status().isBadRequest());
    }


    @WithMockUser(username="user",roles={"USER"})
    @Test
    @Order(6)
    public void createSurveyWithoutAuthorization() throws Exception {
        mockMvc.perform(get("/survey/create")).andExpect(status().isForbidden());
    }

    @WithMockUser(username="admin",roles={"SURVEYOR"})
    @Test
    public void deleteSurveyWithAuthorization() throws Exception {
        //Add Survey
        mockMvc.perform(post("/survey/create")
                .content(new ObjectMapper().writeValueAsString(new SurveyDTO("Survey Name 2", questions)))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();


        //Need to suppress unchecked warning due to type erasure
        @SuppressWarnings("unchecked")
        ArrayList<SurveyDTO> surveyDtoList =
                (ArrayList<SurveyDTO>) Objects.requireNonNull(mockMvc.perform(get("/survey/view"))
                        .andReturn().getModelAndView()).getModel().get("surveyDtoList");

        //Delete Survey (Should be 204 HttpStatus.NO_CONTENT)
        mockMvc.perform(delete("/survey/delete/{id}", surveyDtoList.size())).andExpect(status().isNoContent());

        //Delete Survey Again (Should be 404 HttpStatus.NOT_FOUND)
        mockMvc.perform(delete("/survey/delete/{id}", surveyDtoList.size())).andExpect(status().isNotFound());
    }

    @WithMockUser(username="user")
    @Test
    public void deleteSurveyWithoutAuthorization() throws Exception {
        mockMvc.perform(delete("/survey/delete/{id}", 1L)).andExpect(status().isForbidden());
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

    //Tests answerSurvey Controller with a json representation of a AnswerDTO object
    @WithMockUser(username = "admin", roles = {"SURVEYOR", "USER"})
    @Test
    @Order(7)
    public void answerSurvey() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/survey/answer").content(objectMapper.writeValueAsString(answerDTO)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}