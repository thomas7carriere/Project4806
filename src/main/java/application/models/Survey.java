package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Question> questions = new ArrayList<>();

    private String name;

    /**
     * Default constructor
     */
    public Survey() {

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
     * Sets name of survey
     *
     * @param name is the new name of the survey
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the name of the survey
     *
     * @return the name of the survey
     */
    public String getName(){
        return this.name;
    }

    /**
     * Sets the survey id
     * @param id is the new id of the survey
     */
    public void setId(long id){
        this.id = id;
    }

    /**
     * Gets the id of the survey
     *
     * @return the id of the survey
     */
    public long getId(){
        return this.id;
    }

    /**
     * Gets the list of questions in the survey
     *
     * @return the list of questions
     */
    public List<Question> getQuestions(){
        return this.questions;
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

    /**
     * Sets the surveys list of questions
     *
     * @param questions the list of questions
     */
    public void setQuestions(List<Question> questions){
        this.questions = questions;
    }

}