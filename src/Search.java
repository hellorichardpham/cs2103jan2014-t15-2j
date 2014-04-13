import java.util.ArrayList;

public class Search {
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.\n";
	private static final String EMPTY_STRING = "";
	private static ArrayList<Task> searchResults;
	private ArrayList<Task> taskList;

	public Search(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	// @author A0118590A
	/**
	 * 
	 * printSearch: Display task details of all tasks in Search Results
	 * 
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

	// @author A0118590A
	/**
	 * 
	 * search: Cycle through entire taskList looking for User-specified keyword.
	 * Add all tasks that contain keyword into the searchResults ArrayList.
	 * 
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

	// @author A0118590A
	/**
	 * 
	 * hasMatchingKeyword: checks if a task contains the keyword that a user
	 * specified in the search command
	 * 
	 * @param String
	 *            [], String
	 * @return boolean
	 */

	public static boolean hasMatchingKeyword(Task task, String searchKeyword) {
		if (task.getDetails().contains(searchKeyword)
				|| task.getPriority().contains(searchKeyword)
				|| task.getCategory().contains(searchKeyword)) {

			return true;
		} else {
			return false;
		}
	}

	//@author A0118590A
	/**
	 * 
	 * resetSearchResults: Reinitializes searchResults so it will be empty when
	 * we search.
	 * 
	 * @param void
	 * @return void
	 */

	public static void resetSearchResults() {
		searchResults = new ArrayList<Task>();
	}

	//@author A0118590A
	/**
	 * 
	 * getSearchResults: Returns searchResultes arrayList<Task>
	 * 
	 * @param void
	 * @return arrayList<Task>
	 */
	public static ArrayList<Task> getSearchResults() {
		return searchResults;
	}
}
