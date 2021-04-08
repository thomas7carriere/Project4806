package application.models.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestEditDTO {
    private static final String TEST_SURVEY_NAME = "Test Survey Name";
    private static final String TEST_QUESTION = "Test Question";
    private static final HashMap<Long, String> TEST_EDITED_QUESTIONS = new HashMap<>();
    private static final int TEST_MIN_VAL = 0;
    private static final int TEST_MAX_VAL = 10;
    private static final List<String> TEST_CHOICES = Arrays.asList("1", "2", "3");
    private static final long TEST_SURVEY_ID = 10L;
    private static final long TEST_QUESTION_ID = 4L;
    private static final long EDITED_QUESTION_ID = 4L;
    private static final String EDITED_QUESTION = "Test Edited Question";

    private static final QuestionDTO TEST_QUESTION_DTO_OPEN_ENDED =
            new QuestionDTO(QuestionDTO.OPENENDED, TEST_QUESTION, 0, 0, new ArrayList<>(), TEST_QUESTION_ID);

    private static final QuestionDTO TEST_QUESTION_DTO_MULTIPLE_CHOICE =
            new QuestionDTO(QuestionDTO.MULTIPLECHOICE, TEST_QUESTION, 0, 0, TEST_CHOICES,TEST_QUESTION_ID);

    private static final QuestionDTO TEST_QUESTION_DTO_RANGE =
            new QuestionDTO(QuestionDTO.RANGE, TEST_QUESTION, TEST_MIN_VAL, TEST_MAX_VAL, new ArrayList<>(),TEST_QUESTION_ID);

    private static final List<QuestionDTO> TEST_NEW_QUESTION_DTO_LIST =
            Arrays.asList(TEST_QUESTION_DTO_OPEN_ENDED, TEST_QUESTION_DTO_MULTIPLE_CHOICE, TEST_QUESTION_DTO_RANGE);

    private static EditDTO editDTO;

    @BeforeAll
    public static void setUp(){
        editDTO = new EditDTO();
        TEST_EDITED_QUESTIONS.put(EDITED_QUESTION_ID, EDITED_QUESTION);
    }

    @Test
    public void constructorTest(){
        editDTO = new EditDTO(TEST_SURVEY_ID, TEST_SURVEY_NAME, TEST_NEW_QUESTION_DTO_LIST, TEST_EDITED_QUESTIONS);
        assertEquals(TEST_SURVEY_NAME, editDTO.getSurveyName());
        assertEquals(10, editDTO.getSurveyID());
        assertEquals(TEST_NEW_QUESTION_DTO_LIST, editDTO.getNewQuestions());
    }

    @Test
    public void setAndGetSurveyIDTest(){
        editDTO = new EditDTO();
        editDTO.setSurveyID(TEST_SURVEY_ID);
        assertEquals(TEST_SURVEY_ID, editDTO.getSurveyID());
    }
    @Test
    public void setAndGetSurveyNameTest(){
        editDTO.setSurveyName(TEST_SURVEY_NAME);
        assertEquals(TEST_SURVEY_NAME, editDTO.getSurveyName());
    }
    @Test
    public void setAndGetEditedQsTest(){
        editDTO.setEdited(TEST_EDITED_QUESTIONS);
        assertEquals(TEST_EDITED_QUESTIONS, editDTO.getEdited());
    }
    @Test
    public void setAndGetNewQsTest(){
        editDTO.setNewQuestions(TEST_NEW_QUESTION_DTO_LIST);
        assertEquals(TEST_NEW_QUESTION_DTO_LIST, editDTO.getNewQuestions());
    }
}
