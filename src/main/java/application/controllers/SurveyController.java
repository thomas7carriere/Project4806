package application.controllers;

import application.models.*;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class SurveyController{

    private final String SURVEY_CREATE = "/survey/create";
    private final String SURVEY_VIEW_LIST = "/survey/view";
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
        survey.setName(surveyDTO.getName());
        surveyRepo.save(survey);
        return survey;
    }


    @GetMapping(SURVEY_VIEW_ID)
    public String viewSurvey(Model model, @PathVariable String surveyId) {
        Survey survey = surveyRepo.findById(Long.parseLong(surveyId));
        if (survey == null) {
            return "404"; //TODO? add more detailed message?
        } else {
            SurveyDTO surveyDTO = new SurveyDTO(survey);
            model.addAttribute("surveyDTO", surveyDTO);
            return "viewSurvey";
        }
    }

    @GetMapping(SURVEY_VIEW_LIST)
    public String viewSurveyList(Model model) {
        Iterable<Survey> surveys = surveyRepo.findAll();
        List<SurveyDTO> surveyDtoList = new ArrayList<>();
        surveys.forEach(e-> surveyDtoList.add(new SurveyDTO(e)));

        model.addAttribute("surveyDtoList", surveyDtoList);
        return "viewSurveyList";
    }
}