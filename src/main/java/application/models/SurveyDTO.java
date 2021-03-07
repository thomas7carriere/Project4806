package application.models;

import java.util.Collection;

/**
 * A Data Transfer Object representing a Survey Object
 */

public class SurveyDTO {

    private Collection<QuestionDTO> questions;

    public SurveyDTO() {}

    public SurveyDTO(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Collection<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<QuestionDTO> questions) {
        this.questions = questions;
    }
}
