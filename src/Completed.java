import java.util.ArrayList;

public class Completed {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";;
	private ArrayList<Task> taskList;
	
	//constructor
	public Completed(){
		taskList = ExeCom.getTaskListInstance();
	}
	
	public String markCompleted(Command c) {
                String feedback = "";
		for(String target : c.getTargetedTasks()){
			feedback = feedback + markSpecificTaskCompleted(target);
		}
                return feedback;
	}

	private String markSpecificTaskCompleted(String target) {
		ExeCom ec = new ExeCom();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;
                String output = "";
		//loop thru whole taskList to find for the user specified task
		for (int i = 0; i < taskList.size(); i++) {
			if (ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
				output = output + "This task is marked as completed: " + taskList.get(i).getDetails() + "\n";
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
}
