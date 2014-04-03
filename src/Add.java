import java.util.ArrayList;


public class Add {
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
		ExeCom ec = new ExeCom();
		{
			//System.out.println("adding " + c.getDetails());
			Task taskToAdd = new Task(c);

			taskToAdd.setTaskID(Integer.toString(ExeCom.getTaskListInstance().size() + 1));
			ec.saveToPrevTaskList();
			taskList.add(taskToAdd);
		}
	}


	/**
	 * handleConflict: print conflicted tasks and ask if user wants to add anyway
	 * 
	 * @author Wei Zhou
	 * @param Command, ArrayList<Integer>
	 * @param void
	 * @return 
	 * @throws Exception
	 */
	public void handleConflict(Command command, ArrayList<Integer> conflicts){
		printConflictedTasks(conflicts);
		//String input = askIfUserWantToAdd(command);
		//return input;
	}

	/**
	 * askIfUserWantToAdd: if user want to add despite conflict
	 * 
	 * @author Wei Zhou
	 * @param command
	 * @return void
	 */
//	private String askIfUserWantToAdd(Command command){
//		UI ui = new UI();
//		System.out.println("Add Task anyway? Enter(Y/N) :");	
//		String input = ui.askForUserResponse();
//		return input;
//	}

	/**
	 * isWantToAdd: check if user replied yes to adding task
	 * 
	 * @author Wei Zhou
	 * @param String
	 * @return boolean
	 */
//	boolean isWantToAdd(String input) {
//		if(input=="yes" || input=="y" || input=="yeah" || input=="ya"){
//			return true;
//		}else{
//			return false;
//		}
//	}

	/**
	 * printConflictedTask: print all tasks that conflicts with current task
	 * 
	 * @author Wei Zhou
	 * @param ArrayList<Integer>
	 * @return void
	 */
	private void printConflictedTasks(ArrayList<Integer> conflicts) {
		System.out.println("There is a conflict with these tasks: ");
		//ExeCom.setFeedback("There is a conflict with these tasks: ");
		for(int i=0 ; i<conflicts.size(); i++) {
			System.out.print((conflicts.get(i)+1) + ": ");
			//ExeCom.setFeedback((conflicts.get(i)+1) + ": ");
			System.out.println(taskList.get(conflicts.get(i)).displayTask());
			//ExeCom.setFeedback(taskList.get(conflicts.get(i)).displayTask());
		}
	}

}//end class
