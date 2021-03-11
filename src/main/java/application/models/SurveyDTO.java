package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Data Transfer Object representing a Survey Object
 */

public class SurveyDTO {

    private String name;

    private Collection<QuestionDTO> questions;
    @JsonIgnore
    private long id;

    public SurveyDTO() {}

    public SurveyDTO(Collection<QuestionDTO> questions) {
        this("", questions);
    }

    public SurveyDTO(String name, Collection<QuestionDTO> questions) {
        this.name = name;
        this.questions = questions;
    }

    /**
     * Construct DTO version of the Survey object
     *
     * @param survey Survey DAO
     */
    public SurveyDTO(Survey survey) {
        this.id = survey.getId();
        this.name = survey.getName();
        this.questions = new ArrayList<>();
        survey.getQuestions().forEach(e->this.questions.add(e.toDto()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
