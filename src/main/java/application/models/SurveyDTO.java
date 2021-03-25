package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Data Transfer Object representing a Survey Object
 */
@Getter
@Setter
@NoArgsConstructor
public class SurveyDTO {

    private String name;
    private Collection<QuestionDTO> questions;
    private boolean open;
    @JsonIgnore
    private long id;

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
        this.open = survey.isOpen();
        survey.getQuestions().forEach(e->this.questions.add(e.toDto()));
    }
}
