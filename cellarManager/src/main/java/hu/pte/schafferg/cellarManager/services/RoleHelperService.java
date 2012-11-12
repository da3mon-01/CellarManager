package hu.pte.schafferg.cellarManager.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class RoleHelperService {
	
	public boolean hasRole(String role){
		boolean hasRole = false;
		Collection<GrantedAuthority> listOfAuths = getCurrentAuthenticatedUser().getAuthorities();
		for(GrantedAuthority auth: listOfAuths){
			if(auth.getAuthority().equals(role) && hasRole!=true){
				hasRole=true;
			}
		}
		
		return hasRole;
	}
	
	public User getCurrentAuthenticatedUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User){
			user = (User)principal;
		}
		
		return user;
	}

}
