package application.models;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Data Transfer Object representing a Survey Object
 */

public class SurveyDTO {

    private String name;

    private Collection<QuestionDTO> questions;
    private long id;

    public SurveyDTO() {}

    public SurveyDTO(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }

    public SurveyDTO(String name, Collection<QuestionDTO> questions) {
        this.name = name;
        this.questions = questions;
    }

    public SurveyDTO(Survey survey) {
        this.id = survey.getId();
        questions = new ArrayList<>();
        for (Question question : survey.getQuestions()) {
            questions.add(question.toDto());
        }
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
