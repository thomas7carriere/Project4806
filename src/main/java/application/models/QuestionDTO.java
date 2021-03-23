package application.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;
import java.util.Objects;

/**
 * A Data Transfer Object representing a Question Object
 */
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

    public QuestionDTO(){}

    public QuestionDTO(String questionType, String question, int min, int max, Collection<String> choices, Long id) {
        this.questionType = questionType;
        this.question = question;
        this.min = min;
        this.max = max;
        this.choices = choices;
        this.ID =id;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
    public Long getID() {
        return ID;
    }

    public String getQuestionType() { return questionType; }

    public void setQuestionType(String questionType) { this.questionType = questionType; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public int getMin() { return min; }

    public void setMin(int min) { this.min = min; }

    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }

    public Collection<String> getChoices() { return choices; }

    public void setChoices(Collection<String> choices) { this.choices = choices; }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionDTO that = (QuestionDTO) o;
        return min == that.min &&
                max == that.max &&
                Objects.equals(questionType, that.questionType) &&
                Objects.equals(question, that.question) &&
                Objects.equals(choices, that.choices);
    }
}
