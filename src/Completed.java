import java.util.ArrayList;

/**
 * completed class: contains all methods related to completed feature
 * @author A0085107J
 * 
 */
public class Completed {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";;
	private ArrayList<Task> taskList;

	//constructor
	public Completed(){
		taskList = ExeCom.getTaskListInstance();
	}

	/**
	 * markCompleted: marks a set of tasks as completed
	 * 
	 * @author A0085107J
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

	/**
	 * markSpecificTaskCompleted: marks a task as completed
	 * 
	 * @author A0085107J
	 * @param Command
	 * @return String
	 * 
	 */
	private String markSpecificTaskCompleted(String target) {
		ExeCom ec = new ExeCom();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;
		String output = "";
		//loop thru whole taskList to find for the user specified task
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

	/**
	 * isTaskFound: checks if a particular task matches specificied taskIDs
	 * @author A0085107J
	 * @param ec
	 * @param taskIdNumber
	 * @param i
	 * @return boolean
	 */
	private boolean isTaskFound(ExeCom ec, int taskIdNumber, int i) {
		return ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber);
	}
}
