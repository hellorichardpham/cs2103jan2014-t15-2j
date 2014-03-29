import java.util.ArrayList;


public class Delete {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private static final String DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL = "Deleted all related tasks!";
	private ArrayList<Task> taskList;

	//constructor
	public Delete(ArrayList<Task> taskList){
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

	public void delete(Command c) {

		for(String target : c.getTargetedTasks()){
			if(isInteger(target)){
				deleteSpecifiedTask(target);		
			} else { //element is a string containing location/priority/category
				deleteSpecifiedLocationPriorityCategory(target);
				System.out.println(DELETE_CATEGORYPRIORITYLOCATION_SUCCESSFUL);
			}
		}//end delete
	}

	/**
	 * 
	 * deleteSpecifiedLocationPriorityCategory: Determine target string belongs to location, priority or category
	 * and delete all related tasks from taskList
	 * 
	 * @author Ying Yun
	 * @param String
	 * @return void
	 * 
	 */
	private void deleteSpecifiedLocationPriorityCategory(String target) {
		target = target.toLowerCase();
		switch(target){
		//priority
		case "low":
		case "medium":
		case "high":
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getPriority().equals(target))
					taskList.remove(i);
			}
			break;
			
		//category
		case "family":
		case "work":
		case "friends":
		case "personal":
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getCategory().equals(target))
					taskList.remove(i);
			}
			break;
			
		//location
		default:
			for (int i = 0; i < taskList.size(); i++) {
				Task currentTask = taskList.get(i);
				if (currentTask.getLocation().equals(target))
					taskList.remove(i);
			}
			break;
		}
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
	private void deleteSpecifiedTask(String target) {
		int taskIdNumber = retrieveTaskIdNumber(target);
		boolean isFound = false;
		//loop thru whole taskList to find for the user specified task
		for (int i = 0; i < taskList.size(); i++) {
			if (isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
				System.out.println("Succesfully Deleted: "+ taskList.get(i).getDetails());
				taskList.remove(taskList.get(i));
									
				isFound = true;
			}
		}
		if (!isFound) {
			System.out.println(TASKID_NOT_FOUND_MESSAGE);
		}
	}


	/**
	 * 
	 * isInteger: Checks whether the string in TargetTask[] is an integer or not
	 * 
	 * @author Ying Yun
	 * @param String
	 * @return boolean
	 * 
	 */
	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// only gets here if it successfully parsed the string, implying that particular string is an integer
		return true;
	}


	/**
	 * 
	 * isPositiveInteger: Checks if the delete/update/edit parameter is a valid taskID
	 * (positive integer)
	 * CURRENTLY NOT IN USE AS PARAMETER CAN BE LOCATION/PRIORITY/CATEGORY
	 * @author Richard, yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	public static boolean isPositiveInteger(Command c) {
		try {
			boolean flag = false;
			for(int i=0; i<c.getTargetedTasks().size(); i++){
				if (Integer.parseInt(c.getTargetedTasks().get(i)) > 0) {
					flag= true;
				}
			}
			return flag;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * isTaskIDMatch: Checks if a task's taskID is equal to the userSpecified
	 * taskIdNumber that he's searching for.
	 * 
	 * @author Richard, yingyun
	 * @param String, int
	 * @return boolean
	 * 
	 */

	public static boolean isTaskIDMatch(String specifiedTaskID, int taskIdNumber) {
		return Integer.parseInt(specifiedTaskID) == taskIdNumber;

	}

	/**
	 * 
	 * retrieveTaskIdNumber: retrieves user-specified taskID. We know it's valid
	 * because it passed the isPositiveInteger() test
	 * 
	 * @author Richard, yingyun
	 * @param String
	 * @return int
	 * 
	 */

	public static int retrieveTaskIdNumber(String taskID) {
		return Integer.parseInt(taskID);
	}
}

