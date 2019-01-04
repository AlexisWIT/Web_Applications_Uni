package gEapp.repository;

import org.springframework.data.repository.CrudRepository;

import gEapp.domain.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {

	Member findByName(String name);
}
