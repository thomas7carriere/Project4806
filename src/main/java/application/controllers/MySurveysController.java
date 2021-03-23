package application.controllers;

import application.models.Question;
import application.models.ResultDTO;
import application.models.Survey;
import application.models.SurveyDTO;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MySurveysController {

    private SurveyRepository surveyRepo;
    private QuestionRepository questionRepo;

    @Autowired
    public MySurveysController(SurveyRepository surveyRepo, QuestionRepository questionRepo) {
        this.surveyRepo = surveyRepo;
        this.questionRepo = questionRepo;
    }

    /***
     * Shows all the surveys created by the user in the system
     *
     * @param model
     * @return
     */
    @GetMapping("/mysurveys")
    public String viewSurveyList(Model model, Authentication authentication) {
        Iterable<Survey> surveys = surveyRepo.findBySurveyorUsername(authentication.getName());
        List<SurveyDTO> surveyDtoList = new ArrayList<>();
        surveys.forEach(e-> surveyDtoList.add(new SurveyDTO(e)));
        model.addAttribute("surveyDtoList", surveyDtoList);
        return "mySurveys";
    }

    @GetMapping("/mysurveys/edit/{surveyId}")
    public String editSurvey(Model model, @PathVariable String surveyId){
        Survey survey = surveyRepo.findById(Long.parseLong(surveyId));
        if (survey == null || !survey.isOpen()) {
            return "404";
        }
        else {
            SurveyDTO surveyDTO = new SurveyDTO(survey);
            model.addAttribute("surveyDto", surveyDTO);
            return "editSurvey";
        }
    }

    /**
     * Display the result of the survey associated with surveyId
     * @param model model to be passed to the view
     * @param surveyId id of the survey
     * @return name of the view
     */
    @GetMapping("/mysurveys/results/{surveyId}")
    public String surveyResults(Model model, @PathVariable String surveyId) {
        long surveyIdLong = Long.parseLong(surveyId);

        Survey survey = surveyRepo.findById(surveyIdLong);
        if (survey == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Survey doesn't exist"
            );
        }

        List<Question> questions = survey.getQuestions();
        List<ResultDTO> resultList = ResultDTO.QuestionsToResultList(questions);

        model.addAttribute("surveyName", survey.getName());
        model.addAttribute("resultList", resultList);
        return "viewResult";
    }

    /**
     * Close the survey associated with surveyId
     * @param model model to be passed to the view
     * @param surveyId id of the survey
     * @param authentication authentication data
     * @return name of the view
     */
    @GetMapping("/mysurveys/close/{surveyId}")
    public String closeSurvey(Model model, @PathVariable String surveyId, Authentication authentication) {
        Survey survey = surveyRepo.findById(Long.parseLong(surveyId));
        if (survey == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Survey doesn't exist"
            );
        } else {
            survey.setOpen(false);
        }
        surveyRepo.save(survey);
        List<SurveyDTO> surveyDtoList = new ArrayList<>();
        Iterable<Survey> surveys = surveyRepo.findBySurveyorUsername(authentication.getName());
        surveys.forEach(e -> surveyDtoList.add(new SurveyDTO(e)));
        model.addAttribute("surveyDtoList", surveyDtoList);
        return "mySurveys";
    }
 }
