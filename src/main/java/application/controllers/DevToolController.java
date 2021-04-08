package application.controllers;

import application.csv.WriteCsvToResponse;
import application.models.*;
import application.repositories.QuestionRepository;
import application.repositories.SurveyRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/***
 *     Tools for assisting development
 */
@Controller
class DevToolController {
    private static final Logger log = LoggerFactory.getLogger(DevToolController.class);

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
        log.info("DevTool cheat function was triggered");
        return "A survey with question has been created";
    }

    /***
     * Dev tool for exporting all surveys on this system
     * @param response response used for writting output into
     * @throws IOException when response.getWriter() fails
     */
    @GetMapping(value = "export", produces = "text/csv")
    public void closeSurvey(HttpServletResponse response) throws IOException {
        Iterable<Survey> surveys = surveyRepo.findAll();
        List<Survey> list = Lists.newArrayList(surveys);

        WriteCsvToResponse.writeSurveys(response.getWriter(), list);
        log.info("DevTool export function was triggered with {} surveys", list.size());
    }

    private final static List<String> VALID_LEVELS =  Lists.newArrayList("TRACE", "DEBUG", "INFO", "WARN", "ERROR");

    /***
     * Developer tool used to set the log level of this software package
     * @param logLevel logback log level
     * @return status message directly in response body
     */
    @GetMapping("/log_{logLevel}")
    @ResponseBody
    private String setLogLevel(@PathVariable String logLevel) {
        if (VALID_LEVELS.contains(logLevel)) {
            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            ch.qos.logback.classic.Logger logger = loggerContext.getLogger("application");
            logger.setLevel(Level.toLevel(logLevel));

            return String.format("logLevel changed to %s", logLevel);
        } else {
            return String.format("invalid log level %s", logLevel);
        }
    }
}
