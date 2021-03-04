package application.repositories;

import application.models.MultipleChoiceQuestion;
import application.models.Question;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@DataJpaTest
public class TestMultipleChoiceQuestionJpa {

    @Resource
    private QuestionRepository questionrepo;

    private MultipleChoiceQuestion question;
    private static final String CHOICEONE = "One";
    private static final String CHOICETWO = "Two";
    private static final String CHOICETHREE = "More than Two";

    @Before
    public void setUp(){
        question = new MultipleChoiceQuestion("How many dogs do you have");
        question.addchoice(CHOICEONE);
        question.addchoice(CHOICETWO);
        question.addchoice(CHOICETHREE);
    }

    @Test
    public void TestSavingMCQuestion() {
        questionrepo.save(question);
        Optional<Question> questionFromRepo = questionrepo.findById(question.getId());
        assertTrue(questionFromRepo.isPresent());
        assertEquals(questionFromRepo.get(), question);
    }
    @Test
    public void TestSavingMCQuestionWithAnswers() {
        question.addAnswer(CHOICEONE);
        question.addAnswer(CHOICEONE);

        question.addAnswer(CHOICETWO);

        question.addAnswer(CHOICETHREE);
        question.addAnswer(CHOICETHREE);
        question.addAnswer(CHOICETHREE);

        questionrepo.save(question);
        Optional<Question> questionFromRepo = questionrepo.findById(question.getId());
        MultipleChoiceQuestion questionFromRepoMC = (MultipleChoiceQuestion) questionFromRepo.get();
        assertEquals( 2,questionFromRepoMC.getAnswer(CHOICEONE));
        assertEquals( 1,questionFromRepoMC.getAnswer(CHOICETWO));
        assertEquals( 3,questionFromRepoMC.getAnswer(CHOICETHREE));

    }
}
