package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import com.google.common.collect.*;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.*;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
/**
 * This is the subclass of Question class.
 * This class will allow users to create a question that they can answer on a scale of
 * a minimum number (least likely to agree) to maximum number (most likely to agree) selected.
 */

@Entity
@DiscriminatorValue("2")
@Getter
@Setter
public class RangeQuestion extends Question
{
    protected int min;
    protected int max;
    @ElementCollection
    private List<Integer> answer = new LinkedList<>();

    /**
     * Default constructor
     */
    public RangeQuestion(){
        super();
    }

    /**
     * Constructor for the specified question
     *
     * @param question is the question being asked
     * @param min the minimum value a person can select, represents least likely to agree with the question
     * @param max the minimum value a person can select, represents most likely to agree with the question
     */
    public RangeQuestion(String question, int min, int max){
        super(question);
        this.min = min;
        this.max = max;
    }

    /**
     * Adds a new answer to the already established collection
     *
     * @param answer is an integer in the range of variables min and max
     * @return true if the answer was added successfully
     */
    public boolean addAnswer(Integer answer){
        return this.answer.add(answer);
    }

    @Override
    public QuestionDTO toDto() {
        return new QuestionDTO(QuestionDTO.RANGE, this.getQuestion(), this.min, this.max, new ArrayList<>(),super.getId());
    }

    @Override
    public ResultDTO populateResultDTO() {
        ResultDTO resultDTO = new ResultDTO(getQuestion(), QuestionDTO.RANGE);
        List<List<Object>> list = new ArrayList<>();
        answer.forEach(e-> list.add(Arrays.asList("",e)));
        resultDTO.setChartData(list);

        return resultDTO;
    }

    @Override
    public String getType() {
        return "R";
    }

    @Override
    public String optionToDSV() {
        return String.format("%d|%d", min, max);
    }

    @Override
    public String answersToDSV() {
        return answer.stream().map(String::valueOf).collect(Collectors.joining(Question.ANSWER_DELIMITER));
    }

    @Override
    public List<String> getAnswerSummaryForExport() {
        List<String> summary = new ArrayList<>();
        SortedMultiset<Integer> ms = TreeMultiset.create(answer);
        summary.add("Answer,\"Response Count\"");
        ms.elementSet().forEach(e -> summary.add(String.format("%d,%d", e, ms.count(e))));
        return summary;
    }
}
