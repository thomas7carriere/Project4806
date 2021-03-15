package application.controllers;

import application.models.Survey;
import application.models.SurveyDTO;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
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
 }
