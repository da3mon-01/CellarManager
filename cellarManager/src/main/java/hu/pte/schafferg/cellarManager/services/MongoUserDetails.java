package hu.pte.schafferg.cellarManager.services;

import hu.pte.schafferg.cellarManager.repo.UserRepository;

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

public class MongoUserDetails implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		hu.pte.schafferg.cellarManager.model.User userInDB = userRepo.findByUsername(username);
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		
		return new User( userInDB.getUsername(),
						 userInDB.getPassword(),
						 enabled, 
						 accountNonExpired, 
						 credentialsNonExpired, 
						 accountNonLocked, 
						 getAuthorities(userInDB.getRole().getRole()));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<String> listOfRoles = new ArrayList<String>();
		if(role.intValue() == 1){
			listOfRoles.add("ROLE_USER");
			listOfRoles.add("ROLE_ADMIN");
		} else if(role.intValue() == 2){
			listOfRoles.add("ROLE_USER");
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		for(String s: listOfRoles){
			grantedAuthorities.add(new SimpleGrantedAuthority(s));
		}
		
		return grantedAuthorities;
	}

}
