import java.util.ArrayList;

public class Completed {
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";;
	private ArrayList<Task> taskList;
	
	//constructor
	public Completed(){
		taskList = ExeCom.getTaskListInstance();
	}
	
	public void markCompleted(Command c) {
		for(String target : c.getTargetedTasks()){
			markSpecificTaskCompleted(target);
		}
	}

	private void markSpecificTaskCompleted(String target) {
		ExeCom ec = new ExeCom();
		int taskIdNumber = ec.retrieveTaskIdNumber(target);
		boolean isFound = false;
		//loop thru whole taskList to find for the user specified task
		for (int i = 0; i < taskList.size(); i++) {
			if (ec.isTaskIDMatch(taskList.get(i).getTaskID(), taskIdNumber)) {
				System.out.println("This task is marked as completed: " + taskList.get(i).getDetails());
				Task task = taskList.get(i);
				task.setCompleted("true");
				isFound = true;
			}
		}
		if (!isFound) {
			System.out.println(TASKID_NOT_FOUND_MESSAGE);
		}
		
	}
}
