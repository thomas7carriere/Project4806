package application.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnswerDTO {
    
    private static final long TEST_SURVEY_ID = 5L;
    private static final HashMap<Long,String> TEST_ANSWERS = new HashMap<Long, String>();
    private AnswerDTO answerDTO;
    private static final long QUESTION_ID_1 = 1L;
    private static final long QUESTION_ID_2 = 2L;
    private static final long QUESTION_ID_3 = 3L;
    private static final String QUESTION_Answer_1 = "Dog";
    private static final String QUESTION_Answer_2 = "1";
    private static final String QUESTION_Answer_3 = "53";


    @BeforeEach
    public void setup(){
        TEST_ANSWERS.put(QUESTION_ID_1,QUESTION_Answer_1);
        TEST_ANSWERS.put(QUESTION_ID_2,QUESTION_Answer_2);
        TEST_ANSWERS.put(QUESTION_ID_3,QUESTION_Answer_3);
        answerDTO = new AnswerDTO();
    }
    @Test
    public void constructorTest(){
        answerDTO = new AnswerDTO(TEST_SURVEY_ID,TEST_ANSWERS);
        assertEquals(TEST_SURVEY_ID,answerDTO.getSurveyID());
        assertEquals(TEST_ANSWERS,answerDTO.getAnswers());
    }

    @Test
    public void setAndGetSurveyIDTest(){
        answerDTO.setSurveyID(TEST_SURVEY_ID);
        assertEquals(TEST_SURVEY_ID,answerDTO.getSurveyID());
    }
    @Test
    public void setAndGetAnswersTest(){
        answerDTO.setAnswers(TEST_ANSWERS);
        assertEquals(TEST_ANSWERS,answerDTO.getAnswers());
    }

}
