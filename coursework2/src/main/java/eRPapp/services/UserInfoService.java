package eRPapp.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eRPapp.BallotApplication;
import eRPapp.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserInfoService implements UserDetailsService {
	
	@Autowired private UserRepository userRepository;
	
	private Collection<? extends GrantedAuthority> getAuthority(Integer accountType) {
		List<GrantedAuthority> authorityList = getGrantedAuthority(getAccountType(accountType));
		return authorityList;
		
	}
	
	private List<String> getAccountType(Integer accountType) {
		
		List<String> accountTypes = new ArrayList<String>();
		
		switch (accountType.intValue()) {
		case BallotApplication.COMMISSION:
			accountTypes.add("ROLE_ADMIN");
			break;
		case BallotApplication.VOTER:
			accountTypes.add("ROLE_VOTER");
			break;
		}
		return accountTypes;
	}
	
	public static List<GrantedAuthority> getGrantedAuthority(List<String> accountTypes) {
		List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
		for (String accountType : accountTypes) {
			authority.add(new SimpleGrantedAuthority(accountType));
		}
		return authority;
	}
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		eRPapp.domain.User domainUser = userRepository.findByEmail(email);
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		return new User ( 
			domainUser.getEmail(), 
			domainUser.getPassword(),
			enabled, 
			accountNonExpired, 
			credentialsNonExpired, 
			accountNonLocked,
			getAuthority(domainUser.getAccountType().getId())
		);
		
	}


}
