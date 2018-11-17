package eRPapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eRPapp.domain.BioIdCode;

@Repository
public interface BioIdCodeRepository extends CrudRepository<BioIdCode, Integer> {
	BioIdCode findById(int id);
	BioIdCode findByBioIdCode(String bioIdCode);

}
