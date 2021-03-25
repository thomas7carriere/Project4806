package application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
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
    private Long id;
    private String question;

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

}
