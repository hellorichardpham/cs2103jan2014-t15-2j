import java.util.ArrayList;
import java.util.Scanner;

public class ExeCom {
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> redoTaskList;
	private static ArrayList<Task> searchResults;
	private static String[] info;
	private final static String ADD = "add";
	private final static String DISPLAY = "display";
	private final static String DELETE = "delete";
	private final static String SEARCH = "search";
	private final static String UNDO = "undo";
	private final static String EDIT = "edit";
	private final static String REDO = "redo";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.";
	private static final String REDO_SUCCESS_MESSAGE = "Action has successfully been redone";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be undone.";
	private static final String REDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be redone.";
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.";
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.";
	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.";
	private final static String NOT_INTEGER_MESSAGE = "ERROR: This is not a positive integer.";

	private static ExeCom theOne;
	Scanner scanner = new Scanner(System.in);

	public static ArrayList<Task> getTaskListInstance() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	public static ExeCom getInstance() {
		if (theOne == null) {
			theOne = new ExeCom();
		}
		return theOne;
	}

	ExeCom() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		prevTaskList = new ArrayList<Task>();
		redoTaskList = new ArrayList<Task>();
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
	public String executeCommand(String[] userCommandInfo) throws Exception {
		info = userCommandInfo;
		String command = info[0];

		Storage s = Storage.getInstance();
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
		case REDO:
			redo();
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
		if (!taskList.isEmpty() && isValidUndoRedoDisplayCommand()) {
			System.out.println("~~~~~ Listing of all tasks ~~~~~");
			for (Task task : taskList) {
				String print = task.displayAll();
				print = print.replace("null ", "");
				print = print.replace("null", "");
				System.out.println(print);
			}

		} else if (taskList.isEmpty() && isValidUndoRedoDisplayCommand()) {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
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
		saveToRedoTaskList();
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
		//if (isPositiveInteger()) {
			assert(isPositiveInteger());
			int taskIdNumber = retrieveTaskIdNumber();
			boolean isFound = false;
			for (int i = 0; i < taskList.size(); i++) {
				if (isTaskIDMatch(taskList.get(i), taskIdNumber)) {
					saveToPrevTaskList();
					System.out.println("Deleted: "
							+ taskList.get(i).getDetails());
					taskList.remove(taskList.get(i));
					saveToRedoTaskList();
					isFound = true;
				}
			}
			if (!isFound) {
				System.out.println(TASKID_NOT_FOUND_MESSAGE);
			}
		/*} else {
			// User input was "delete (String)" or "delete (negative #)"
			System.out.println(NOT_INTEGER_MESSAGE);
		}*/
	}

	/**
	 * 
	 * isPositiveInteger: Checks if the delete parameter is a valid taskID
	 * (positive integer)
	 * 
	 * @author Richard
	 * @param void
	 * @return boolean
	 * 
	 */
	public static boolean isPositiveInteger() {
		try {
			if (Integer.parseInt(info[15]) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 
	 * retrieveTaskIdNumber: retrieves user-specified taskID. We know it's valid
	 * because it passed the isPositiveInteger() test
	 * 
	 * @author Richard
	 * @param void
	 * @return int
	 * 
	 */

	public static int retrieveTaskIdNumber() {
		return Integer.parseInt(info[15]);
	}

	/**
	 * 
	 * isTaskIDMatch: Checks if a task's taskID is equal to the userSpecified
	 * taskIdNumber that he's searching for.
	 * 
	 * @author Richard
	 * @param Task
	 *            , int
	 * @return boolean
	 * 
	 */

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
			for (Task task : searchResults) {
				System.out.println(task.displayAll());
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
			resetSearchResults();
			String searchKeyword = info[1];
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
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	/**
	 * 
	 * isValidSearchCommand: Makes sure there is a keyword that the user is
	 * searching for instead of an invalid command like "search"
	 * 
	 * @author Richard
	 * @param String
	 *            []
	 * @return boolean
	 */

	public static boolean isValidSearchCommand(String[] info) {
		return info[1] != null;
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
		resetPrevTaskList();
		for (Task task : taskList) {
			prevTaskList.add(new Task(task));
		}
	}

	/**
	 * 
	 * saveToRedoTaskList: Reset redoTaskList and add all objects from taskList
	 * to rTL
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 * 
	 */

	public static void saveToRedoTaskList() {
		resetRedoTaskList();
		for (Task task : taskList) {
			redoTaskList.add(new Task(task));
		}
	}

	/**
	 * 
	 * resetRedoTaskList: Reinitializes redoTaskList so it will be empty when we
	 * redo a command.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void resetRedoTaskList() {
		redoTaskList = new ArrayList<Task>();
	}

	/**
	 * 
	 * resetPrevTaskList: Reinitializes prevTaskList so it will be empty when we
	 * undo a command.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void resetPrevTaskList() {
		prevTaskList = new ArrayList<Task>();
	}

	/**
	 * 
	 * resetTaskList: Reinitializes taskList so it will be empty when we perform
	 * undo or redo.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void resetTaskList() {
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
		if (isValidUndoRedoDisplayCommand() && !prevTaskList.isEmpty()) {
			resetTaskList();
			for (Task task : prevTaskList) {
				taskList.add(task);
			}
			System.out.println(UNDO_SUCCESS_MESSAGE);
		} else if (isValidUndoRedoDisplayCommand() && prevTaskList.isEmpty()) {
			System.out.println(UNDO_UNSUCCESSFUL_MESSAGE);
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	/**
	 * 
	 * redo: Reperforms any task that was done before undo() was called.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void redo() {
		if (isValidUndoRedoDisplayCommand() && !redoTaskList.isEmpty()) {
			resetTaskList();
			for (Task task : redoTaskList) {
				taskList.add(task);
			}
			System.out.println(REDO_SUCCESS_MESSAGE);
		} else if (isValidUndoRedoDisplayCommand() && redoTaskList.isEmpty()) {
			System.out.println(REDO_UNSUCCESSFUL_MESSAGE);
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	/**
	 * 
	 * isValidUndoRedoDisplayCommand: Checks if the user specified an invalid command
	 * where undo/redo/display is followed by another String.
	 * 
	 * @author Richard
	 * @param void
	 * @return boolean
	 */
	public static boolean isValidUndoRedoDisplayCommand() {
		if (info[1] == null) {
			return true;
		} else {
			return false;
		}
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
				saveToPrevTaskList();
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
				saveToRedoTaskList();
			}

		}

	}

}
