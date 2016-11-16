package uzh.ifi.seal.performancehat.example.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import uzh.ifi.seal.performancehat.example.interfaces.IDatabase;
import uzh.ifi.seal.performancehat.example.stubs.DatabaseStub;

@Controller
public class ExampleController {

    @RequestMapping("/example")
    public String example(Model model){
        return "example";
    }
    
    
	
    @RequestMapping("/users")
    public String greeting(Model model) {
    	for(String u3: getUserInfo()){
	    	for(String u: getUserInfo()){
	    		for(String u2: getUserInfo()){
	    			if(true){
		    			getUserInfo();	    				
	    			} else {
		    			getUserInfo();	    					    				
	    			}
	        	}
	    	}
    	}

  
    	for(int i = 0; i < 5; i++){
    		getUserInfo();	 
    	}
    	
      	for(String u2: getUserInfo()){
    		try{
          		getUserInfo();
    		} catch(Exception e){
        		getUserInfo();
    		} finally{
        		getUserInfo();
    		}
    	}
    	
    	List<String> userList = getUserInfo();
        model.addAttribute("userList", userList);
        return "users";
        
    }
    
    
    private List<String> getUserInfo(){
    	IDatabase myDB = new DatabaseStub();
    	List<String> userList = myDB.getUserInfo();
    	return userList;
    }

    
}
