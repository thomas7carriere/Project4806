package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;

import javax.persistence.*;
import java.util.List;

@Entity(name="questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="question_type", discriminatorType = DiscriminatorType.INTEGER)
abstract public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    @CsvBindByName
    private Long id;
    @CsvBindByName
    private String question;

    /**
     * Delimiter used when dumping all surveys
     */
    protected static final String ANSWER_DELIMITER = "*";

    public Question(){
    }

    public Question(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return the DTO version of this object
     *
     * @return DTO object to be passed to the view
     */
    abstract public QuestionDTO toDto();

    /**
     * Return the result DTO of the answers stored in this question
     *
     * @return result DTO to be used in view result page
     */
    abstract public ResultDTO populateResultDTO();

    /**
     * Get a string representation of the type of the question
     *
     * @return an abbreviation indicating the type of question
     */
    abstract public String getType();

    /**
     * Return the options or criteria of this question as a string
     *
     * @return all options/criteria
     */
    abstract public String optionToDSV();

    /**
     * Return all answer to this question as a delimiter separated string
     *
     * @return all answers
     */
    abstract public String answersToDSV();

    /**
     * Return a summary of this question as a list of strings
     *
     * @return summary in the form of a list
     */
    abstract public List<String> getAnswerSummaryForExport();
}
