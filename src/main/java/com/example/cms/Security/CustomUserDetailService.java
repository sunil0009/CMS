package com.example.cms.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
public class CustomUserDetailService implements UserDetailsService {

	
	private UserRepository repo;
	
	
	
	public CustomUserDetailService(UserRepository repo) {
		super();
		this.repo = repo;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repo.findByEmail(username).map(User-> new CustomUserDetails(User))
				.orElseThrow(()->new UsernameNotFoundException("This UserNAME USER NOT FOUND"));
	}

}
