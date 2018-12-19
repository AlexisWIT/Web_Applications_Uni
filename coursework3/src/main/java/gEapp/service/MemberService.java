package gEapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gEapp.domain.Member;
import gEapp.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	public Object findAllMembers(){
		return memberRepository.findAll();
	}
	
	
	public Member findById(Integer i){
		return memberRepository.findOne(i);
	}
	
	public void deleteById(Integer id){
		memberRepository.delete(id);;
	}
	
	
	public void save(Member a){
		memberRepository.save(a);
	}

}
