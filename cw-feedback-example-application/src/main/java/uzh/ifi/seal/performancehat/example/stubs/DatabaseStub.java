package uzh.ifi.seal.performancehat.example.stubs;

import java.util.ArrayList;
import java.util.List;

import uzh.ifi.seal.performancehat.example.interfaces.IDatabase;
import uzh.ifi.seal.performancehat.example.interfaces.IUser;

public class DatabaseStub implements IDatabase {
	
	private List<IUser> userList = new ArrayList<IUser>();
	
	public DatabaseStub() {
		userList.add(new UserStub("jdoe", "publisher", "john.doe@example.com"));
		userList.add(new UserStub("fdavid", "administrator", "frank.david@example.com"));
		userList.add(new UserStub("pleiter", "administrator", "philipp.leitner@example.com"));
		userList.add(new UserStub("bjohnson", "publisher", "bob.johnson@example.com"));
		userList.add(new UserStub("tlee", "publisher", "tim.lee@example.com"));
		userList.add(new UserStub("vmeyer", "publisher", "victoria.mayer@example.com"));
		userList.add(new UserStub("jstevens", "publisher", "jessica.stevens@example.com"));
	
	}
		
	public List<String> getUserNames() {
		List<String> usernames = new ArrayList<String>();
		for(IUser user : userList){
			usernames.add(user.getUsername());
		}
		return usernames;
	}
	
	public List<String> getUserEmails() {
		List<String> useremails = new ArrayList<String>();
		for(IUser user : userList){
			useremails.add(user.getEmail());
		}	
		return useremails;
		
	}
	
	
	public List<String> getUserInfo() {
		List<String> userInfoList = new ArrayList<String>();
		for(IUser user: userList){
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			userInfoList.add("User " + user.getUsername() + " has role " + user.getRole() + " and has E-Mail address " + user.getEmail());
		}
		
		return userInfoList;
		
	}

}
