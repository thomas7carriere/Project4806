package application.repositories;

import application.models.Question;
import application.models.OpenEndedQuestion;
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
public class TestOpenEndedQuestionJpa {

    @Resource
    private QuestionRepository qRepo;

    @Test
    public void TestTextQuestionJPA() {
        OpenEndedQuestion q1 = new OpenEndedQuestion("meow");
        qRepo.save(q1);
        Optional<Question> q2 = qRepo.findById(q1.getId());
        assertTrue(q2.isPresent());
        assertEquals(q2.get(), q1);
    }
}
