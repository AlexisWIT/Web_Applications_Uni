package eRPapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eRPapp.domain.Option;
import eRPapp.domain.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer>{
	Question findByRefId(int refId);
	Question findByOption(Option option);

}
