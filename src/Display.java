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
	 * display: display all task found in the taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public void display() {
		if (!taskList.isEmpty()) {
			System.out.println("~~~~~ Listing of all tasks ~~~~~");
			for (Task task : taskList) {
				String print = task.display();
				print = replaceNull(print);
				System.out.println((taskList.indexOf(task)+1) + ": " + print);
			}
		} else if (taskList.isEmpty()) {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		} 
	}
	
	public String replaceNull(String print) {
		print = print.replace("null ", EMPTY_STRING);
		print = print.replace("null", EMPTY_STRING);
		return print;
	}
}//end class
