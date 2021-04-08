package application.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * Uses to transfer as a Data transfer Object for transferring survey ID, question ID and answers
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {
    private long surveyID;
    private HashMap<Long, String> answers;
}
