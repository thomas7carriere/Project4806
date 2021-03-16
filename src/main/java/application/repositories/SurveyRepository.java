package application.repositories;

import application.models.Survey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurveyRepository extends CrudRepository<Survey, Long> {

    Survey findById(long id);
    Survey findByName(String name);
    List<Survey> findBySurveyorUsername(String surveyorUsername);

}
