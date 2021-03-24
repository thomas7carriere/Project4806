package application.repositories;

import application.models.MultipleChoiceQuestion;
import application.models.Question;
import application.models.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
@DataJpaTest
public class TestSurveyJpa {
    @Resource
    private SurveyRepository sRepo;

    private MultipleChoiceQuestion question;
    private Survey survey;
    private List<Question> questions;
    private static final String SURVEY_NAME = "Jpa Test";

    @BeforeEach
    public void setUp(){
        questions = new ArrayList<>();
        survey = new Survey(SURVEY_NAME,questions);
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

    /**
     * Testing deleting survey
     */
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

    @Test
    public void TestSearchingSurveyByName(){
        //Save Survey
        survey.addQuestion(question);
        sRepo.save(survey);
        Survey surveyFromRepo = sRepo.findById(survey.getId());
        assertEquals(surveyFromRepo, survey);
        assertEquals(survey.getQuestions(), surveyFromRepo.getQuestions());

        //Search for survey
        Iterable<Survey> surveys = sRepo.findByNameContaining(SURVEY_NAME);
        surveys.forEach(e-> assertEquals(e.getName(),SURVEY_NAME));
    }
}
