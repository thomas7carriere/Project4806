package application.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.Assert.*;

public class TestTextQuestion {

    private TextQuestion q;
    private static final String QUESTION = "Do you like dogs?";
    private static final String ANSWER1 = "No I HATE dogs";
    private static final String ANSWER2 = "Yes I like dogs";

    @Before
    public void setUp() {
        q = new TextQuestion(QUESTION);
    }

    @Test
    public void TestDefaultConstructor() {
        // not sure what could be tested...
        TextQuestion question = new TextQuestion();
        assertNotNull(question);
    }

    @Test
    public void TestSetThenGetAnswer() {
        Collection<String> answer = new HashSet<>();
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

}
