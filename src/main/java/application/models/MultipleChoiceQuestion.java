package application.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class MultipleChoiceQuestion extends Question{
    //to do
}
