import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class ExeCom {
	private static Command c;
	private static ArrayList<Task> taskList;
	private static Stack<ArrayList<Task>> undoStack;
	private static Stack<ArrayList<Task>> redoStack;
	private static ArrayList<Task>[][] monthList;
	private static String feedback;

	// code placed at front of feedback for GUI to recognize there is a conflict
	private final static String CONFLICTED_CODE = "-cs2103--conflicted ";

	private final static String ADD = "add";
	private static final String COMPLETED = "completed";
	private static final String CANCELLED = "cancel";
	private static final String CLEAR = "clear";
	private final static String DISPLAY = "display";
	private final static String DELETE = "delete";
	private static final String DONE = "done";
	private final static String EDIT = "edit";
	private final static String REDO = "redo";
	private final static String SEARCH = "search";
	private final static String UNDO = "undo";
	private static final String UPDATE = "update";
	private final static String JUSTADD = "justadd";
	private final static String JUSTEDIT = "justedit";
	private final static String JUSTUPDATE = "justupdate";
	private final static String EXIT = "exit";
	private final static String QUIT = "quit";
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.\n";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.\n";
	private static final String REDO_SUCCESS_MESSAGE = "Action has successfully been redone.\n";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be undone.\n";
	private static final String REDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be redone.\n";
	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.\n";
	private static final String NO_DETAILS_MESSAGE = "No details detected! This task is not added to the task list\n";
	private static final String INVALID_TIME_MESSAGE = "Time entered is invalid! This task is not added to the task list.\n";
	private static final String CANCELLED_ACTION_MESSAGE = "The action has been cancelled.\n";
	private static final String DISPLAYD = "displayd";
	private static final Object EMPTY_STRING = "";
	private static ExeCom theOne;

	public static String getFeedback() {
		return feedback;
	}

	public static void setFeedback(String feedback) {
		ExeCom.feedback = feedback;
	}

	// Allows all part of the program to get the same instance of ExeCom
	public static ExeCom getInstance() {
		if (theOne == null) {
			theOne = new ExeCom();
		}
		return theOne;
	}

	public static ArrayList<Task> getTaskListInstance() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		return taskList;
	}

	public static ArrayList<Task>[][] getMonthListInstance() {
		monthList = new ArrayList[12][1];
		return monthList;
	}

	// constructor
	ExeCom() {
		if (taskList == null) {
			taskList = new ArrayList<Task>();
		}
		undoStack = new Stack<ArrayList<Task>>();
		redoStack = new Stack<ArrayList<Task>>();
	}

	/**
	 * 
	 * executeCommand: determines which action to perform based on the
	 * userCommand then calls the appropriate method.
	 * 
	 * @author A0118590A, Joey, A0085107J, A0097961M
	 * @param Command
	 * @return String
	 * 
	 */
	public String executeCommand(Command command) throws Exception {

		feedback = "";
		c = command;
		String keyWord = c.getKeyword().toLowerCase();

		Storage s = new Storage();
		s.loadStorage();
		Alarm.setAlarm();

		switch (keyWord) {

		case JUSTADD:
			addTask(command);
			break;

		case ADD:
			if (isValidAddCommand()) {
				if (isValidTime()) {
					ArrayList<Integer> conflicts = new ArrayList<Integer>();
					conflicts = checkConflict();
					if (conflicts.size() <= 0) {
						addTask(command);
					} else {
						feedback = CONFLICTED_CODE
								+ printConflictedTasks(conflicts);
						feedback = feedback + "Add task anyway? (Yes/No): \n\n";
					}
				} else {
					feedback = feedback + INVALID_TIME_MESSAGE + "\n";
				}
			} else {
				feedback = feedback + NO_DETAILS_MESSAGE + "\n";
			}
			break;

		case DISPLAY:
			Display d = new Display(getTaskListInstance(), c,
					getMonthListInstance());
			if (isValidDisplayDateCommand()){
				feedback = feedback + d.displayDate();
			}else if (isDisplayMonth()) {
				feedback = feedback + d.displayStartMonth();
			} else if (isValidUndoRedoDisplayCommand()) {
				feedback = feedback + d.displayTaskList();
			} else if (isDisplayCompleted()) {
				feedback = feedback + d.displayCompleted();
			} else if (isDisplayUncompleted()) {
				feedback = feedback + d.displayUncompleted();
			} else {
				feedback = feedback + INVALID_COMMAND_MESSAGE;
			}
			break;

		case DISPLAYD:
			Display displayDeadline = new Display(getTaskListInstance(), c,
					getMonthListInstance());
			if (isDisplayMonth()) {
				feedback = feedback + displayDeadline.displayEndMonth();
			} else {
				feedback = feedback + INVALID_COMMAND_MESSAGE;
			}
			break;
		case DELETE:
			saveToUndoStack();
			Delete del = new Delete();
			feedback = feedback + del.delete(c);
			// s.loadStorage(); // to update the taskList
			break;

		case COMPLETED:
		case DONE:
			saveToUndoStack();
			Completed completed = new Completed();
			feedback = feedback + completed.markCompleted(c);
			break;

		case SEARCH:
			if (isValidSearchCommand(c)) {
				Search search = new Search(taskList);
				feedback = feedback + search.searchTaskList(c);
			} else {
				feedback = feedback + INVALID_COMMAND_MESSAGE;
			}
			break;

		case EDIT:
		case UPDATE:
			ArrayList<Integer> conflicts = new ArrayList<Integer>();
			conflicts = checkConflict();
			if (conflicts.size() <= 0) {
				saveToUndoStack();
				Update u = new Update();
				feedback = feedback + u.editContent(c);
			} else {
				feedback = CONFLICTED_CODE + printConflictedTasks(conflicts)
						+ "\n";
				feedback = feedback + "Edit task anyway? (Yes/No): \n";
			}
			break;

		case JUSTUPDATE:
		case JUSTEDIT:
			saveToUndoStack();
			Update u = new Update();
			feedback = feedback + u.editContent(c);
			break;

		case UNDO:
			undo();
			break;

		case REDO:
			redo();
			break;

		case CANCELLED:
			feedback = CANCELLED_ACTION_MESSAGE + "\n";
			break;
		case CLEAR:
			break;
		case EXIT:
		case QUIT:
			System.exit(0);
			break;
		default:
			feedback = INVALID_COMMAND_MESSAGE + "\n";

		}
		s.saveStorage();
		return feedback;
	}

	/**
	 * addTask: Adds Task to Tasklist and updates the undo and redo tasklists
	 * 
	 * 
	 * @author Tian Weizhou
	 * @param Command
	 * @return void
	 */
	private void addTask(Command command) throws Exception {
		// System.out.println(command.getDetails());
		Add add = new Add(getTaskListInstance());
		saveToUndoStack();
		add.addToTaskList(command);
		feedback = feedback + ADD_SUCCESSFUL_MESSAGE;
	}

	/**
	 * 
	 * undo: Reset taskList then add contents of pTL to tL.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 */
	public static void undo() {
		if (!undoStack.empty() && isValidUndoRedoDisplayCommand()) {
			ArrayList<Task> newCopy = new ArrayList<Task>();
			transferTasksFromTo(taskList,newCopy);
			redoStack.push(newCopy);
			taskList = undoStack.pop();

			feedback = UNDO_SUCCESS_MESSAGE;
		} else if (isValidUndoRedoDisplayCommand() && undoStack.empty()) {
			feedback = UNDO_UNSUCCESSFUL_MESSAGE;
		} else {
			feedback = INVALID_COMMAND_MESSAGE;
		}
	}

	/**
	 * redo: Reperforms any task that was done before undo() was called.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 */
	public static void redo() {
		if (!redoStack.empty() && isValidUndoRedoDisplayCommand()){
			ArrayList<Task> newCopy = new ArrayList<Task>();
			transferTasksFromTo(taskList,newCopy);
			undoStack.push(newCopy);
			taskList = redoStack.pop();
			feedback = REDO_SUCCESS_MESSAGE;
		} else if (isValidUndoRedoDisplayCommand() && redoStack.empty()) {
			feedback = REDO_UNSUCCESSFUL_MESSAGE;
		} else {
			feedback = INVALID_COMMAND_MESSAGE;
		}
	}

	/**
	 * checkConflict: check conflict of time and date, return ArrayList<Integer>
	 * with the elements being the indexes of conflicting tasks in tasklist
	 * 
	 * @author Tian Weizhou
	 * @param void
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> checkConflict() {

		ArrayList<Integer> conflicts = new ArrayList<Integer>();

		for (int i = 0; i < taskList.size(); i++) {

			Task current = taskList.get(i);
			int taskStart = setStartSignature(current);
			int taskEnd = setEndSignature(current);
			int commandStart = setStartSignature(c);
			int commandEnd = setEndSignature(c);

			if (taskStart == -1 && commandStart == -1) {
				if (taskEnd == commandEnd) {
					conflicts.add(i);
				}
			}
			if (taskStart != -1 && commandStart != -1) {
				if (commandStart >= taskStart && commandStart < taskEnd) {
					conflicts.add(i);
				} else if (commandEnd > taskStart && commandEnd <= taskEnd) {
					conflicts.add(i);
				}
			}
			if (taskStart != -1 && commandStart == -1) {
				if (commandEnd > taskStart && commandEnd < taskEnd) {
					conflicts.add(i);
				}
			}
			if (taskStart == -1 && commandStart != -1) {
				if (taskEnd > commandStart && taskEnd < commandEnd) {
					conflicts.add(i);
				}
			}
		}
		return conflicts;
	}

	/**
	 * printConflictedTask: print all tasks that conflicts with current task
	 * 
	 * @author Wei Zhou
	 * @param ArrayList
	 *            <Integer>
	 * @return void
	 */
	private static String printConflictedTasks(ArrayList<Integer> conflicts) {
		String conflictList = "";
		conflictList = conflictList
				+ "There is a conflict with these tasks: \n";
		for (int i = 0; i < conflicts.size(); i++) {
			conflictList = conflictList + (conflicts.get(i) + 1) + ": ";
			conflictList = conflictList
					+ taskList.get(conflicts.get(i)).displayTask() + "\n";
		}
		return conflictList;
	}

	/**
	 * setStartSignature: returns an integer value which contains all the end
	 * date and end time info in a single number used for efficient comparison
	 * 
	 * @author Tian Weizhou
	 * @param Command
	 * @return int
	 */
	private static int setEndSignature(Command comm) {
		int end = 0;
		if (comm.getEndYear() == null) {
			return end;
		}
		end += 100000000 * Integer.parseInt(comm.getEndYear());
		end += 1000000 * Integer.parseInt(comm.getEndMonth());
		end += 10000 * Integer.parseInt(comm.getEndDay());

		if (comm.getEndHours() == null) {
			end += 2359;
		} else {
			end += 100 * Integer.parseInt(comm.getEndHours());
			end += Integer.parseInt(comm.getEndMins());
		}

		return end;
	}

	/**
	 * setStartSignature: returns an integer value which contains all the start
	 * date and start time info in a single number used for efficient comparison
	 * 
	 * @author Tian Weizhou
	 * @param Command
	 * @return int
	 */
	private static int setStartSignature(Command comm) {

		int start = -1;

		if (comm.getEndYear() == null) {
			return Integer.MAX_VALUE;
		}

		if (comm.getStartYear() != null) {
			start += 100000000 * Integer.parseInt(comm.getStartYear());
			start += 1000000 * Integer.parseInt(comm.getStartMonth());
			start += 10000 * Integer.parseInt(comm.getStartDay());
		} else if (comm.getStartMins() != null) {
			start += 100000000 * Integer.parseInt(comm.getEndYear());
			start += 1000000 * Integer.parseInt(comm.getEndMonth());
			start += 10000 * Integer.parseInt(comm.getEndDay());
		} else {
			return start;
		}

		if (comm.getStartMins() != null) {
			start += 100 * Integer.parseInt(comm.getStartHours());
			start += Integer.parseInt(comm.getStartMins());
		}
		return start;

	}

	/**
	 * setEndSignature: returns an integer value which contains all the End date
	 * and End time details in a single number used for efficient comparison
	 * 
	 * @author Tian Weizhou
	 * @param Task
	 * @return int
	 */
	private static int setEndSignature(Task task) {

		int end = 0;

		end += 100000000 * Integer.parseInt(task.getEndYear());
		end += 1000000 * Integer.parseInt(task.getEndMonth());
		end += 10000 * Integer.parseInt(task.getEndDay());

		if (task.getEndHours().equals("null")) {
			end += 2359;
		} else {
			end += 100 * Integer.parseInt(task.getEndHours());
			end += Integer.parseInt(task.getEndMins());
		}

		return end;
	}

	/**
	 * setStartSignature: returns an integer value which contains all the start
	 * date and start time details in a single number used for efficient
	 * comparison
	 * 
	 * @author Tian Weizhou
	 * @param Task
	 * @return int
	 */
	private static int setStartSignature(Task task) {
		int start = -1;

		if (!task.getStartYear().equals("null")) {
			start += 100000000 * Integer.parseInt(task.getStartYear());
			start += 1000000 * Integer.parseInt(task.getStartMonth());
			start += 10000 * Integer.parseInt(task.getStartDay());
		} else if (!task.getStartMins().equals("null")) {
			start += 100000000 * Integer.parseInt(task.getEndYear());
			start += 1000000 * Integer.parseInt(task.getEndMonth());
			start += 10000 * Integer.parseInt(task.getEndDay());
		} else {
			return start;
		}

		if (!task.getStartMins().equals("null")) {
			start += 100 * Integer.parseInt(task.getStartHours());
			start += Integer.parseInt(task.getStartMins());
		}
		return start;

	}
	/**
	 * 
	 * transferTasksFromTo: Move tasks from one ArrayList to another
	 * 
	 * @author A0118590A
	 * @param ArrayList<Task>, ArrayList<Task>
	 * @return void
	 */
	public static void transferTasksFromTo(ArrayList<Task> source,
			ArrayList<Task> target) {
		for (Task task : source) {
			target.add(new Task(task));
		}
	}

	/**
	 * 
	 * saveToPrevTaskList: Reset prevTaskList and add all objects from taskList
	 * to pTL
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 * 
	 */
	public void saveToUndoStack() {
		ArrayList<Task> undoList = new ArrayList<Task>();
		transferTasksFromTo(taskList,undoList);
		undoStack.push(undoList);
		//transferTasksFromTo(taskList, prevTaskList);
		/*
		 * for (Task task : taskList) { prevTaskList.add(new Task(task)); }
		 */
	}

	/**
	 * 
	 * saveToRedoTaskList: Reset redoTaskList and add all objects from taskList
	 * to rTL
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 * 
	 */
	public void saveToRedoStack() {
		ArrayList<Task> redoList = new ArrayList<Task>();
		transferTasksFromTo(taskList,redoList);
		undoStack.push(redoList);
		/*
		 * for (Task task : taskList) { redoTaskList.add(new Task(task)); }
		 */
	}

	/**
	 * 
	 * resetTaskList: Reinitializes taskList so it will be empty when we perform
	 * undo or redo.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return void
	 */
	public static void resetTaskList() {
		taskList = new ArrayList<Task>();
	}

	/**
	 * 
	 * isValidSearchCommand: Makes sure there is a keyword that the user is
	 * searching for instead of an invalid command like "search"
	 * 
	 * @author A0118590A
	 * @param String
	 *            []
	 * @return boolean
	 */

	public static boolean isValidSearchCommand(Command c) {
		return c.getDetails() != null;
	}

	/**
	 * 
	 * isValidUndoRedoDisplayCommand: Checks if the user specified an invalid
	 * command where undo/redo/display is followed by another String.
	 * 
	 * @author A0118590A
	 * @param void
	 * @return boolean
	 */
	public static boolean isValidUndoRedoDisplayCommand() {
		if (c.getDetails() == null
				&& c.getStartDay()==null 
				&& c.getStartMonth()==null 
				&& c.getStartYear()==null 
				) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * isValidDisplayMonthCommand: Check if user is trying to display a month
	 * 
	 * @author A0118590A
	 * @param void
	 * @return boolean
	 * 
	 */
	public static boolean isValidDisplayMonthCommand() {
		if (c.getDetails().equals(null) && !(c.getEndMonth().equals(null))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * isValidAddCommand: Check if user keyed in details (mandatory)
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidAddCommand() {
		if (c.getDetails() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * isDisplayDate: Check if user wants to display a list of
	 * tasks of a particular date
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private static boolean isValidDisplayDateCommand() {
		if (c.getEndMonth()!=null && (!c.getEndDay().equals(EMPTY_STRING)|| c.getEndDay().equals(null))) { 
			return true;
		}else { 
			return false;
		}
	}

	/**
	 * isValidTime: check if user entered valid time which is from 0000 t0 2359
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidTime() {
		if (isValidHours() && isValidMins()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isValidhours: check if user entered between 0 to 23 hours
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidHours() {
		if (c.getStartHours() != null) {
			int startHours = Integer.parseInt(c.getStartHours());
			if (0 > startHours || startHours >= 24) {
				return false;
			}
		}

		if (c.getEndHours() != null) {
			int endHours = Integer.parseInt(c.getEndHours());
			if (0 > endHours || endHours >= 24) {
				return false;
			}
		}

		return true;
	}

	/**
	 * isValidMins: check if user entered between 0 to 59 minutes
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidMins() {
		if (c.getStartMins() != null) {
			int startMins = Integer.parseInt(c.getStartMins());
			if (0 > startMins || startMins >= 59) {
				return false;
			}
		}

		if (c.getEndMins() != null) {
			int endMins = Integer.parseInt(c.getEndMins());
			if (0 > endMins || endMins >= 59) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * isDisplayCompleted: Check if user wants to display list of completed
	 * tasks
	 * 
	 * @author A0085107J
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isDisplayCompleted() {
		if (c.getDetails() != null) {
			if (c.getDetails().equals("completed")
					|| c.getDetails().equals("completed tasks")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * isDisplayUncompleted: Check if user wants to display a list of
	 * uncompleted tasks
	 * 
	 * @author Khaleef
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isDisplayUncompleted() {
		if (c.getDetails() != null) {
			if (c.getDetails().equals("uncompleted")
					|| c.getDetails().equals("uncompleted tasks")
					|| c.getDetails().equals("uc")
					|| c.getDetails().equals("uc tasks")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * isDisplayMonth: Check if user wants to display a list of
	 * tasks of a particular month
	 * 
	 * @author A0118590A
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isDisplayMonth() {
		if (c.getEndMonth() != null && c.getEndDay().equals(EMPTY_STRING)) { // handles bad commands like
			// "display ofuh"
			switch (c.getEndMonth()) {
			case "01":
				return true;
			case "02":
				return true;
			case "03":
				return true;
			case "04":
				return true;
			case "05":
				return true;
			case "06":
				return true;
			case "07":
				return true;
			case "08":
				return true;
			case "09":
				return true;
			case "10":
				return true;
			case "11":
				return true;
			case "12":
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 
	 * isTaskIDMatch: Checks if a task's taskID is equal to the userSpecified
	 * taskIdNumber that he's searching for.
	 * 
	 * @author A0085107J
	 * @param String , int
	 * @return boolean
	 * 
	 */

	public boolean isTaskIDMatch(String specifiedTaskID, int taskIdNumber) {
		return Integer.parseInt(specifiedTaskID) == taskIdNumber;
	}

	/**
	 * retrieveTaskIdNumber: retrieves user-specified taskID.
	 * 
	 * @author A0085107J
	 * @param String
	 * @return int
	 * 
	 */

	public int retrieveTaskIdNumber(String taskID) {
		return Integer.parseInt(taskID);
	}

	public static boolean checkUndoStack() {
		return !undoStack.empty();
	}
	
	public static boolean checkRedoStack() {
		return !redoStack.empty();
	}
}
