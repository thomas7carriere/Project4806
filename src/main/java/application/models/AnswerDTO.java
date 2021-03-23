package application.models;

import java.util.HashMap;

/**
 * Uses to transfer as a Data transfer Object for transferring survey ID, question ID and answers
 */
public class AnswerDTO {

    private long surveyID;
    private HashMap<Long, String> answers;

    public AnswerDTO() {
    }

    public AnswerDTO(long surveyID, HashMap<Long, String> answers) {
        this.surveyID = surveyID;
        this.answers = answers;
    }


    public HashMap<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<Long, String> answers) {
        this.answers = answers;
    }


    public long getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(long surveyID) {
        this.surveyID = surveyID;
    }

}
