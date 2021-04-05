package application.models;

import application.csv.TextToQuestion;
import application.csv.WriteCsvToResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    @CsvBindByName
    private long id;

    //Not sure if "surveyorUsername" and "open" should be in SurveyDTO as well.
    //If they are added to SurveyDto, JsonIgnore needs to be removed here, and tests updated
    @JsonIgnore
    @CsvBindByName
    private String surveyorUsername;
    @JsonIgnore
    @CsvBindByName
    private boolean open = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @CsvBindAndSplitByName(elementType = Question.class, splitOn = ";+", writeDelimiter = ";", converter = TextToQuestion.class)
    private List<Question> questions = new ArrayList<>();

    //Surveys need a unique name
    @Column(unique=true)
    @CsvBindByName
    private String name;

    /**
     * Default constructor
     */
    public Survey() {

    }

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
     * Gets the username of the Surveyor that created the Survey
     *
     * @return username of Surveyor
     */
    public String getSurveyorUsername() {
        return surveyorUsername;
    }

    /**
     * Sets the username of the Surveyor that created the Survey
     *
     * @param surveyorUsername the name of the Surveyor
     */
    public void setSurveyorUsername(String surveyorUsername) {
        this.surveyorUsername = surveyorUsername;
    }

    /**
     * @return the status of the Survey (Open = true, Closed = false)
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @param open status of the Survey (Open = true, Closed = false)
     */

    public void setOpen(boolean open) {
        this.open = open;
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