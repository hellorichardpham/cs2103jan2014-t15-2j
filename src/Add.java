import java.util.ArrayList;


public class Add {
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.";
	private ArrayList<Task> taskList;

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
			
			taskToAdd.setTaskID(Integer.toString(ExeCom.getTaskListInstance().size() + 1));
			taskList.add(taskToAdd);
			
			System.out.println(ADD_SUCCESSFUL_MESSAGE);
		}
	}
	

	/**
	 * handleConflict: print conflicted tasks and ask if user wants to add anyway
	 * 
	 * @author Wei Zhou
	 * @param Command, ArrayList<Integer>
	 * @param void
	 * @throws Exception
	 */
	public void handleConflict(Command command, ArrayList<Integer> conflicts){
		printConflictedTasks(conflicts);
		askIfUserWantToAdd(command);
	}

	/**
	 * askIfUserWantToAdd: if user want to add despite conflict
	 * 
	 * @author Wei Zhou
	 * @param command
	 * @return void
	 */
	private void askIfUserWantToAdd(Command command){
		UI ui = new UI();
		System.out.println("Add Task anyway? Enter(Y/N) :");	
		String input = ui.askForUserResponse();
		if (isWantToAdd(input)){
			processAdd(command);
		}
	}

	/**
	 * processAdd: do necessary backups for undo and redo and add to taskList
	 * 
	 * @author Wei Zhou
	 * @param command
	 * @return void
	 */
	public void processAdd(Command command){
		ExeCom ec = new ExeCom();
		ec.saveToPrevTaskList();
		try {
			addToTaskList(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ec.saveToRedoTaskList();
	}

	/**
	 * isWantToAdd: check if user replied yes to adding task
	 * 
	 * @author Wei Zhou
	 * @param String
	 * @return boolean
	 */
	private boolean isWantToAdd(String input) {
		if(input=="yes" || input=="y" || input=="yeah" || input=="ya"){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * printConflictedTask: print all tasks that conflicts with current task
	 * 
	 * @author Wei Zhou
	 * @param ArrayList<Integer>
	 * @return void
	 */
	private void printConflictedTasks(ArrayList<Integer> conflicts) {
		System.out.println("There is a conflict with these tasks: ");
		for(int i=0 ; i<conflicts.size(); i++) {
			System.out.print(conflicts.get(i)+": ");
			System.out.println(taskList.get(conflicts.get(i)).displayTask());
		}
	}

}//end class
