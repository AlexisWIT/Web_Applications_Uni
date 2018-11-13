package eRPapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eRPapp.domain.AccountType;

@Repository
public interface AccountTypeRepository extends CrudRepository<AccountType, Integer>{
	AccountType findById(int typeId);

}