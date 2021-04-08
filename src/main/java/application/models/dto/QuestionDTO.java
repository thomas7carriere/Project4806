package application.models.dto;

import application.models.MultipleChoiceQuestion;
import application.models.OpenEndedQuestion;
import application.models.Question;
import application.models.RangeQuestion;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Collection;

/**
 * A Data Transfer Object representing a Question Object
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
