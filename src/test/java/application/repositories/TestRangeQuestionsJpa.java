package application.repositories;

import application.models.Question;
import application.models.RangeQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@DataJpaTest
public class TestRangeQuestionsJpa {

    @Resource
    private QuestionRepository qRepo;

    private RangeQuestion q1;
    private RangeQuestion q2;


    @BeforeEach
    public void setUp(){
        q1 = new RangeQuestion("How old are you?", 0, 117);
        q2 = new RangeQuestion();
    }

    /**
     * Testing that the question has been saved in the repository
     */
    @Test
    public void TestSavingRangeQuestion(){
        qRepo.save(q1);
        qRepo.save(q2);
        Optional<Question> q1FromRepo = qRepo.findById(q1.getId());
        Optional<Question> q2FromRepo = qRepo.findById(q2.getId());
        assertTrue(q1FromRepo.isPresent());
        assertTrue(q2FromRepo.isPresent());
        assertEquals(q1FromRepo.get(), q1);
        assertEquals(q2FromRepo.get(), q2);
        assertEquals(q1FromRepo.get().getQuestion(), q1.getQuestion());
    }
}