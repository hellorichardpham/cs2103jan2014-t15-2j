//@Author A0118590A
/*Testing the Update class. 
 * Specifically, we want to make sure the editContent(Command) method works.
 */
import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class UpdateTest {
	ExeCom ec = ExeCom.getInstance();
	Update update = new Update();
	Add a = new Add(ec.getTaskListInstance());
	ArrayList<Task> tempTaskList = ec.getTaskListInstance();
	ArrayList<String> targetedTasks = new ArrayList<String>();
	@Test
	public void test() throws Exception {
		//Add taskID that we will update.
		targetedTasks.add("1");
		Command addCommand = new Command();
		addCommand.setKeyword("add");
		addCommand.setDetails("lunch");
		addCommand.setStartDay("12");
		addCommand.setStartMonth("04");
		addCommand.setStartYear("2014");
		addCommand.setEndDay("14");
		addCommand.setEndMonth("04");
		addCommand.setEndYear("2014");
		addCommand.setPriority("high");
		addCommand.setCategory("school");
		addCommand.setLocation("Jurong East");
		addCommand.setStartHours("13");
		addCommand.setStartMins("00");
		addCommand.setEndHours("15");
		addCommand.setEndMins("00");
		a.addToTaskList(addCommand);
		
		Task taskToUpdate = tempTaskList.get(0);
		taskToUpdate.setTaskID("1");
		
		//original location
		assert(taskToUpdate.getLocation().equals("Jurong East"));
		

		Command command = new Command();
		command.setKeyword("edit");
		command.setTaskID("1");
		command.setLocation("home");
		command.setTargetedTasks(targetedTasks);
		
		update.editContent(command);
		//Show that the location has changed.
		assert(taskToUpdate.getLocation().equals("home"));
		
		//Show original priority
		assert(taskToUpdate.getPriority().equals("high"));
		command = new Command();
		command.setKeyword("edit");
		command.setTaskID("1");
		command.setPriority("low");
		command.setTargetedTasks(targetedTasks);
		update.editContent(command);
		
		//Show that the priority has changed.
		assert(taskToUpdate.getPriority().equals("low"));

		//Show original details
		assert(taskToUpdate.getDetails().equals("lunch"));
		command = new Command();
		command.setKeyword("edit");
		command.setTaskID("1");
		command.setDetails("lunch with Joey");
		command.setTargetedTasks(targetedTasks);
		update.editContent(command);
		
		//Show that the details have changed.
		assert(taskToUpdate.getDetails().equals("lunch with Joey"));
		
		//Show original start day
		assert(taskToUpdate.getStartDay().equals("12"));
		command = new Command();
		command.setKeyword("edit");
		command.setTaskID("1");
		command.setStartDay("13");
		command.setTargetedTasks(targetedTasks);
		update.editContent(command);
		
		//Show that the start day have changed.
		assert(taskToUpdate.getStartDay().equals("13"));

	}
}
