import java.util.ArrayList;


public class Add {
	private ArrayList<Task> taskList;

	public Add(ArrayList<Task> taskList){
		this.taskList = taskList;
	}

	/**
	 * 
	 * addToTaskList: Add tasks to arrayList and set taskID.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 */
	public String addToTaskList(Command c) throws Exception {
		{
			ExeCom ec = ExeCom.getInstance();
			Task taskToAdd = new Task(c);
			taskToAdd.setTaskID(Integer.toString(ExeCom.getTaskListInstance().size() + 1));
		taskList.add(taskToAdd);
			return taskToAdd.getTaskID();
	}

	//@author A0083093E
	/**
	 * handleConflict: print conflicted tasks and ask if user wants to add anyway
	 * @param Command, ArrayList<Integer>
	 * @return void
	 * @throws Exception
	 */
	public void handleConflict(Command command, ArrayList<Integer> conflicts){
		printConflictedTasks(conflicts);
	}

	//@author A0083093E-unused
	//not used as we switched to a GUI based design
	/**
	 * askIfUserWantToAdd: if user want to add despite conflict
	 * 
	 * @author Wei Zhou
	 * @param command
	 * @return void
	 */
	private String askIfUserWantToAdd(Command command){
		UI ui = new UI();
		System.out.println("Add Task anyway? Enter(Y/N) :");	
		String input = ui.askForUserResponse();
		return input;
	}


	//@author A0083093E-unused
	//not used as we switched to a GUI based design
	/**
	 * isWantToAdd: check if user replied yes to adding task
	 * 
	 * @param String
	 * @return boolean
	 */
	boolean isWantToAdd(String input) {
		if(input=="yes" || input=="y" || input=="yeah" || input=="ya"){
			return true;
		}else{
			return false;
		}
	}

	//@author A0083093E-unused
	//not used as we switched to a GUI based design
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
			System.out.print((conflicts.get(i)+1) + ": ");
			System.out.println(taskList.get(conflicts.get(i)).displayTask());
		}
	}
}