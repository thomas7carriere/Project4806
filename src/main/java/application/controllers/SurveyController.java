package application.controllers;

import application.models.*;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class SurveyController{

    private final String SURVEY_CREATE = "/survey/create";
    private final String SURVEY_VIEW_ID = "/survey/view/{surveyId}";
    private final String OPENENDED = "openEnded", RANGE = "range", MULTIPLECHOICE = "multipleChoice";


    private SurveyRepository surveyRepo;
    private QuestionRepository questionRepo;

    @Autowired
    public SurveyController(SurveyRepository surveyRepo, QuestionRepository questionRepo) {
        this.surveyRepo = surveyRepo;
        this.questionRepo = questionRepo;
    }

    /**
     * @return the view template "createSurvey.html"
     */
    @GetMapping(SURVEY_CREATE)
    public String getSurveyPage(){
        return "createSurvey";
    }

    /**
     * Creates a Survey object and persists it. In the future, maybe it should redirect the user to their survey link.
     * @param surveyDTO data of the user submitted survey
     * @return
     */
    @PostMapping(SURVEY_CREATE)
    @ResponseBody
    public Survey createSurvey(@RequestBody SurveyDTO surveyDTO){
        //Any server side checks on the Request should be done here.
        Survey survey = new Survey();
        Collection<QuestionDTO> questions = surveyDTO.getQuestions();

        for(QuestionDTO question : questions){
            switch(question.getQuestionType()){
                case OPENENDED:
                    survey.addQuestion(new OpenEndedQuestion(question.getQuestion()));
                    break;
                case RANGE:
                    survey.addQuestion(new RangeQuestion(question.getQuestion(), question.getMin(), question.getMax()));
                    break;
                case MULTIPLECHOICE:
                    survey.addQuestion(new MultipleChoiceQuestion(question.getQuestion(), question.getChoices()));
                    break;
            }
        }
        surveyRepo.save(survey);
        return survey;
    }
}