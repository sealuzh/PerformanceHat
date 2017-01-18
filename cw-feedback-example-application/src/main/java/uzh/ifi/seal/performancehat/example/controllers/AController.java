package uzh.ifi.seal.performancehat.example.controllers;

public class AController {

	//Will Never have feedback, because is always called on ExampleController
	// and never on AController
	public void test(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
