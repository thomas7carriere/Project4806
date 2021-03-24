package application.repositories;

import application.models.MultipleChoiceQuestion;
import application.models.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@DataJpaTest
public class TestMultipleChoiceQuestionJpa {

    @Resource
    private QuestionRepository questionrepo;

    private MultipleChoiceQuestion question;
    private static final String CHOICEONE = "One";
    private static final String CHOICETWO = "Two";
    private static final String CHOICETHREE = "More than Two";
    private static final Collection<String> choices = new ArrayList<String>();

    @BeforeEach
    public void setUp(){
        choices.add(CHOICEONE);
        choices.add(CHOICETWO);
        choices.add(CHOICETHREE);
        question = new MultipleChoiceQuestion("How many dogs do you have?", choices);
    }

    /**
     * Testing that the question has been saved in the repository
     */
    @Test
    public void TestSavingMCQuestion() {
        questionrepo.save(question);
        Optional<Question> questionFromRepo = questionrepo.findById(question.getId());
        assertTrue(questionFromRepo.isPresent());
        assertEquals(questionFromRepo.get(), question);
    }

    /**
     * Testing that the saved question from the repository has the correct answers saved
     */
    @Test
    public void TestSavingMCQuestionWithAnswers() {
        question.addAnswer(1);
        question.addAnswer(1);

        question.addAnswer(2);

        question.addAnswer(3);
        question.addAnswer(3);
        question.addAnswer(3);

        questionrepo.save(question);
        Optional<Question> questionFromRepo = questionrepo.findById(question.getId());
        MultipleChoiceQuestion questionFromRepoMC = (MultipleChoiceQuestion) questionFromRepo.get();
        //Tests the expected number of times each choice was chosen
        assertEquals( 2,questionFromRepoMC.getAnswer(1));
        assertEquals( 1,questionFromRepoMC.getAnswer(2));
        assertEquals( 3,questionFromRepoMC.getAnswer(3));

    }
}
