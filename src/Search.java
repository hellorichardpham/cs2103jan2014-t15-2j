import java.util.ArrayList;


public class Search {
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.\n";
	private static final String EMPTY_STRING = "";
	private static ArrayList<Task> searchResults;
	private ArrayList<Task> taskList;
	
	//constructor
	public Search(ArrayList<Task> taskList){
		this.taskList = taskList;	
	}

	/**
	 * 
	 * printSearch: Display task details of all tasks in Search Results
	 * 
	 * @author A0118590A, A0085107J
	 * @param void
	 * @return void
	 */

	public static String printSearch() {
            String details = "";
		if (!searchResults.isEmpty()) {
			for (Task task : searchResults) {
				details = details + "Task ID: " + task.getTaskID() + "\n";
				String taskToPrint = task.displayTask();
				details = details + taskToPrint + "\n";
			}
		} else {
			details = TASK_NOT_FOUND_MESSAGE;
		}
                return details;
	}

	/**
	 * 
	 * search: Cycle through entire taskList looking for User-specified keyword.
	 * Add all tasks that contain keyword into the searchResults ArrayList.
	 * 
	 * @author A0118590A, A0085107J
	 * @param void
	 * @return void
	 */

	public String searchTaskList(Command c) {
            boolean isFound = false;
			resetSearchResults();
			String searchKeyword = c.getDetails();

			for (Task task : taskList) {
				if (hasMatchingKeyword(task, searchKeyword)) {
					searchResults.add(task);
					isFound = true;
				}
			}
			if (isFound) {
				 return printSearch();
			} else {
				return TASK_NOT_FOUND_MESSAGE;
			}
	}

	/**
	 * 
	 * hasMatchingKeyword: checks if a task contains the keyword that a user
	 * specified in the search command
	 * 
	 * @author A0118590A
	 * @param String
	 *            [], String
	 * @return boolean
	 */

	public static boolean hasMatchingKeyword(Task task, String searchKeyword) {
		if(task.getDetails().contains(searchKeyword) || task.getPriority().contains(searchKeyword) ||
				task.getCategory().contains(searchKeyword))
		
		return true;
		else return false;
	}
	
	/**
	 * 
	 * resetSearchResults: Reinitializes searchResults so it will be empty when
	 * we search.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 */

	public static void resetSearchResults() {
		searchResults = new ArrayList<Task>();
	}
	/*
	 * @author A0118590A
	 */
	public static ArrayList<Task> getSearchResults() {
		return searchResults;
	}
}
