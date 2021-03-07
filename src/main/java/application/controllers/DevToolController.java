package application.controllers;

import application.models.*;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/*
    Tools for assisting development
 */
@Controller
class DevToolController {

    private SurveyRepository surveyRepo;
    private QuestionRepository questionRepo;

    @Autowired
    DevToolController(SurveyRepository surveyRepo, QuestionRepository questionRepo) {
        this.surveyRepo = surveyRepo;
        this.questionRepo = questionRepo;
    }


    /*
        Create a survey with questions when http GET is issued
     */
    @GetMapping("/cheat")
    @ResponseBody
    String getSurveyPage() {
        Survey survey = new Survey();
        survey.addQuestion(new RangeQuestion("Pick a number from range", 10, 100));
        survey.addQuestion(new MultipleChoiceQuestion("Pick an animal", Arrays.asList("dog", "cat", "hamster")));
        survey.addQuestion(new OpenEndedQuestion("Enter some random stuff"));
        survey.addQuestion(new RangeQuestion("Another range question", 25, 75));
        survey.addQuestion(new MultipleChoiceQuestion("Another multiple choice question",
                Arrays.asList("red", "blue", "yellow")));
        survey.addQuestion(new OpenEndedQuestion("another textbox"));
        surveyRepo.save(survey);

        return "A survey with question has been created";
    }

}
