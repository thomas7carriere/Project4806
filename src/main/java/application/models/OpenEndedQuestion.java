package application.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A subclass of the base Question class
 *
 * Basically a huge text box where you can input whatever you want
 */
@Entity
@DiscriminatorValue("3")
public class OpenEndedQuestion extends Question{

    /**
     * All the answers provided by user to this question
     */
    @ElementCollection
    private List<String> answer = new LinkedList<>();

    /**
     * Default constructor required by JPA
     */
    public OpenEndedQuestion() {
        super();
    }

    /**
     * Constructs a TextQuestion containing an specific question
     *
     * @param question the question to be asked
     */
    public OpenEndedQuestion(String question) {
        super(question);
    }

    /**
     * Return the collection of answers to this question
     *
     * @return the collection of answers to this question
     */
    public Collection<String> getAnswer() {
        return answer;
    }

    /**
     * Replace the collection of answers with the specified collection
     *
     * @param answer collection used to replaced existing answer
     */
    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }

    /**
     * Add an answer to the collection of answers
     *
     * @param answer answer to be added into the collection
     * @return true if this collection changed as a result of the call
     */
    public boolean addAnswer(String answer) {
        return this.answer.add(answer);
    }

    @Override
    public QuestionDTO toDto() {
        return new QuestionDTO(QuestionDTO.OPENENDED, this.getQuestion(), 0, 0, new ArrayList<>(),super.getId());
    }

    @Override
    public ResultDTO populateResultDTO() {
        ResultDTO resultDTO = new ResultDTO(getQuestion(), QuestionDTO.OPENENDED);
        resultDTO.setStringAnswers(new ArrayList<>(this.getAnswer()));

        return resultDTO;
    }

    @Override
    public String getType() {
        return "OE";
    }

    @Override
    public String optionToDSV() {
        return "";
    }

    @Override
    public String answersToDSV() {
        return String.join(Question.ANSWER_DELIMITER, answer);
    }

    @Override
    public List<String> getAnswerSummaryForExport() {
        return answer;
    }
}
