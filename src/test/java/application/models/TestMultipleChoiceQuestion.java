package application.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestMultipleChoiceQuestion {

    private MultipleChoiceQuestion mcQuestion;
    private static final String QUESTION = "What is your favorite colour?";
    private static final String CHOICEONE = "Red";
    private static final String CHOICETWO = "Blue";
    private static final String CHOICETHREE = "Green";
    private static final String CHOICEFOUR = "Yellow";
    private static final String CHOICEFIVE = "Teal";
    private static final Collection<String> choices = new ArrayList<String>();

    /**
     * Sets up initial conditions for tests
     */
    @Before
    public void setUp(){
        choices.clear();
        mcQuestion = new MultipleChoiceQuestion(QUESTION,choices);
    }

    /**
     * Tests default constructor
     */
    @Test
    public void TestDefaultConstructor(){
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        assertNotNull(question);
    }

    /**
     * Testing the setter to set the collection of choices
     */
    @Test
    public void TestSettingChoices(){
        choices.add("Purple");
        choices.add("Brown");
        choices.add(CHOICEFIVE);
        mcQuestion.setChoices(choices);
        assertEquals(choices,mcQuestion.getChoices());
        //tests that there are three choices available
        assertEquals(3,mcQuestion.getChoices().size());
    }

    /**
     * Testing adding choices individually
     */
    @Test
    public void TestAddingChoices(){
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        mcQuestion.addChoice(CHOICETHREE);
        mcQuestion.addChoice(CHOICEFOUR);
        //tests that there are four choices available
        assertEquals(4,mcQuestion.getChoices().size());
    }

    /**
     * Tests getting the string representation of a question ID
     */
    @Test
    public void TestGettingStringFromIDs(){
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        Map<Integer, String> questionIDs = mcQuestion.getChoicesID();
        assertEquals(CHOICEONE,questionIDs.get(1));
        assertEquals(CHOICETWO,questionIDs.get(2));
    }

    /**
     * Testing adding answers to the multiple choice question
     */
    @Test
    public void TestAddingAnswers(){
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        mcQuestion.addChoice(CHOICETHREE);
        mcQuestion.addChoice(CHOICEFOUR);

        /**
         *  Adding answers by their corresponding ID of each choice
         */
        mcQuestion.addAnswer(1);
        mcQuestion.addAnswer(1);

        mcQuestion.addAnswer(2);

        mcQuestion.addAnswer(3);
        mcQuestion.addAnswer(3);
        mcQuestion.addAnswer(3);

        mcQuestion.addAnswer(4);
        mcQuestion.addAnswer(4);
        mcQuestion.addAnswer(4);
        mcQuestion.addAnswer(4);
        //tests the expected results from each choice answered
        assertEquals( 2,mcQuestion.getAnswer(1));
        assertEquals( 1,mcQuestion.getAnswer(2));
        assertEquals( 3,mcQuestion.getAnswer(3));
        assertEquals( 4,mcQuestion.getAnswer(4));

    }

}
