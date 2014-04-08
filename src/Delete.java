import java.util.ArrayList;

public class Delete {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private static final String DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL = "Deleted all related tasks!";
	private static final String DELETE_CATEGORYPRIORITYLOCATION_UNSUCCESSFUL = "No tasks were found with that information!";
	private ArrayList<Task> taskList;

	// constructor
	public Delete() {
		this.taskList = ExeCom.getTaskListInstance();
	}

	/**
	 * 
	 * delete: Go through taskList and remove task with matching taskID
	 * 
	 * @author Richard, Ying Yun
	 * @param void
	 * @return void
	 * 
	 */

	public String delete(Command c) {
		String feedback = "Succesfully Deleted: \n";
		for (String target : c.getTargetedTasks()) {
			if (isInteger(target)) {
				feedback += deleteSpecifiedTask(target);
			} else { // element is a string containing
						// location/priority/category
				if (deleteSpecifiedLocationPriorityCategory(target)) {
					feedback = DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL;
				} else {
					feedback = DELETE_CATEGORYPRIORITYLOCATION_UNSUCCESSFUL;
				}
			}
		}// end delete
		return feedback + "\n";
	}

	/**
	 * 
	 * deleteSpecifiedLocationPriorityCategory: Determine target string belongs
	 * to location, priority or category and delete all related tasks from
	 * taskList
	 * 
	 * @author Ying Yun
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

	/**
	 * 
	 * deleteSpecifiedTask: Deletes one Task from TaskList
	 * 
	 * @author Ying Yun
	 * @param String
	 * @return void
	 * 
	 */
	private String deleteSpecifiedTask(String target) {
		ExeCom ec = ExeCom.getInstance();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;

		// loop thru whole taskList to find for the user specified task
		String output = "";
		for (int i = 0; i < taskList.size(); i++) {
			if (ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
				output += taskList.get(i).getDetails();
				ec.setUndoableTrue();
				ec.setRedoableFalse();
				taskList.remove(taskList.get(i));
				isFound = true;
			}
		}
		if (!isFound) {
			output = TASKID_NOT_FOUND_MESSAGE;
		}
		return output + "\n";
	}

	/**
	 * 
	 * isInteger: Checks whether the string in TargetedTask[] is an integer or
	 * not
	 * 
	 * @author Ying Yun
	 * @param String
	 * @return boolean
	 * 
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only gets here if it successfully parsed the string, implying that
		// particular string is an integer
		return true;
	}

	/**
	 * 
	 * isPositiveInteger: Checks if the delete/update/edit/completed parameter
	 * is a valid taskID (positive integer) CURRENTLY NOT IN USE AS PARAMETER
	 * CAN BE LOCATION/PRIORITY/CATEGORY
	 * 
	 * @author Richard, yingyun
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
