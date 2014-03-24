import java.util.ArrayList;


public class Delete {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private ArrayList<Task> taskList;

	//constructor
	public Delete(ArrayList<Task> taskList){
		this.taskList = taskList;	
	}


	/**
	 * 
	 * delete: Go through taskList and remove task with matching taskID
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 * 
	 */

	public void delete(Command c) {
		//if (isPositiveInteger()) {
		assert(isPositiveInteger(c));
		for(String taskID : c.getTargetedTasks()){
			int taskIdNumber = retrieveTaskIdNumber(taskID);
			boolean isFound = false;

			for (int i = 0; i < taskList.size(); i++) {
				if (isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
					System.out.println("Deleted: "+ taskList.get(i).getDetails());
					taskList.remove(taskList.get(i));
					isFound = true;
				}
			}
			if (!isFound) {
				System.out.println(TASKID_NOT_FOUND_MESSAGE);
			}
		}
		/*} else {
			// User input was "delete (String)" or "delete (negative #)"
			System.out.println(NOT_INTEGER_MESSAGE);
		}*/
	}

	/**
	 * 
	 * isPositiveInteger: Checks if the delete/update/edit parameter is a valid taskID
	 * (positive integer)
	 * 
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

