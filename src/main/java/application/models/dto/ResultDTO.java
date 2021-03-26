package application.models.dto;

import application.models.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * A DTO for passing the results into the view
 */
@Getter
public class ResultDTO {
    private String questionType;
    private String question;
    @Setter
    private List<String> stringAnswers;
    @Setter
    private List<List<Object>> chartData;

    /**
     * Basic constructor
     *
     * @param question question text
     * @param questionType type of question
     */
    public ResultDTO(String question, String questionType) {
        this.question = question;
        this.questionType = questionType;
    }

    /**
     * Convert the list questions insides the survey into a list of ResultDTO to be rendered on web page
     *
     * @param questions List of questions inside a survey
     * @return list of ResultDTO
     */
    static public List<ResultDTO> QuestionsToResultList (List<Question> questions) {
        List<ResultDTO> resultList = new ArrayList<>();
        for (Question question: questions){
            resultList.add(question.populateResultDTO());
        }
        return resultList;
    }
}
