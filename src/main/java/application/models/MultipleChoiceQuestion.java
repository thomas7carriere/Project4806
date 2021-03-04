package application.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
     private Collection<String> choices = new ArrayList<String>();



    /**
     * Stores a mapping of choices and the number of times that choice was picked
     */
    @ElementCollection
    private Map<String,Integer> answers = new HashMap<String,Integer>();
    /**
     * Required default constructor by JPA
     * Do not use when creating questions
     */
    public MultipleChoiceQuestion(){

    }

    /**
     * Constructor to create a multiple choice question
     * @param question a string for the name of the question
     */
    public MultipleChoiceQuestion(String question){
        super(question);
    }

    /**
     * Returns the collection of choices
     * @return the collection of choices that a user can pick
     */
    public Collection<String> getChoices() {
        return choices;
    }

    /**
     *  used for replacing the collection of choices
     * @param choices a list to replace the choices
     */
    public void setChoices(Collection<String> choices) {
        this.choices = choices;
        for(String choice : choices) {
            answers.put(choice,0);

        }
    }

    /**
     *
     * @param choice a choice to add to the list of choices
     */
    public void addchoice(String choice){
        choices.add(choice);
        answers.put(choice,0);
    }

    /**
     *
     * @param answer a choice to pick
     */
    public void addAnswer(String answer){
        answers.put(answer,answers.get(answer)+1);
    }

    /**
     *
     * @param answer name of the choice in the question
     * @return the number of times this choice was chosen
     */
    public int getAnswer(String answer){
        return answers.get(answer);
    }

    /**
     *
     * @return a mapping of the choices and anwers given for each choice
     */
    public Map<String, Integer> getAnswers() {
        return answers;
    }




}
