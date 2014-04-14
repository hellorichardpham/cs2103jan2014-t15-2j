import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

//@author A0097961M
/**
 * testStorage(): a simple test using JUnit by valid or invalid userinput.
 * 
 */
public class StorageTest {
	@Test
	public void testStorage() throws Exception {
		
		//A valid user input
		String userInput_Test = "add lunch with dad //location Orchard";
		
		//A invalid user input with missing details
		String userInput_Test2 = "add   0400hrs";
		
		Storage s = new Storage();
		ProcessCommand pc = new ProcessCommand();
		ExeCom ec = ExeCom.getInstance();
		
		//Process user input
		Command com = pc.process(userInput_Test);
		Command com2 = pc.process(userInput_Test2);
		
		//load all task from Storage.txt
		s.loadStorage();
		
		//Declaring a task list
		ArrayList<Task> taskList = new ArrayList<Task>();
		
		//Getting the current task list
		taskList = ExeCom.getTaskListInstance();
		
		ec.executeCommand(com);
		ec.executeCommand(com2);
		
		int size = taskList.size()-1;
		
		// By using assertEquals, we compare the details of userInput 
		// and the latest addition of the task list
		Assert.assertEquals(com.getDetails(),taskList.get(size).getDetails());
		
		// By using assertNotEquals, we compare the details of userInput2
		// and the latest addition of the task list
		// in this case, not equals is true.
		Assert.assertNotEquals(com2.getDetails(),taskList.get(size).getDetails());

	}
}