package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    @BeforeEach
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

    @Test
    public void TestToQuestionDTO() {
        QuestionDTO dto = mcQuestion.toDto();
        assertEquals(mcQuestion.getQuestion(), dto.getQuestion());
        assertEquals(QuestionDTO.MULTIPLECHOICE, dto.getQuestionType());
        //assertEquals(mcQuestion.getId(), dto.getID()); //TODO: uncomment after implementation
    }

    @Test
    public void TestPopulateResultDTO() {
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addAnswer(1);
        ResultDTO dto = mcQuestion.populateResultDTO();
        assertEquals(mcQuestion.getQuestion(), dto.getQuestion());
        assertEquals(QuestionDTO.MULTIPLECHOICE, dto.getQuestionType());
        assertEquals(1, dto.getChartData().size());
    }

    @Test
    public void TestGetType() {
        assertEquals("MC", mcQuestion.getType());
    }

    @Test
    public void TestOptionToDSV() {
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        mcQuestion.addChoice(CHOICETHREE);
        mcQuestion.addChoice(CHOICEFOUR);
        assertEquals(String.format("%s:%s|%s:%s|%s:%s|%s:%s",
                1,CHOICEONE,
                2,CHOICETWO,
                3,CHOICETHREE,
                4,CHOICEFOUR),
                mcQuestion.optionToDSV());
    }

    @Test
    public void TestAnswersToDSV() {
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        mcQuestion.addAnswer(1);
        mcQuestion.addAnswer(2);

        assertEquals("1*1", mcQuestion.answersToDSV());
    }

    @Test
    public void TestGetAnswerSummaryForExport() {
        List<String> summary = new ArrayList<>();
        summary.add("Answer Options,\"Response Percent\",\"Response Count\"");
        summary.add(String.format("%s,\"25.00%%\",1",CHOICEONE));
        summary.add(String.format("%s,\"50.00%%\",2",CHOICETWO));
        summary.add(String.format("%s,\"25.00%%\",1",CHOICETHREE));
        mcQuestion.addChoice(CHOICEONE);
        mcQuestion.addChoice(CHOICETWO);
        mcQuestion.addChoice(CHOICETHREE);
        mcQuestion.addAnswer(1);
        mcQuestion.addAnswer(2);
        mcQuestion.addAnswer(2);
        mcQuestion.addAnswer(3);
        assertEquals(summary, mcQuestion.getAnswerSummaryForExport());
    }
}
