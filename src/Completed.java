import java.util.ArrayList;

//@author A0085107J
/**
 * completed class: contains all methods related to completed feature
 */

public class Completed {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";;
	private ArrayList<Task> taskList;

	public Completed(){
		taskList = ExeCom.getTaskListInstance();
	}

	//@author A0085107J
	/**
	 * markCompleted: marks a set of tasks as completed
	 * 
	 * @param Command
	 * @return String
	 * 
	 */
	public String markCompleted(Command c) {
		String feedback = "";
		for(String target : c.getTargetedTasks()){
			feedback += markSpecificTaskCompleted(target);
		}
		return feedback;
	}

	//@author A0085107J
	/**
	 * markSpecificTaskCompleted: marks a task as completed
	 * 
	 * @param Command
	 * @return String
	 * 
	 */
	private String markSpecificTaskCompleted(String target) {
		ExeCom ec = new ExeCom();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;
		String output = "";
		//loop thru whole taskList to find for the user-specified task
		for (int i = 0; i < taskList.size(); i++) {
			if (isTaskFound(ec, taskIdNumber, i)) {
				output += "This task is marked as completed: " + taskList.get(i).getDetails() + "\n";
				Task task = taskList.get(i);
				task.setCompleted("true");
				isFound = true;
			}
		}
		if (!isFound) {
			output = TASKID_NOT_FOUND_MESSAGE;
		}
		return output;
	}

	//@author A0085107J
	/**
	 * isTaskFound: checks if a particular task matches specified taskID
	 * @param ExeCom, int, int
	 * @return boolean
	 */
	private boolean isTaskFound(ExeCom ec, int taskIdNumber, int i) {
		return ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber);
	}
}
