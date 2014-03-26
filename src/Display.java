import java.util.ArrayList;

public class Display {
	
private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
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
	public void display() {
		if (!taskList.isEmpty()) {
			printListingHeader();
			for (Task task : taskList) {
				String print = task.display();
				print = replaceNull(print);
				printTaskIndex(task, print);
			}
		} else if (taskList.isEmpty()) {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		} 
	}
	
	/**
	 * 
	 * printListingHeader: print header for listing
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return void
	 */
	private void printListingHeader() {
		System.out.println("~~~~~ Listing of all tasks ~~~~~");
	}
	
	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author Ying Yun
	 * @param Task, String
	 * @return void
	 */
	private void printTaskIndex(Task task, String print) {
		System.out.println((taskList.indexOf(task)+1) + ": " + print);
	}
	
	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author Richard
	 * @param String
	 * @return String
	 */
	public String replaceNull(String print) {
		print = print.replace("null ", EMPTY_STRING);
		print = print.replace("null", EMPTY_STRING);
		return print;
	}
}//end class
