import java.util.ArrayList;


public class Update {
	private ArrayList<Task> taskList;

	//constructor
	public Update(ArrayList<Task> taskList){
		this.taskList = taskList;	
	}

	/**
	 * 
	 * editContent: edit content of specific task using the taskID based on
	 * user's input
	 * 
	 * @author yingyun, weizhou
	 * @param void
	 * @return void
	 */
	public void editContent(Command c) {
		System.out.println(c.getTaskID() + "");
		int id = Integer.parseInt(c.getTaskID());	//user specified task ID
		
		//loop through taskList to find matching task object
		for (int i = 0; i< taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			int currentTaskID = Integer.parseInt(currentTask.getTaskID());
			if (currentTaskID==id) {
				
				//Update specified task object using either information from command/current task object
				currentTask.setDetails(merge(c.getDetails(),currentTask.getDetails()));
				
				currentTask.setStartDay(merge(c.getStartDay(),currentTask.getStartDay()));
				currentTask.setStartMonth(merge(c.getStartMonth(),currentTask.getStartMonth()));
				currentTask.setStartYear(merge(c.getStartYear(),currentTask.getStartYear()));
				
				currentTask.setEndDay(merge(c.getEndDay(),currentTask.getEndDay()));
				currentTask.setEndMonth(merge(c.getEndMonth(),currentTask.getEndMonth()));
				currentTask.setEndYear(merge(c.getEndYear(),currentTask.getEndYear()));
				
				currentTask.setStartHours(merge(c.getStartHours(),currentTask.getStartHours()));
				currentTask.setStartMins(merge(c.getStartMins(),currentTask.getStartMins()));
				
				currentTask.setEndHours(merge(c.getEndHours(),currentTask.getEndHours()));
				currentTask.setEndMins(merge(c.getEndMins(),currentTask.getEndMins()));
				
				currentTask.setLocation(merge(c.getLocation(),currentTask.getLocation()));
				currentTask.setCategory(merge(c.getCategory(),currentTask.getCategory()));
				
			}
		}
	}

	/**
	 * merge: returns selected information from either command object or task object.
	 * @author yingyun
	 * @param String, String
	 * @return String
	 */
	private static String merge(String fromCommand, String fromTask) {
		//user did not specify this attribute to be updated
		if (fromCommand == null){
			//use back the same information from task object
			return fromTask;
		} else {
			//update information from command object
			return fromCommand;
		}
	}
}
