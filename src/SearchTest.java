//@author A0118590A

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SearchTest {
	ProcessCommand pc = new ProcessCommand();
	ExeCom ec = ExeCom.getInstance();
	Search search = new Search(ec.getTaskListInstance());
	Add a = new Add(ec.getTaskListInstance());
	ArrayList<Task> testTaskList = ec.getTaskListInstance();

	@Test
	public void test() throws Exception {

		initializeTestEnvironment(a);
		
		//Test for details
		Command command = new Command();
		command.setKeyword("search");
		command.setDetails("lunch");
		assert (ec.isValidSearchCommand(command));
		search.searchTaskList(command);
		assert (search.getSearchResults().size() == 1);
		assert (search.getSearchResults().get(0).displayToStorage()
				.equals("lunch 12 04 2014 14 04 2014 13 00 15 00 //location Jurong East //category school //priority high //completed false "));
		
		//Test for priority
	    search = new Search(ec.getTaskListInstance());
		command = new Command();
		command.setKeyword("search");
		command.setDetails("low");
	    search.searchTaskList(command);
		assert (search.getSearchResults().size() == 1);
		assert (search.getSearchResults().get(0).displayToStorage()
				.equals("dinner 13 05 2014 14 05 2014 13 30 16 00 //location UTown //category personal //priority low //completed false "));

		//Test search for category
		search = new Search(ec.getTaskListInstance());
		command = new Command();
		command.setKeyword("search");
		command.setDetails("finals");
	    search.searchTaskList(command);
		assert (search.getSearchResults().size() == 1);
		assert (search.getSearchResults().get(0).displayToStorage()
				.equals("Study for finals 19 04 2014 26 04 2014 13 30 16 00 //location NUS //category finals //priority high //completed false "));
	}
	
	public static void initializeTestEnvironment(Add a) throws Exception {
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

		addCommand = new Command();
		addCommand.setKeyword("add");
		addCommand.setDetails("dinner");
		addCommand.setStartDay("13");
		addCommand.setStartMonth("05");
		addCommand.setStartYear("2014");
		addCommand.setEndDay("14");
		addCommand.setEndMonth("05");
		addCommand.setEndYear("2014");
		addCommand.setPriority("low");
		addCommand.setCategory("personal");
		addCommand.setLocation("UTown");
		addCommand.setStartHours("13");
		addCommand.setStartMins("30");
		addCommand.setEndHours("16");
		addCommand.setEndMins("00");
		a.addToTaskList(addCommand);
		
		addCommand = new Command();
		addCommand.setKeyword("add");
		addCommand.setDetails("Study for finals");
		addCommand.setStartDay("19");
		addCommand.setStartMonth("04");
		addCommand.setStartYear("2014");
		addCommand.setEndDay("26");
		addCommand.setEndMonth("04");
		addCommand.setEndYear("2014");
		addCommand.setPriority("high");
		addCommand.setCategory("finals");
		addCommand.setLocation("NUS");
		addCommand.setStartHours("13");
		addCommand.setStartMins("30");
		addCommand.setEndHours("16");
		addCommand.setEndMins("00");
		a.addToTaskList(addCommand);
		
		addCommand = new Command();
		addCommand.setKeyword("add");
		addCommand.setDetails("fix computer");
		addCommand.setStartDay("30");
		addCommand.setStartMonth("04");
		addCommand.setStartYear("2014");
		addCommand.setEndDay("31");
		addCommand.setEndMonth("04");
		addCommand.setEndYear("2014");
		addCommand.setPriority("medium");
		addCommand.setCategory("school");
		addCommand.setLocation("Dhoby Ghaut");
		addCommand.setStartHours("09");
		addCommand.setStartMins("00");
		addCommand.setEndHours("11");
		addCommand.setEndMins("00");
		a.addToTaskList(addCommand);
	}
}
