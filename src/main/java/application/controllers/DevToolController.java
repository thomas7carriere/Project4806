package application.controllers;

import application.models.*;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/***
 *     Tools for assisting development
 */
@Controller
class DevToolController {

    private SurveyRepository surveyRepo;
    private QuestionRepository questionRepo;
    private int surveyCount = 0;


    @Autowired
    DevToolController(SurveyRepository surveyRepo, QuestionRepository questionRepo) {
        this.surveyRepo = surveyRepo;
        this.questionRepo = questionRepo;
    }


    /***
     *  Create a survey with questions when http GET is issued
     *
     * @return
     */
    @GetMapping("/cheat")
    @ResponseBody
    private String getSurveyPage(Authentication authentication) {
        Survey survey = new Survey("DummyTestSurvey" + surveyCount++, authentication.getName());
        RangeQuestion r = new RangeQuestion("Pick a number from range", 10, 100);
        r.addAnswer(10);
        r.addAnswer(11);
        r.addAnswer(100);
        r.addAnswer(100);
        MultipleChoiceQuestion m = new MultipleChoiceQuestion("Pick an animal", Arrays.asList("dog", "cat", "hamster"));
        m.addAnswer(1);m.addAnswer(2);m.addAnswer(2);m.addAnswer(3);
        OpenEndedQuestion o = new OpenEndedQuestion("Enter some random stuff");
        o.addAnswer("hello");
        o.addAnswer("olleh");
        o.addAnswer("hello");
        survey.addQuestion(r);
        survey.addQuestion(m);
        survey.addQuestion(o);
        survey.addQuestion(new RangeQuestion("Another range question", 25, 75));

        m = new MultipleChoiceQuestion("Pick an fruit", Arrays.asList("orange", "apple", "each"));
        m.addAnswer(1);m.addAnswer(2);m.addAnswer(3);
        survey.addQuestion(m);

        survey.addQuestion(new OpenEndedQuestion("another textbox"));
        //every 2nd survey will be set to closed for testing
        if(surveyCount % 2 == 0) {
            survey.setOpen(false);
        }
        surveyRepo.save(survey);
        return "A survey with question has been created";
    }

}
