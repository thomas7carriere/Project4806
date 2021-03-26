package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

public class TestResultDTO {

    private static final String question = "this is a random question";
    private static final String questionType = "this is an arbitrary question type";


    private ResultDTO resultDTO;

    @BeforeEach
    public void setup(){
        resultDTO = new ResultDTO(question, questionType);
    }

    @Test
    public void TestConstructor(){
        assertEquals(question, resultDTO.getQuestion());
        assertEquals(questionType, resultDTO.getQuestionType());
    }

    @Test
    public void TestStringAnswers(){
        List<String> answers = Arrays.asList("dog", "cat", "rabbit", "hamster", "wolf");
        resultDTO.setStringAnswers(answers);
        assertEquals(answers, resultDTO.getStringAnswers());
    }

    @Test
    public void TestChartData(){
        List<List<Object>> chartData = Arrays.asList(Arrays.asList("dog", "7"),
                Arrays.asList("rabbit", "12"), Arrays.asList("wolf", "181"));
        resultDTO.setChartData(chartData);
        assertEquals(chartData, resultDTO.getChartData());
    }

    @Test
    public void TestQuestionsToResultList(){
        RangeQuestion r = new RangeQuestion("Pick a number from range", 10, 100);
        r.addAnswer(10);r.addAnswer(11);r.addAnswer(100);r.addAnswer(100);
        MultipleChoiceQuestion m = new MultipleChoiceQuestion("Pick an animal", Arrays.asList("dog", "cat", "hamster"));
        m.addAnswer(1);m.addAnswer(2);m.addAnswer(2);m.addAnswer(3);
        OpenEndedQuestion o = new OpenEndedQuestion("Enter some random stuff");
        o.addAnswer("hello");o.addAnswer("olleh");o.addAnswer("hello");

        List<Question> questions = new ArrayList<>();
        questions.add(r);
        questions.add(m);
        questions.add(o);

        List<ResultDTO> resultDTOList = ResultDTO.QuestionsToResultList(questions);

        assertEquals(questions.size(), resultDTOList.size());
        assertThat(resultDTOList, hasItem(
                hasProperty("questionType", is(QuestionDTO.MULTIPLECHOICE))));
        assertThat(resultDTOList, hasItem(
                hasProperty("questionType", is(QuestionDTO.RANGE))));
        assertThat(resultDTOList, hasItem(
                hasProperty("questionType", is(QuestionDTO.OPENENDED))));
    }
}
