import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExeCom {
	static ArrayList<Task> taskList;
	private static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> searchResults;
	private static String[] info;
	private final static String ADD = "add";
	private final static String DISPLAY = "display";
	private final static String DELETE = "delete";
	private final static String SEARCH = "search";
	private final static String UNDO = "undo";
	private final static String EDIT = "edit";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "Cannot perform undo on consecutive actions";
	private final static String PROMPT_USER_DELETE_MESSAGE = "Select which task you would like to delete based on its Task ID Number. Type 0 to cancel.";
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.";
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.";
	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.";
	private final static String NOT_INTEGER_MESSAGE = "ERROR: This is not an integer.";

	ExeCom() {
		taskList = new ArrayList<Task>();
		prevTaskList = new ArrayList<Task>();
	}

	/**
	 * 
	 * executeCommand: determines which action to perform based on the
	 * userCommand then calls the appropriate method.
	 * 
	 * @author Richard
	 * @param userCommandInfo
	 * @return String
	 * 
	 */
	public static String executeCommand(String[] userCommandInfo)
			throws Exception {
		info = userCommandInfo;
		String command = info[0];
		Storage s = new Storage();
		switch (command) {
		case ADD:
			addToTaskList();
			s.saveStorage();
			return " ";
		case DISPLAY:
			display();
			return " ";
		case DELETE:
			delete();
			s.saveStorage();
			return " ";
		case SEARCH:
			search();
			s.saveStorage();
			return " ";
		case UNDO:
			undo();
			s.saveStorage();
			return " ";
		case EDIT:
			editContent();
			s.saveStorage();
			return " ";
		}
		return " ";
	}

	/**
	 * 
	 * display: display all task found in the taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	private static void display() {
		if (!taskList.isEmpty()) {
			System.out.println("~~~~~ Listing of all tasks ~~~~~");
			for (int i = 0; i < taskList.size(); i++) {
				String print = taskList.get(i).displayAll();
				print = print.replace("null ", "");
				print = print.replace("null", "");
				System.out.println(print);
			}
		} else {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		}
	}

	/**
	 * 
	 * addToTaskList: Add tasks to arraylist and set taskID.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void addToTaskList() {
		Task taskToAdd = new Task(info);
		saveToPrevTaskList();
		taskToAdd.setTaskID(Integer.toString(taskList.size() + 1));
		taskList.add(taskToAdd);
		System.out.println(ADD_SUCCESSFUL_MESSAGE);
	}

	/**
	 * 
	 * delete: Go through taskList and remove task with matching taskID
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 * 
	 */

	public static void delete() {
		if (isInteger(info[15])) {
			int taskIdNumber = retrieveTaskIdNumber(info[15]);
			boolean isFound = false;
			if (!isCancelNumber(taskIdNumber) && isValidTaskId(taskIdNumber)) {
				for (int i = 0; i < taskList.size(); i++) {
					if (isTaskIDMatch(taskList.get(i), taskIdNumber)) {
						saveToPrevTaskList();
						System.out.println("Deleted: " + taskList.get(i).getDetails());
						taskList.remove(taskList.get(i));
						isFound = true;
					}
				}
				if (!isFound) {
					System.out.println(TASKID_NOT_FOUND_MESSAGE);
				}
			} else if (isCancelNumber(taskIdNumber)
					&& isValidTaskId(taskIdNumber)) {
				// User has cancelled the delete command. Revert back to user
				// command prompt.
			} else {
				//User input was not a valid integer
				System.out.println(NOT_INTEGER_MESSAGE);
			}
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static boolean isValidTaskId(int taskIdNumber) {
		return taskIdNumber > 0;
	}

	public static int retrieveTaskIdNumber(String s) {
		return Integer.parseInt(s);
	}

	public static boolean isCancelNumber(int taskIdNumber) {
		return taskIdNumber == 0;
	}

	public static boolean isTaskIDMatch(Task task, int taskIdNumber) {
		return Integer.parseInt(task.getTaskID()) == taskIdNumber;
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
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i).displayAll());
			}
		} else {
			System.out.println(TASK_NOT_FOUND_MESSAGE);
		}
	}

	/**
	 * 
	 * search: Cycle through entire taskList looking for User-specified keyword.
	 * Add all tasks that contain keyword ino the searchResults ArrayList.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */

	public static void search() {
		if (isValidSearchCommand(info)) {
			boolean isFound = false;
			reinitializeSearchResults();
			String searchKeyword = info[1];
			for (int i = 0; i < taskList.size(); i++) {
				if (hasMatchingKeyword(taskList.get(i), searchKeyword)) {
					searchResults.add(taskList.get(i));
					isFound = true;
				}
			}
			if (isFound) {
				printSearch();
				// delete();
			} else {
				System.out.println(TASK_NOT_FOUND_MESSAGE);
			}
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	public static boolean isValidSearchCommand(String[] info) {
		return info[1] != null;
	}

	public static boolean hasMatchingKeyword(Task task, String searchKeyword) {
		return task.getDetails().contains(searchKeyword);
	}

	public static void reinitializeSearchResults() {
		searchResults = new ArrayList<Task>();
	}

	/**
	 * 
	 * saveToPrevTaskList: Reset prevTaskList and add all objects from taskList
	 * to pTL
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 * 
	 */
	public static void saveToPrevTaskList() {
		reinitializePrevTaskList();
		for (int i = 0; i < taskList.size(); i++) {
			prevTaskList.add(taskList.get(i));
		}
	}

	public static void reinitializePrevTaskList() {
		prevTaskList = new ArrayList<Task>();
	}

	public static void reinitializeTaskList() {
		taskList = new ArrayList<Task>();
	}

	/**
	 * 
	 * undo: Reset taskList then add contents of pTL to tL.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void undo() {
		reinitializeTaskList();
		for (int i = 0; i < prevTaskList.size(); i++) {
			taskList.add(prevTaskList.get(i));
		}
		System.out.println(UNDO_SUCCESS_MESSAGE);
	}

	/* if (isUndoable()) { */
	/*
	 * else { System.out.println(UNDO_UNSUCCESSFUL_MESSAGE); }
	 */
	public static boolean isUndoable() {
		return taskList.size() != prevTaskList.size();
	}

	/**
	 * 
	 * editContent: edit content of specific task using the taskID based on
	 * user's input
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public static void editContent() {
		int id = Integer.parseInt(info[15]);
		int counter;
		int i;

		for (counter = 0; counter < taskList.size(); counter++) {
			if (Integer.parseInt(taskList.get(counter).getTaskID()) == id) {
				for (i = 1; i < info.length; i++) {
					if (info[i] != null) {
						switch (i) {
						case 1:
							taskList.get(counter).setDetails(info[i]);
							break;
						case 2:
							taskList.get(counter).setStartDay(info[i]);
							break;
						case 3:
							taskList.get(counter).setStartMonth(info[i]);
							break;
						case 4:
							taskList.get(counter).setStartYear(info[i]);
							break;
						case 5:
							taskList.get(counter).setEndDay(info[i]);
							break;
						case 6:
							taskList.get(counter).setEndMonth(info[i]);
							break;
						case 7:
							taskList.get(counter).setEndYear(info[i]);
							break;
						case 8:
							taskList.get(counter).setStartHours(info[i]);
							break;
						case 9:
							taskList.get(counter).setStartMin(info[i]);
							break;
						case 10:
							taskList.get(counter).setEndHours(info[i]);
							break;
						case 11:
							taskList.get(counter).setEndMins(info[i]);
							break;
						case 12:
							taskList.get(counter).setLocation(info[i]);
							break;
						case 13:
							taskList.get(counter).setPriority(info[i]);
							break;
						case 14:
							taskList.get(counter).setCategory(info[i]);
							break;
						case 15:
							// taskList.get(counter).setTaskID(info[i]);
							break;
						default:
							// invalid message
						}
					}
				}
			}

		}

	}

}
