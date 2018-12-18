package gEapp.repository;

import org.springframework.data.repository.CrudRepository;

import gEapp.domain.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {
	
	Member findById(int id);
	Member findByMum(int mumKey);

}
