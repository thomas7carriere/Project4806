package application.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestOpenEndedQuestion {

    private OpenEndedQuestion q;
    private static final String QUESTION = "Do you like dogs?";
    private static final String ANSWER1 = "No I HATE dogs";
    private static final String ANSWER2 = "Yes I like dogs";

    @BeforeEach
    public void setUp() {
        q = new OpenEndedQuestion(QUESTION);
    }

    @Test
    public void TestDefaultConstructor() {
        // not sure what could be tested...
        OpenEndedQuestion question = new OpenEndedQuestion();
        assertNotNull(question);
    }

    @Test
    public void TestSetThenGetAnswer() {
        List<String> answer = new ArrayList<>();
        answer.add(ANSWER1);
        answer.add(ANSWER2);
        answer.add("random answer");
        q.setAnswer(answer);

        assertEquals(answer, q.getAnswer());
        assertEquals(3, q.getAnswer().size());
    }

    @Test
    public void TestAddThenGetAnswer() {
        assertTrue(q.addAnswer(ANSWER1));
        assertTrue(q.addAnswer(ANSWER2));
        assertTrue(q.getAnswer().contains(ANSWER1));
        assertTrue(q.getAnswer().contains(ANSWER2));
        assertEquals(2, q.getAnswer().size());
    }

    @Test
    public void TestGetType() {
        assertEquals("OE", q.getType());
    }

    @Test
    public void TestOptionToDSV() {
        assertEquals("", q.optionToDSV());
    }

    @Test
    public void TestAnswersToDSV() {
        List<String> answer = new ArrayList<>();
        answer.add(ANSWER1);
        answer.add(ANSWER2);
        q.setAnswer(answer);
        assertEquals(String.join("*", answer), q.answersToDSV());
    }

    @Test
    public void TestGetAnswerSummaryForExport() {
        List<String> summary = new ArrayList<>();
        summary.add(ANSWER1);
        summary.add(ANSWER2);
        q.setAnswer(summary);
        assertEquals(summary, q.getAnswerSummaryForExport());
    }
}
