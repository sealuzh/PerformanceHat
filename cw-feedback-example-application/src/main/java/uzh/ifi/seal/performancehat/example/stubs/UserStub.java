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
	
	
	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getRole() {
		return this.role;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

}
