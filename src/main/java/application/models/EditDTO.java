package application.models;

import java.util.Collection;
import java.util.HashMap;

public class EditDTO {
    private long surveyID;
    private String surveyName;
    private Collection<QuestionDTO> newQuestions;
    private HashMap<Long, String> edited;

    public EditDTO(){}

    public EditDTO(long surveyID, Collection<QuestionDTO> questions, HashMap<Long, String> edited, String surveyName){
        this.surveyID = surveyID;
        this.newQuestions = questions;
        this.edited = edited;
        this.surveyName = surveyName;
    }

    public Collection<QuestionDTO> getNewQuestions() {
        return newQuestions;
    }

    public HashMap<Long, String> getEdited() {
        return edited;
    }

    public long getSurveyID() {
        return surveyID;
    }

    public void setEdited(HashMap<Long, String> edited) {
        this.edited = edited;
    }

    public void setNewQuestions(Collection<QuestionDTO> newQuestions) {
        this.newQuestions = newQuestions;
    }

    public void setSurveyID(long surveyID) {
        this.surveyID = surveyID;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
}
