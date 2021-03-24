package application.models;

import java.util.*;

/**
 * A DTO for passing the results into the view
 */
public class ResultDTO {
    private String questionType;
    private String question;
    private List<String> stringAnswers;
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
     * Get the question type of this result DTO
     *
     * @return question type
     */
    public String getQuestionType() {
        return questionType;
    }

    /**
     * Get the question text of this result DTO
     *
     * @return question type
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get a List of answers, suitable for open-ended question
     *
     * @return list of answers to be rendered on webpage
     */
    public List<String> getStringAnswers() {
        return stringAnswers;
    }

    /**
     * Set the List of answers, suitable for open-ended question
     *
     */
    void setStringAnswers(List<String> stringAnswers) {
        this.stringAnswers = stringAnswers;
    }

    /**
     * Get the chart data to be passed to Google Charts
     *
     * Refer to this page for data format https://developers.google.com/chart/interactive/docs/gallery
     *
     * @return chart data
     */
    public List<List<Object>> getChartData() {
        return chartData;
    }

    /**
     * Get the chart data to be passed to Google Charts
     *
     * Refer to this page for data format https://developers.google.com/chart/interactive/docs/gallery
     * This data type shoulb work for most of the charts
     *
     * @param chartData chart data required by Google Chart
     */
    public void setChartData(List<List<Object>> chartData) {
        this.chartData = chartData;
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
