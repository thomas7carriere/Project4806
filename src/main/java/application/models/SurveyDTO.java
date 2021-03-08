package application.models;

import java.util.Collection;

/**
 * A Data Transfer Object representing a Survey Object
 */

public class SurveyDTO {

    private String name;

    private Collection<QuestionDTO> questions;

    public SurveyDTO() {}

    public SurveyDTO(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }

    public SurveyDTO(String name, Collection<QuestionDTO> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Collection<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }
}