package application.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestMultipleChoiceQuestion {

    private MultipleChoiceQuestion mcQuestion;
    private static final String QUESTION = "what is your favorite colour";
    private static final String CHOICEONE = "Red";
    private static final String CHOICETWO = "Blue";
    private static final String CHOICETHREE = "Green";
    private static final String CHOICEFOUR = "Yellow";
    private static final String CHOICEFIVE = "Teal";

    /**
     * Sets up initial conditions for tests
     */
    @Before
    public void setUp(){
        mcQuestion = new MultipleChoiceQuestion(QUESTION);

    }

    /**
     * Tests default constructor
     */
    @Test
    public void TestDefaultConstructor(){
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        assertNotNull(question);
    }
    @Test
    public void TestSettingChoices(){
        Collection<String> choices = new ArrayList<String>();
        choices.add("Purple");
        choices.add("Brown");
        choices.add(CHOICEFIVE);
        mcQuestion.setChoices(choices);
        assertEquals(choices,mcQuestion.getChoices());
        assertEquals(3,mcQuestion.getChoices().size());
    }

    @Test
    public void TestAddingChoices(){
        mcQuestion.addchoice(CHOICEONE);
        mcQuestion.addchoice(CHOICETWO);
        mcQuestion.addchoice(CHOICETHREE);
        mcQuestion.addchoice(CHOICEFOUR);
        assertEquals(4,mcQuestion.getChoices().size());
    }
    @Test
    public void TestAddingAnswers(){
        mcQuestion.addchoice(CHOICEONE);
        mcQuestion.addchoice(CHOICETWO);
        mcQuestion.addchoice(CHOICETHREE);
        mcQuestion.addchoice(CHOICEFOUR);

        mcQuestion.addAnswer(CHOICEONE);
        mcQuestion.addAnswer(CHOICEONE);

        mcQuestion.addAnswer(CHOICETWO);

        mcQuestion.addAnswer(CHOICETHREE);
        mcQuestion.addAnswer(CHOICETHREE);
        mcQuestion.addAnswer(CHOICETHREE);

        mcQuestion.addAnswer(CHOICEFOUR);
        mcQuestion.addAnswer(CHOICEFOUR);
        mcQuestion.addAnswer(CHOICEFOUR);
        mcQuestion.addAnswer(CHOICEFOUR);

        assertEquals( 2,mcQuestion.getAnswer(CHOICEONE));
        assertEquals( 1,mcQuestion.getAnswer(CHOICETWO));
        assertEquals( 3,mcQuestion.getAnswer(CHOICETHREE));
        assertEquals( 4,mcQuestion.getAnswer(CHOICEFOUR));

    }

}
