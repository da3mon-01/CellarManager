package hu.pte.schafferg.cellarManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
	
	/**
	 * 
	 */

	@Id
	private String id;
	private String username;
	private String password;
	@DBRef
	private Role role;
	@DBRef
	private Person person;
	
	
	
	
	public User() {
		
	}
	
	
	@PersistenceConstructor
	public User(String id, String username, String password, Role role, Person person) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.person = person;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	
	

}
