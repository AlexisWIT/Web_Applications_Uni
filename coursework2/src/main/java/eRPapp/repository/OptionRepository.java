package eRPapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eRPapp.domain.Option;

@Repository
public interface OptionRepository extends CrudRepository<Option, Integer>{
	Option findById(int id);

}
