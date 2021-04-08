package application.controllers;

import application.models.*;
import application.models.dto.AnswerDTO;
import application.models.dto.QuestionDTO;
import application.models.dto.SurveyDTO;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
public class SurveyController{

    private final String SURVEY_CREATE = "/survey/create";
    private final String SURVEY_VIEW_LIST = "/survey/view";
    private final String SURVEY_VIEW_ID = "/survey/view/{surveyId}";
    private final String SURVEY_DELETE_ID = "/survey/delete/{surveyId}";
    private final String SURVEY_ANSWER = "/survey/answer";
    private final String HELP_PAGE = "/survey/help";

    private final SurveyRepository surveyRepo;
    private final QuestionRepository questionRepo;

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
    public Survey createSurvey(@RequestBody SurveyDTO surveyDTO, Authentication authentication){
        //Any server side checks on the Request should be done here.
        //Probably check to make sure user is a "SURVEYOR"
        //check is survey with that name already exists, return 404?
        Survey s = surveyRepo.findByName(surveyDTO.getName());
        if(s != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "entity already exists!"
            );
        }

        Survey survey = new Survey(surveyDTO.getName(), authentication.getName());
        Collection<QuestionDTO> questions = surveyDTO.getQuestions();

        for(QuestionDTO question : questions){
            switch(question.getQuestionType()){
                case QuestionDTO.OPENENDED:
                    survey.addQuestion(new OpenEndedQuestion(question.getQuestion()));
                    break;
                case QuestionDTO.RANGE:
                    survey.addQuestion(new RangeQuestion(question.getQuestion(), question.getMin(), question.getMax()));
                    break;
                case QuestionDTO.MULTIPLECHOICE:
                    survey.addQuestion(new MultipleChoiceQuestion(question.getQuestion(), question.getChoices()));
                    break;
            }
        }
        surveyRepo.save(survey);
        return survey;
    }

    /**
     * Shows the content of specific survey
     *
     * @param model
     * @param surveyId id in Survey DAO
     * @return
     */
    @GetMapping(SURVEY_VIEW_ID)
    public String viewSurvey(Model model, @PathVariable String surveyId) {
        Survey survey = surveyRepo.findById(Long.parseLong(surveyId));
        if (survey == null || !survey.isOpen()) {
            return "404"; //TODO: implement proper error pages
        } else {
            SurveyDTO surveyDTO = new SurveyDTO(survey);
            model.addAttribute("surveyDto", surveyDTO);
            return "viewSurvey";
        }
    }
    /**
     * Saves the respective answers to the question from the AnswerDTO that is provided
     * @param answerDTO data of the user submitted survey answers
     * @return
     */
    @PostMapping(SURVEY_ANSWER)
    @ResponseBody
    public Survey answerSurvey(@RequestBody AnswerDTO answerDTO) {

        Survey survey = surveyRepo.findById(answerDTO.getSurveyID());

        if (survey == null || !survey.isOpen()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Survey is closed or doesn't exist"
            );
        }
        //we could also iterate over the list of questions in the Survey, would need to make sure every question is answered though
        //the upside of going through the map is that un-submitted answers aren't really a problem
        Map<Long, String> answers = answerDTO.getAnswers();
        answers.forEach((key, value) -> System.out.println(key + ":" + value));
        for (Map.Entry<Long, String> entry : answers.entrySet()) {
            long questionId = entry.getKey();
            String questionAnswer = entry.getValue();

            Question question = questionRepo.findById(questionId);
            if(question.getClass() == MultipleChoiceQuestion.class){
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                mcQuestion.addAnswer(Integer.parseInt(questionAnswer));

            }else if(question.getClass() == RangeQuestion.class){
                RangeQuestion rangeQuestion = (RangeQuestion) question;
                rangeQuestion.addAnswer(Integer.parseInt(questionAnswer));
            }else {
                OpenEndedQuestion openQuestion = (OpenEndedQuestion) question;
                openQuestion.addAnswer(questionAnswer);
            }
        }
        surveyRepo.save(survey);
        return survey;
    }

    /***
     * Shows all surveys in the system
     *
     * @param model
     * @param surveyName name of survey to filter to
     * @return
     */
    @GetMapping(SURVEY_VIEW_LIST)
    public String viewSurveyList(Model model, @Param("surveyName") String surveyName) {
        List<SurveyDTO> surveyDtoList = new ArrayList<>();
        Iterable<Survey> surveys;
        if(surveyName == null){
            //by default show all surveys
            surveys = surveyRepo.findAll();
        } else {
            //filter surveys if survey name parameter is specified
            surveys = surveyRepo.findByNameContaining(surveyName);
        }
        surveys.forEach(e-> surveyDtoList.add(new SurveyDTO(e)));
        model.addAttribute("surveyDtoList", surveyDtoList);
        return "viewSurveyList";
    }

    /***
     * Delete survey with specified id
     *
     * @param surveyId id of survey to delete
     */
    @DeleteMapping(SURVEY_DELETE_ID)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteSurvey(@PathVariable Long surveyId){
        Survey survey = surveyRepo.findById(surveyId).orElse(null);
        if(survey == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        } else {
            surveyRepo.deleteById(surveyId);
        }
    }

    /**
     * @return the view template "helpPage.html"
     */
    @GetMapping(HELP_PAGE)
    public String getHelpPage(){

        return "helpPage";
    }
}
