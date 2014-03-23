import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ExeCom {
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> redoTaskList;
	private static ArrayList<Task> searchResults;
	private static Command c;
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

	public ArrayList<Task> getTaskListInstance() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	//Allows all part of the program to get the same instance of Execom
	public static ExeCom getInstance() {
		if (theOne == null) {
			theOne = new ExeCom();
		}
		return theOne;
	}

	
	//constructor
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
	public String executeCommand(Command command) throws Exception {
		c = command;
		String keyWord = c.getKeyword().toLowerCase();

		Storage s = new Storage();
		s.loadStorage();	
		
		switch (keyWord) {
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
			//s.loadStorage(); // to update the taskList
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
				String print = task.display();
				print = print.replace("null ", "");
				print = print.replace("null", "");
				System.out.println((taskList.indexOf(task)+1) + ": " + print);
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
	 * @throws Exception 
	 */
	public static void addToTaskList() throws Exception {
		if(checkConflict()==false)
		{
			Task taskToAdd = new Task(c);
			saveToPrevTaskList();
			taskToAdd.setTaskID(Integer.toString(taskList.size() + 1));
			taskList.add(taskToAdd);
			saveToRedoTaskList();
			System.out.println(ADD_SUCCESSFUL_MESSAGE);
		}
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
	 * isPositiveInteger: Checks if the delete/update/edit parameter is a valid taskID
	 * (positive integer)
	 * 
	 * @author Richard
	 * @param void
	 * @return boolean
	 * 
	 */
	public static boolean isPositiveInteger() {
		try {
			if (Integer.parseInt(c.getTaskID()) > 0) {
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
		return Integer.parseInt(c.getTaskID());
	}

	/**
	 * 
	 * isTaskIDMatch: Checks if a task's taskID is equal to the userSpecified
	 * taskIdNumber that he's searching for.
	 * 
	 * @author Richard
	 * @param Task, int
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
		if (isValidSearchCommand(c)) {
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

	public static boolean isValidSearchCommand(Command c) {
		return c.getDetails() != null;
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
		transferTasksFromTo(taskList,prevTaskList);
		/*for (Task task : taskList) {
			prevTaskList.add(new Task(task));
		}*/
	}
	
	public static void transferTasksFromTo(ArrayList<Task> source, ArrayList<Task> target) {
		for(Task task : source) {
			target.add(new Task(task));
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
		transferTasksFromTo(taskList,redoTaskList);
		
		/*for (Task task : taskList) {
			redoTaskList.add(new Task(task));
		}*/
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
			transferTasksFromTo(prevTaskList,taskList);
			/*for (Task task : prevTaskList) {
				taskList.add(task);
			}*/
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
			transferTasksFromTo(redoTaskList,taskList);
			/*for (Task task : redoTaskList) {
				taskList.add(task);
			}*/
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
		if (c.getDetails() == null) {
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
	 * @author yingyun, weizhou
	 * @param void
	 * @return void
	 */
	public static void editContent() {
		int id = Integer.parseInt(c.getTaskID());	//user specified task ID

		//loop through taskList to find matching task object
		for (int i = 0; i< taskList.size(); i++) {
			Task currentTask = taskList.get(i);
			int currentTaskID = Integer.parseInt(currentTask.getTaskID());
			if (currentTaskID==id) {
				saveToPrevTaskList();
				
				//Update specified task object using either information from command/current task object
				currentTask.setDetails(merge(c.getDetails(),currentTask.getDetails()));
				
				currentTask.setStartDay(merge(c.getStartDay(),currentTask.getStartDay()));
				currentTask.setStartMonth(merge(c.getStartMonth(),currentTask.getStartMonth()));
				currentTask.setStartYear(merge(c.getStartYear(),currentTask.getStartYear()));
				
				currentTask.setEndDay(merge(c.getEndDay(),currentTask.getEndDay()));
				currentTask.setEndMonth(merge(c.getEndMonth(),currentTask.getEndMonth()));
				currentTask.setEndYear(merge(c.getEndYear(),currentTask.getEndYear()));
				
				currentTask.setStartHours(merge(c.getStartHours(),currentTask.getStartHours()));
				currentTask.setStartMins(merge(c.getStartMins(),currentTask.getStartMins()));
				
				currentTask.setEndHours(merge(c.getEndHours(),currentTask.getEndHours()));
				currentTask.setEndMins(merge(c.getEndMins(),currentTask.getEndMins()));
				
				currentTask.setLocation(merge(c.getLocation(),currentTask.getLocation()));
				currentTask.setCategory(merge(c.getCategory(),currentTask.getCategory()));
				currentTask.setDetails(merge(c.getPriority(),currentTask.getPriority()));
			
				saveToRedoTaskList();
			}
		}
	}

	/**
	 * merge: returns selected information from either command object or task object.
	 * @author yingyun
	 * @param String, String
	 * @return String
	 */
	private static String merge(String fromCommand, String fromTask) {
		//user did not specify this attribute to be updated
		if (fromCommand == null){
			//use back the same information from task object
			return fromTask;
		} else {
			//update information from command object
			return fromCommand;
		}
	}
	
	/**
	 * 
	 * checkConflict: check conflict of time and date, return true if there are conflict
	 * 
	 * @author Khaleef
	 * @param void
	 * @return boolean
	 */
	public static boolean checkConflict() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		String s1Input;
		String e1Input;

		// copy endDay & endMonth & endYear to startDay & startMonth & startYear 
		if(c.getStartDay()==null)
		{
			s1Input = c.getEndDay() + "/" + c.getEndMonth() + "/" + c.getEndYear() + " " + c.getStartHours() + ":" + c.getStartMins();
		}
		// copy endHours & endMins to startHours & startMins 
		else if(c.getStartHours()==null)
		{
			s1Input = c.getStartDay() + "/" + c.getStartMonth() + "/" + c.getStartYear() + " " + c.getEndHours() + ":" + c.getEndMins();
		}
		else if(c.getStartHours()==null && c.getStartDay()==null)
		{
			s1Input = c.getEndDay() + "/" + c.getEndMonth() + "/" + c.getEndYear() + " " + c.getEndHours() + ":" + c.getEndMins();
		}
		else
		{
			s1Input = c.getStartDay() + "/" + c.getStartMonth() + "/" + c.getStartYear() + " " + c.getStartHours() + ":" + c.getStartMins();
		}
		
		e1Input = c.getEndDay() + "/" + c.getEndMonth() + "/" + c.getEndYear() + " " + c.getEndHours() + ":" + c.getEndMins();
		
		//System.out.println(s1Input);
		//System.out.println(e1Input);

		Date s1DateTime = sdf.parse(s1Input);
		Date e1DateTime = sdf.parse(e1Input);
		
		String s2Task;
		String e2Task;
		int index;
		boolean isConflict = false;
		Date s2DateTime;
		Date e2Datetime;
		
		long s1;
		long e1;
		long s2;
		long e2;
		
		for (Task task : taskList) {
			index = taskList.indexOf(task);
			s2Task = taskList.get(index).getStartDay() + "/" + taskList.get(index).getStartMonth() + "/" + taskList.get(index).getStartYear() +  " " + taskList.get(index).getStartHours() + ":" +taskList.get(index).getStartMins();
			e2Task = taskList.get(index).getEndDay() + "/" + taskList.get(index).getEndMonth() + "/" + taskList.get(index).getEndYear() +  " " + taskList.get(index).getEndHours() + ":" +taskList.get(index).getEndMins();
			
			s2DateTime = sdf.parse(s2Task);
			e2Datetime = sdf.parse(e2Task);
			
			s1 = s1DateTime.getTime();
			e1 = e1DateTime.getTime();
			s2 = s2DateTime.getTime();
			e2 = e2Datetime.getTime();
			
			isConflict = ((s1 >= s2) && (s1 <= e2)) || ((e1 >= s2) && (e2 <= e2)) || ((s1 <= s2) && (e1 >= e2));
			if (isConflict == true)
				System.out.println("There is a conflict of schedule with Task ID: " + (taskList.indexOf(task)+1));
				break;
		}
		return isConflict;
	}
}
