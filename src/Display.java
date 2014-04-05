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
                return dispOut;
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
                return dispOut;
	}
	
	public String displayMonth() {
		String month = command.getEndMonth();
		switch(month) {
		case "01":
		return sorted.printMonthList(0);
		case "02":
			return sorted.printMonthList(1);
		case "03":
			return sorted.printMonthList(2);
		case "04":
			return sorted.printMonthList(3);
		case "05":
			return sorted.printMonthList(4);
		case "06":
			return sorted.printMonthList(5);
		case "07":
			return sorted.printMonthList(6);
		case "08":
			return sorted.printMonthList(7);
		case "09":
			return sorted.printMonthList(8);
		case "10":
			return sorted.printMonthList(9);
		case "11":
			return sorted.printMonthList(10);
		case "12":
			return sorted.printMonthList(11);
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

	/**
	 * replaceNull: replaces "null" String with empty String
	 * 
	 * @author Richard
	 * @param String
	 * @return String
	 */
	public String replaceNull(String print) {
		print = print.replace("null", EMPTY_STRING);
		return print;
	}
}//end class
