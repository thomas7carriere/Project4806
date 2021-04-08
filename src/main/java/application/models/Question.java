package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="question_type", discriminatorType = DiscriminatorType.INTEGER)

@Getter
@Setter
@NoArgsConstructor
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

    public Question(String question){
        this.question = question;
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
