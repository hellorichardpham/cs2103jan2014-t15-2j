import java.util.ArrayList;

public class Display {
	
private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
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
				print = print.replace("null ", "");
				print = print.replace("null", "");
				System.out.println((taskList.indexOf(task)+1) + ": " + print);
			}
		} else if (taskList.isEmpty()) {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		} 
	}
}//end class