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
		//4811 = 687*7
    	for(String u3: getUserInfo()){
    		//687 = 98*7
	    	for(String u: getUserInfo()){
	        	//98 = 14*7
	    		for(String u2: getUserInfo()){
	    			getUserInfo();
	        	}
	    	}
    	}
   
    	//196 = 28*7
    	for(String u2: getUserInfo()){
      		getUserInfo();
    		getUserInfo();
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
