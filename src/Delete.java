import java.util.ArrayList;

public class Delete {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private static final String DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL = "Deleted all related tasks!";
	private static final String DELETE_CATEGORYPRIORITYLOCATION_UNSUCCESSFUL = "No tasks were found with that information!";
	private ArrayList<Task> taskList;

	public Delete() {
		this.taskList = ExeCom.getTaskListInstance();
	}

	//@author A0085107J
	/**
	 * 
	 * delete: Go through taskList and remove task with matching taskID
	 * 
	 * @param command
	 * @return string
	 * 
	 */

	public String delete(Command c) {
		String feedback = "";
		for (String target : c.getTargetedTasks()) {
			if (isInteger(target)) {
				feedback += deleteSpecifiedTask(target);
			} else { 
				// element is a string containing location/priority/category
				if (deleteSpecifiedLocationPriorityCategory(target)) {
					feedback = DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL;
				} else {
					feedback = DELETE_CATEGORYPRIORITYLOCATION_UNSUCCESSFUL;
				}
			}
		}
		return feedback + "\n";
	}

	//@author A0085107J
	/**
	 * 
	 * deleteSpecifiedLocationPriorityCategory: Determine target string belongs
	 * to location, priority or category and delete all related tasks from
	 * taskList
	 * 
	 * @param String
	 * @return void
	 * 
	 */
	private boolean deleteSpecifiedLocationPriorityCategory(String target) {
		target = target.toLowerCase();
		boolean isDeleted = false;
		switch (target) {
		// priority
		case "low":
		case "medium":
		case "high":
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getPriority().equals(target)) {
					taskList.remove(i);
					isDeleted = true;
				}
			}
			break;

			// category
		case "family":
		case "work":
		case "friends":
		case "personal":
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getCategory().equals(target)) {
					taskList.remove(i);
					isDeleted = true;
				}
			}
			break;

			// location
		default:
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getLocation().equals(target)) {
					taskList.remove(i);
					isDeleted = true;
				}
			}
			break;
		}
		return isDeleted;
	}

	//@author A0085107J
	/**
	 * 
	 * deleteSpecifiedTask: Deletes one Task from TaskList
	 * 
	 * @author A0085107J
	 * @param String
	 * @return void
	 * 
	 */
	private String deleteSpecifiedTask(String target) {
		ExeCom ec = ExeCom.getInstance();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;

		// loop thru whole taskList to find for the user specified task
		String output = "Succesfully Deleted: \n";
		for (int i = 0; i < taskList.size(); i++) {
			if (ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
				output += taskList.get(i).getDetails();
				taskList.remove(taskList.get(i));
				isFound = true;
			}
		}
		if (!isFound) {
			output = TASKID_NOT_FOUND_MESSAGE;
		}
		return output + "\n";
	}

	//@author A0085107J
	/**
	 * isInteger: Checks whether the string in TargetedTask[] is an integer or not
	 * 
	 * @param String
	 * @return boolean
	 * 
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * isPositiveInteger: Checks if the delete/update/edit/completed parameter
	 * is a valid taskID (positive integer) CURRENTLY NOT IN USE AS PARAMETER
	 * CAN BE LOCATION/PRIORITY/CATEGORY
	 * 
	 * @author A0118590A
	 * @param void
	 * @return boolean
	 * 
	 */
	public static boolean isPositiveInteger(Command c) {
		try {
			boolean flag = false;
			for (int i = 0; i < c.getTargetedTasks().size(); i++) {
				if (Integer.parseInt(c.getTargetedTasks().get(i)) > 0) {
					flag = true;
				}
			}
			return flag;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
