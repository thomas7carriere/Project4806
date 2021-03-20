package application.models;

import java.util.Collection;

/**
 * Uses to transfer as a Data transfer Object for transferring survey ID, question ID and answers
 */
public class AnswerDTO {

    private int surveyID;
    private Collection<Integer> questionID;
    private Collection<String> answers;

    public AnswerDTO(int surveryID, Collection<Integer> questionID, Collection<String> answers) {
        this.surveyID = surveryID;
        this.questionID = questionID;
        this.answers = answers;
    }

    public Collection<Integer> getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Collection<Integer> questionID) {
        this.questionID = questionID;
    }

    public Collection<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<String> answers) {
        this.answers = answers;
    }



    public int getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(int surveyID) {
        this.surveyID = surveyID;
    }

}
