package application.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collection;

/**
 * A Data Transfer Object representing a Question Object
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class QuestionDTO {

    private String questionType;
    private String question;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int min;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int max;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<String> choices;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Long ID;

    public static final String OPENENDED = "openEnded", RANGE = "range", MULTIPLECHOICE = "multipleChoice";

    public QuestionDTO(String questionType, String question, int min, int max, Collection<String> choices, Long id) {
        this.questionType = questionType;
        this.question = question;
        this.min = min;
        this.max = max;
        this.choices = choices;
        this.ID =id;
    }

    @Override
    public String toString(){
        String str = "QuestionType: " + questionType + " Question: " + question;
        switch(questionType) {
        case "openEnded":
            break;

        case "range":
            str += " Min: " + min + " Max: " + max;
            break;

         case "multipleChoice":
             for(String choice : choices){
                 str += " Choice: " + choice;
             }
             break;
        }
    return str;
    }
}
