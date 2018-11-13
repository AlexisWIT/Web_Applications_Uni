package eRPapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eRPapp.domain.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Integer>{
	Vote findById(int voteId);

}
