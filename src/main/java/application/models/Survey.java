package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Survey {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    //Not sure if "surveyorUsername" and "open" should be in SurveyDTO as well.
    //If they are added to SurveyDto, JsonIgnore needs to be removed here, and tests updated
    @JsonIgnore
    private String surveyorUsername;
    @JsonIgnore
    private boolean open = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    //Surveys need a unique name
    @Column(unique=true)
    private String name;

    /**
     * Constructor that takes the Survey's Name and the Surveyor's username
     */
    public Survey(String name, String surveyorUsername) {
        this.name = name;
        this.surveyorUsername = surveyorUsername;
    }

    /**
     * Constructor for specified survey
     *
     * @param name is the name of the survey
     * @param questions the questions contained in the survey
     */
    public Survey(String name, List<Question> questions){
        this.name = name;
        this.questions = questions;
    }

    /**
     * Adds a question to the surveys List of questions
     *
     * @param q the new question to be added to the survey
     */
    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    /**
     * Removes a question from the surveys list of questions
     *
     * @param q the question to be removed
     */
    public boolean removeQuestion(Question q){
        return this.questions.remove(q);
    }
}