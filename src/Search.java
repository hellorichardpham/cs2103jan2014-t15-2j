import java.util.ArrayList;


public class Search {
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.";
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
	 * @author Richard
	 * @param void
	 * @return void
	 */

	public static void printSearch() {
		if (!searchResults.isEmpty()) {
			for (Task task : searchResults) {
				System.out.println("Task ID: " + task.getTaskID());
				String taskToPrint = task.displayTask();
				System.out.println(taskToPrint);
			}
		} else {
			System.out.println(TASK_NOT_FOUND_MESSAGE);
		}
	}

	/**
	 * 
	 * search: Cycle through entire taskList looking for User-specified keyword.
	 * Add all tasks that contain keyword into the searchResults ArrayList.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */

	public void searchTaskList(Command c) {
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
				printSearch();
			} else {
				System.out.println(TASK_NOT_FOUND_MESSAGE);
			}
	}
	
	public void TESTsearchTaskList(Command c) {
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
			// Commented out for test purposes printSearch();
		} else {
			System.out.println(TASK_NOT_FOUND_MESSAGE);
		}
}

	/**
	 * 
	 * hasMatchingKeyword: checks if a task contains the keyword that a user
	 * specified in the search command
	 * 
	 * @author Richard
	 * @param String
	 *            [], String
	 * @return boolean
	 */

	public static boolean hasMatchingKeyword(Task task, String searchKeyword) {
		return task.getDetails().contains(searchKeyword);
	}
	
	/**
	 * 
	 * resetSearchResults: Reinitializes searchResults so it will be empty when
	 * we search.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */

	public static void resetSearchResults() {
		searchResults = new ArrayList<Task>();
	}
	
	public static ArrayList<Task> getSearchResults() {
		return searchResults;
	}
	
	public static String replaceNull(String print) {
		print = print.replace("null ", EMPTY_STRING);
		print = print.replace("null", EMPTY_STRING);
		return print;
	}
}
