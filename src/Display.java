import java.util.ArrayList;

public class Display extends Task {

	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.\n";
	private static final CharSequence EMPTY_STRING = "";
	private ArrayList<Task> taskList;

	//constructor
	public Display(ArrayList<Task> taskList){
		this.taskList = taskList;
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
