package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.SurveyDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSurveyDTO {

    private static final String TEST_SURVEY_NAME = "Test Survey Name";
    private static final String TEST_QUESTION = "Test Question";
    private static final int TEST_MIN_VAL = 0;
    private static final int TEST_MAX_VAL = 3;
    private static final List<String> TEST_CHOICES = Arrays.asList("red", "yellow", "green");
    private static final long TEST_SURVEY_ID = 1L;
    private static final long TEST_QUESTION_ID = 0L;

    private static final QuestionDTO TEST_QUESTION_DTO_OPEN_ENDED =
            new QuestionDTO(QuestionDTO.OPENENDED, TEST_QUESTION, 0, 0, new ArrayList<>(),TEST_QUESTION_ID);

    private static final QuestionDTO TEST_QUESTION_DTO_MULTIPLE_CHOICE =
            new QuestionDTO(QuestionDTO.MULTIPLECHOICE, TEST_QUESTION, 0, 0, TEST_CHOICES,TEST_QUESTION_ID);

    private static final QuestionDTO TEST_QUESTION_DTO_RANGE =
            new QuestionDTO(QuestionDTO.RANGE, TEST_QUESTION, TEST_MIN_VAL, TEST_MAX_VAL, new ArrayList<>(),TEST_QUESTION_ID);

    private static final List<QuestionDTO> TEST_QUESTION_DTO_LIST =
            Arrays.asList(TEST_QUESTION_DTO_OPEN_ENDED, TEST_QUESTION_DTO_MULTIPLE_CHOICE, TEST_QUESTION_DTO_RANGE);

    private SurveyDTO surveyDTO;

    @BeforeEach
    public void setUp() {
        surveyDTO = new SurveyDTO();
    }

    @Test
    public void constructorNotPassingSurvey() {
        //Test constructor by passing name and Question DTO
        SurveyDTO surveyDTO = new SurveyDTO(TEST_SURVEY_NAME, TEST_QUESTION_DTO_LIST);
        assertEquals(TEST_SURVEY_NAME, surveyDTO.getName());
        assertEquals(0, surveyDTO.getId());
        assertEquals(TEST_QUESTION_DTO_LIST, surveyDTO.getQuestions());

        //Test constructor by passing Question DTO
        surveyDTO = new SurveyDTO(TEST_QUESTION_DTO_LIST);
        assertEquals("", surveyDTO.getName());
        assertEquals(0, surveyDTO.getId());
        assertEquals(TEST_QUESTION_DTO_LIST, surveyDTO.getQuestions());
    }

    @Test
    public void constructorPassingInSurvey() {
        OpenEndedQuestion openEndedQuestion = new OpenEndedQuestion(TEST_QUESTION);
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(TEST_QUESTION, TEST_CHOICES);
        RangeQuestion rangeQuestion = new RangeQuestion(TEST_QUESTION, TEST_MIN_VAL, TEST_MAX_VAL);
        openEndedQuestion.setId(0L);
        multipleChoiceQuestion.setId(0L);
        rangeQuestion.setId(0L);

        List<Question> listOfQuestions = new ArrayList<>();
        listOfQuestions.add(openEndedQuestion);
        listOfQuestions.add(multipleChoiceQuestion);
        listOfQuestions.add(rangeQuestion);

        //Test constructor by passing Survey
        surveyDTO = new SurveyDTO(new Survey(TEST_SURVEY_NAME, listOfQuestions));
        assertEquals(TEST_SURVEY_NAME, surveyDTO.getName());
        assertEquals(0, surveyDTO.getId());
        assertEquals(TEST_QUESTION_DTO_LIST, surveyDTO.getQuestions());
    }

    @Test
    public void setAndGetName() {
        surveyDTO.setName(TEST_SURVEY_NAME);
        assertEquals(TEST_SURVEY_NAME, surveyDTO.getName());
    }

    @Test
    public void setAndGetQuestions() {
        surveyDTO.setQuestions(TEST_QUESTION_DTO_LIST);
        assertEquals(TEST_QUESTION_DTO_LIST, surveyDTO.getQuestions());
    }

    @Test
    public void setAndGetId() {
        surveyDTO.setId(TEST_SURVEY_ID);
        assertEquals(TEST_SURVEY_ID, surveyDTO.getId());
    }
}
