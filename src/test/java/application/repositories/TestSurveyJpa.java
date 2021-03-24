package application.repositories;

import application.models.MultipleChoiceQuestion;
import application.models.Question;
import application.models.Survey;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@DataJpaTest
public class TestSurveyJpa {
    @Resource
    private SurveyRepository sRepo;

    private MultipleChoiceQuestion question;
    private Survey survey;
    private List<Question> questions;

    @Before
    public void setUp(){
        questions = new ArrayList<>();
        survey = new Survey("Jpa Test",questions);
        question = new MultipleChoiceQuestion();
    }

    /**
     * Testing if the survey has been saved in the repository
     */
    @Test
    public void TestSavingSurvey(){
        survey.addQuestion(question);
        sRepo.save(survey);
        Survey surveyFromRepo = sRepo.findById(survey.getId());
        assertEquals(surveyFromRepo, survey);
        assertEquals(survey.getQuestions(), surveyFromRepo.getQuestions());
    }

    @Test
    public void TestDeletingSurvey(){
        //Save Survey
        survey.addQuestion(question);
        sRepo.save(survey);
        Survey surveyFromRepo = sRepo.findById(survey.getId());
        assertEquals(surveyFromRepo, survey);
        assertEquals(survey.getQuestions(), surveyFromRepo.getQuestions());

        //Delete Survey
        sRepo.deleteById(survey.getId());
        surveyFromRepo = sRepo.findById(survey.getId());
        assertNull(surveyFromRepo);
    }
}
