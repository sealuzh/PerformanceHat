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
   
    public void grill(){
    	grull();
    	grall();
    }
    
    
    public void grall(){
        greeting(null);
    }
    
    public void grull(){
        greeting(null);
    }
    
    
    @RequestMapping("/users")
    public String greeting(Model model) {
    	test:for(String u3: getUserInfo()){
	    	for(String u: getUserInfo()){
	    		for(String u2: getUserInfo()){
	    			//# branches: 0.5, 0.5
	    			blubb:if(true){
		    			getUserInfo();	    				
	    			} else {
		    			getUserInfo();	    					    				
	    			}
	        	}
	    	}
    	}

    	int x = getUserInfo().size();
   
    	for(int i = 0; i < x; i++){
    		lulo:getUserInfo();	 
    	}
   
    	
      	for(String u2: getUserInfo()){
    		lala:try{
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
    	int x = getUserInfo().size();
    	for(int i = 0; i < x; i++){
    		lulo:getUserInfo();	 
    	}
    	List<String> userList = myDB.getUserInfo();
    	return userList;
    }

    
}
