package hu.pte.schafferg.cellarManager.services;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
/**
 * Helper Service that determines if the logged in user has  the role in question.
 * @author Da3mon
 *
 */
public class RoleHelperService {
	
	/**
	 * Returns true if the logged in user has role.
	 * @param role
	 * @return
	 */
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
	
	/**
	 * Gets the current logged in user.
	 * @return
	 */
	public User getCurrentAuthenticatedUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = null;
		
		if(principal instanceof User){
			user = (User)principal;
		}
		
		return user;
	}

}
