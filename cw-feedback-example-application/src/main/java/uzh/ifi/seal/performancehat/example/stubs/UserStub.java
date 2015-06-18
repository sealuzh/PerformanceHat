package uzh.ifi.seal.performancehat.example.stubs;

import uzh.ifi.seal.performancehat.example.interfaces.IUser;

public class UserStub implements IUser {

	private String username;
	private String role;
	private String email;
	
	public UserStub(String username, String role, String email){
		this.username = username;
		this.role = role;
		this.email = email;
	}
	
	
	public String getUsername() {
		return this.username;
	}

	public String getRole() {
		return this.role;
	}

	public String getEmail() {
		return this.email;
	}

}
