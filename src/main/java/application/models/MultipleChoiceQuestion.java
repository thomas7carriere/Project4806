package application.models;

import application.models.dto.QuestionDTO;
import application.models.dto.ResultDTO;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Subclass of Question class
 * Create a multiple choice question
 */
@Entity
@DiscriminatorValue("1")
public class MultipleChoiceQuestion extends Question{

    /**
     *  Holds the collection of choices a user can choose from
     */
    @ElementCollection
    @Getter
     private Collection<String> choices;

    /**
     * Stores a mapping Unique Id to the string representation of the choice
     */
    @ElementCollection
    @Getter
    private Map<Integer,String> choicesID = new HashMap<Integer,String>();

    /**
     * Stores a mapping of the Unique id of choices to the number of times that choice was chosen
     */
    @ElementCollection
    private Map<Integer,Integer> answersValues = new HashMap<Integer,Integer>();

    /**
     * Used for the IDs of the choices
     */
    private Integer idMapping = 1;

    /**
     * Required default constructor by JPA
     * Do not use when creating questions
     */
    public MultipleChoiceQuestion(){
        super();
    }

    /**
     * Constructor to create a multiple choice question
     * @param question a string for the name of the question
     * @param choices a collection of strings to be used for the choices
     */
    public MultipleChoiceQuestion(String question, Collection<String> choices){
        super(question);
        setChoices(choices);
    }

    /**
     *  used for replacing the collection of choices
     * @param choices a list to replace the choices
     */
    public void setChoices(Collection<String> choices) {
        this.choices = choices;
        //Sets a unique ID for each choice and stores them in choicesID map
        for(String choice : choices) {
            choicesID.put(idMapping,choice);
            updateMapID();
        }
        //Stores each unique ID from choicesID to set the key in answersValues with 0 initial answers
        for(Integer id : choicesID.keySet()) {
            answersValues.put(id,0);
        }
    }

    /**
     * Used internally to update the unique id of a choice
     */
    private void updateMapID(){
        idMapping ++;
    }

    /**
     * Adds a choice to the list of choices and adds the choice to the
     * answers with an initial value of zero answers.
     * @param choice a choice to add to the list of choices
     */
    public void addChoice(String choice){
        choices.add(choice);
        choicesID.put(idMapping,choice);
        answersValues.put(idMapping,0);
        updateMapID();
    }

    /**
     * Locates the choice given and updates the number of times that choice
     * was picked by 1.
     * @param answer a choices ID used to map to that choice and update the answer
     */
    public void addAnswer(int answer){
        answersValues.put(answer, answersValues.get(answer)+1);
    }

    /**
     * Used to return the amount of times a specific choice was picked
     * @param answer a choices ID
     * @return the number of times this choice was chosen
     */
    public int getAnswer(int answer){
        return answersValues.get(answer);
    }

    /**
     * Used to return a map of the unique IDs for each choice
     * and the number of times each choice was chosen
     * @return a map of the corresponding choice ID and number of this chosen.
     */
    public Map<Integer, Integer> getAnswersValues() {
        return answersValues;
    }

    @Override
    public QuestionDTO toDto() {
        return new QuestionDTO(QuestionDTO.MULTIPLECHOICE, this.getQuestion(), 0, 0, this.choices,super.getId());
    }

    @Override
    public ResultDTO populateResultDTO() {
        ResultDTO resultDTO = new ResultDTO(getQuestion(), QuestionDTO.MULTIPLECHOICE);
        List<List<Object>> list = new ArrayList<>();
        answersValues.forEach((k,v)-> list.add(Arrays.asList(choicesID.get(k), v)));
        resultDTO.setChartData(list);

        return resultDTO;
    }

    @Override
    public String getType() {
        return "MC";
    }

    @Override
    public String optionToDSV() {
        Iterator<Map.Entry<Integer, String>> it = choicesID.entrySet().iterator();
        StringBuilder sb = new StringBuilder();

        while (it.hasNext()) {
            Map.Entry<Integer, String> e = it.next();
            sb.append(String.format("%d:%s", e.getKey(), e.getValue()));
            if (it.hasNext()) {
                sb.append("|");
            }
        }

        return sb.toString();
    }

    @Override
    public String answersToDSV() {
        return answersValues.values().stream().map(String::valueOf).collect(Collectors.joining(Question.ANSWER_DELIMITER));
    }

    @Override
    public List<String> getAnswerSummaryForExport() {
        long sum = 0;
        List<String> summary = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : answersValues.entrySet()) {
            sum += entry.getValue();
        }

        summary.add("Answer Options,\"Response Percent\",\"Response Count\"");
        for (Map.Entry<Integer, String> entry : choicesID.entrySet()) {
            Integer response_count = answersValues.get(entry.getKey());
            String str = String.format("%s,\"%.2f%%\",%d",
                    entry.getValue(),
                    (float) response_count / sum * 100,
                    response_count);
            summary.add(str);
        }

        return summary;
    }
}
