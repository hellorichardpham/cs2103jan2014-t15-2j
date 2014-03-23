import java.util.ArrayList;


public class Add {
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.";
	private static ArrayList<Task> taskList;

	//constructor
	public Add(ArrayList<Task> taskList){
		this.taskList = taskList;
		
	}
	
	/**
	 * 
	 * addToTaskList: Add tasks to arrayList and set taskID.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public void addToTaskList(Command c) throws Exception {
		
		{
			Task taskToAdd = new Task(c);
			
			taskToAdd.setTaskID(Integer.toString(taskList.size() + 1));
			taskList.add(taskToAdd);
			
			System.out.println(ADD_SUCCESSFUL_MESSAGE);
		}
	}
	

}//end class
