import java.util.ArrayList;

public class Display extends Task {

	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.\n";
	private static final CharSequence EMPTY_STRING = "";
	private static ArrayList<Task>[][] monthList;
	private static Command command = null;
	private ArrayList<Task> taskList;
	SortDate sorted;

	//constructor
	public Display(ArrayList<Task> taskList){
		this.taskList = taskList;
	}
	
	public Display(ArrayList<Task> taskList, Command command, ArrayList<Task>[][] monthList){
		this.taskList = taskList;
		this.command = command;
		this.monthList = monthList;
		sorted = new SortDate(taskList);
		taskList = sorted.sort();
	}
	
	/**
	 * 
	 * display: display all tasks found in the taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public String displayTaskList() {
                String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + printListingHeader();
			for (Task task : taskList) {
				String print = task.displayTask();
				dispOut = dispOut + printTaskWithIndex(task, print);
			}
		}
		else if (taskList.isEmpty()) {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		} 
                return dispOut + "\n";
	}

	/**
	 * displayCompleted: prints out all completed tasks in taskList
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return void
	 */
	public String displayCompleted() {
                String dispOut = "";
		if (!taskList.isEmpty()){
			dispOut = dispOut + "The following tasks are completed: \n";
			for (Task task : taskList){
				if (task.isCompleted()){
					
					String info = task.displayTask();
					dispOut = dispOut + printTaskWithIndex(task, info);
				}
			}
		}
		else{
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
                return dispOut + "\n";
	}
	
	public String displayStartMonth() {
		String month = command.getEndMonth();
		switch(month) {
		case "01":
		return sorted.printStartMonthList(0);
		case "02":
			return sorted.printStartMonthList(1);
		case "03":
			return sorted.printStartMonthList(2);
		case "04":
			return sorted.printStartMonthList(3);
		case "05":
			return sorted.printStartMonthList(4);
		case "06":
			return sorted.printStartMonthList(5);
		case "07":
			return sorted.printStartMonthList(6);
		case "08":
			return sorted.printStartMonthList(7);
		case "09":
			return sorted.printStartMonthList(8);
		case "10":
			return sorted.printStartMonthList(9);
		case "11":
			return sorted.printStartMonthList(10);
		case "12":
			return sorted.printStartMonthList(11);
		}
		return "invalid month";
	}
	
	public String displayEndMonth() {
		String month = command.getEndMonth();
		switch(month) {
		case "01":
		return sorted.printEndMonthList(0);
		case "02":
			return sorted.printEndMonthList(1);
		case "03":
			return sorted.printEndMonthList(2);
		case "04":
			return sorted.printEndMonthList(3);
		case "05":
			return sorted.printEndMonthList(4);
		case "06":
			return sorted.printEndMonthList(5);
		case "07":
			return sorted.printEndMonthList(6);
		case "08":
			return sorted.printEndMonthList(7);
		case "09":
			return sorted.printEndMonthList(8);
		case "10":
			return sorted.printEndMonthList(9);
		case "11":
			return sorted.printEndMonthList(10);
		case "12":
			return sorted.printEndMonthList(11);
		}
		return "invalid month";
	}
	
	/**
	 * 
	 * printListingHeader: print header for listing
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return void
	 */
	private String printListingHeader() {
		return "~~~~~ Listing of all tasks ~~~~~\n";
	}

	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author Ying Yun
	 * @param Task, String
	 * @return void
	 */
	private String printTaskWithIndex(Task task, String print) {
		return (taskList.indexOf(task)+1) + ": " + print + "\n";
	}
}//end class
