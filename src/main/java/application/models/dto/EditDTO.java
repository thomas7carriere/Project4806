package application.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditDTO {
    private long surveyID;
    private String surveyName;
    private Collection<QuestionDTO> newQuestions;
    private HashMap<Long, String> edited;
}
