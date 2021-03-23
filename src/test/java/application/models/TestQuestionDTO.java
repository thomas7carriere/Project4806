package application.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestQuestionDTO {

    private QuestionDTO questionDTO;

    private static final String TEST_QUESTION = "Test Question";
    private static final int TEST_MIN_VAL = 0;
    private static final int TEST_MAX_VAL = 3;
    private static final List<String> TEST_CHOICES = Arrays.asList("red", "yellow", "green");
    private static final long TEST_QUESTION_ID = 5L;

    @Before
    public void setUp() {
        questionDTO = new QuestionDTO();
    }

    @Test
    public void constructorTest() {
        questionDTO = new QuestionDTO(QuestionDTO.OPENENDED,
                TEST_QUESTION, TEST_MIN_VAL, TEST_MAX_VAL, TEST_CHOICES,TEST_QUESTION_ID);

        assertEquals(QuestionDTO.OPENENDED, questionDTO.getQuestionType());
        assertEquals(TEST_QUESTION, questionDTO.getQuestion());
        assertEquals(TEST_MIN_VAL, questionDTO.getMin());
        assertEquals(TEST_MAX_VAL, questionDTO.getMax());
        assertEquals(TEST_CHOICES, questionDTO.getChoices());
        assertEquals(TEST_QUESTION_ID, (long) questionDTO.getID());
    }

    @Test
    public void setAndGetQuestionType() {
        questionDTO.setQuestionType(QuestionDTO.OPENENDED);
        assertEquals(QuestionDTO.OPENENDED, questionDTO.getQuestionType());

        questionDTO.setQuestionType(QuestionDTO.RANGE);
        assertEquals(QuestionDTO.RANGE, questionDTO.getQuestionType());

        questionDTO.setQuestionType(QuestionDTO.MULTIPLECHOICE);
        assertEquals(QuestionDTO.MULTIPLECHOICE, questionDTO.getQuestionType());
    }

    @Test
    public void setAndGetQuestion() {
        questionDTO.setQuestion(TEST_QUESTION);
        assertEquals(TEST_QUESTION, questionDTO.getQuestion());
    }

    @Test
    public void setAndGetMin() {
        questionDTO.setMin(TEST_MIN_VAL);
        assertEquals(TEST_MIN_VAL, questionDTO.getMin());
    }

    @Test
    public void setAndGetMax() {
        questionDTO.setMax(TEST_MAX_VAL);
        assertEquals(TEST_MAX_VAL, questionDTO.getMax());
    }

    @Test
    public void setAndGetChoices() {
        questionDTO.setChoices(TEST_CHOICES);
        assertEquals(TEST_CHOICES, questionDTO.getChoices());
    }
    @Test
    public void setAndGetID() {
        questionDTO.setID(TEST_QUESTION_ID);
        assertEquals(TEST_QUESTION_ID, (long) questionDTO.getID());
    }

    @Test
    public void testToString() {
        questionDTO = new QuestionDTO(QuestionDTO.OPENENDED,
                TEST_QUESTION, TEST_MIN_VAL, TEST_MAX_VAL, TEST_CHOICES,TEST_QUESTION_ID);

        String expectedText = "QuestionType: "
                + QuestionDTO.OPENENDED + " Question: " + TEST_QUESTION;
        assertEquals(expectedText, questionDTO.toString());

        questionDTO.setQuestionType(QuestionDTO.MULTIPLECHOICE);
        expectedText = "QuestionType: "
                + QuestionDTO.MULTIPLECHOICE + " Question: " + TEST_QUESTION
                + " Choice: " + TEST_CHOICES.get(0) + " Choice: " +
                TEST_CHOICES.get(1) + " Choice: " + TEST_CHOICES.get(2);
        assertEquals(expectedText, questionDTO.toString());

        questionDTO.setQuestionType(QuestionDTO.RANGE);
        expectedText = "QuestionType: "
                + QuestionDTO.RANGE + " Question: " + TEST_QUESTION
                + " Min: " + TEST_MIN_VAL + " Max: " + TEST_MAX_VAL;
        assertEquals(expectedText, questionDTO.toString());
    }
}
