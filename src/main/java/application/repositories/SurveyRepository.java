package application.repositories;

import application.models.Survey;
import org.springframework.data.repository.CrudRepository;

public interface SurveyRepository extends CrudRepository<Survey, Long> {

    Survey findById(long id);
}
