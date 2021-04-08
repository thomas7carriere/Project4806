package application.controllers;

import application.csv.WriteCsvToResponse;
import application.models.*;
import application.models.dto.EditDTO;
import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import application.models.dto.SurveyDTO;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class MySurveysController {
    private static final Logger log = LoggerFactory.getLogger(WriteCsvToResponse.class);
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
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Survey doesn't exist"
            );
        }
        else {
            SurveyDTO surveyDTO = new SurveyDTO(survey);
            model.addAttribute("surveyDto", surveyDTO);
            return "editSurvey";
        }
    }

    @PatchMapping("/mysurveys/edit")
    @ResponseBody
    public Survey saveEdit(@RequestBody EditDTO editDTO){
        Survey survey = surveyRepo.findById(editDTO.getSurveyID());
        if (survey == null || !survey.isOpen()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Survey is closed or doesn't exist"
            );
        }
        Survey checkName = surveyRepo.findByName(editDTO.getSurveyName());
        if(checkName != null && checkName.getId() != editDTO.getSurveyID()){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Surveys new name is already taken"
            );
        }
        survey.setName(editDTO.getSurveyName());
        List<Question> originalQs = survey.getQuestions();
        Map<Long, String> edited = editDTO.getEdited();
        for (Map.Entry<Long, String> e : edited.entrySet()){
            for(Question q : originalQs){
                if(q.getId().equals(e.getKey())){
                    q.setQuestion(e.getValue());
                }
            }
        }
        Collection<QuestionDTO> newQuestions = editDTO.getNewQuestions();

        if(newQuestions != null){
            for(QuestionDTO question: newQuestions){
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
        }
        surveyRepo.save(survey);
        return survey;
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

    /**
     * Export the survey results as a CSV
     *
     * @param surveyId id of the survey
     * @param response response used for writting output into
     * @throws IOException when response.getWriter() fails
     */
    @GetMapping(value = "/mysurveys/export/{surveyId}.csv")
    @ResponseBody
    public void exportSurvey(@PathVariable String surveyId, HttpServletResponse response) throws IOException {
        Survey survey = surveyRepo.findById(Long.parseLong(surveyId));
        if (survey == null) {
            log.debug("User requested exporting survey %d, but it does not exist", surveyId);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Survey doesn't exist"
            );
        }
        response.setContentType("text/csv");
        WriteCsvToResponse.writeSurveySummary(response.getWriter(), survey);
        log.trace("User requested exporting survey %d", surveyId);
    }
 }
