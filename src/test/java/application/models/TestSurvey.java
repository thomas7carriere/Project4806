package application.models;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestSurvey {
    private Survey survey;
    private List<Question> questions = new ArrayList<>();
    private MultipleChoiceQuestion multi;
    private RangeQuestion range;
    private OpenEndedQuestion open;


    @Before
    public void setUp(){
        multi = new MultipleChoiceQuestion();
        range = new RangeQuestion();
        open = new OpenEndedQuestion();
        survey = new Survey();
    }

    @Test
    public void setAndGetName() {
        survey.setName("Test Survey");
        assertEquals(survey.getName(), "Test Survey");
    }

    @Test
    public void setAndGetId() {
        survey.setId(10383);
        assertEquals(survey.getId(), 10383);
    }

    @Test
    public void addAndRemoveQuestion() {
        survey.addQuestion(multi);
        survey.addQuestion(range);
        survey.addQuestion(open);
        assertTrue(survey.removeQuestion(multi));
        assertTrue(survey.removeQuestion(range));
        assertTrue(survey.removeQuestion(open));
    }

    @Test
    public void getAndSetQuestions() {
        questions.add(multi);
        questions.add(range);
        questions.add(open);
        survey.setQuestions(questions);
        assertEquals(questions,survey.getQuestions());
    }
}