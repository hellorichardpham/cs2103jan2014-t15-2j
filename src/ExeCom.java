//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Scanner;

public class ExeCom {
	private static Command c;
	private static ArrayList<Task> taskList;
	private static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> redoTaskList;

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

	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.";
	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
	//private static final String CONFLICT_FOUND = "There is a conflict of schedule with Task ID: %1d";

	Scanner scanner = new Scanner(System.in);
	private static ExeCom theOne;

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

	// constructor
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
	 * @param Command
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
			saveToPrevTaskList();
			Add a = new Add(getTaskListInstance());
			a.addToTaskList(command);
			break;
			
		case DISPLAY:
			if (isValidUndoRedoDisplayCommand()) {
				Display d = new Display(getTaskListInstance());
				d.display();
			} else
				System.out.println(TASKLIST_EMPTY_MESSAGE);
			break;

		case DELETE:
			saveToPrevTaskList();
			Delete del = new Delete(getTaskListInstance());
			del.delete(c);
			// s.loadStorage(); // to update the taskList
			break;

		case SEARCH:
			if (isValidSearchCommand(c)) {
				Search search = new Search(taskList);
				search.searchTaskList(c);
			} else {
				System.out.println(INVALID_COMMAND_MESSAGE);
			}
			break;

		case EDIT:
			saveToPrevTaskList();
			Update u = new Update();
			u.editContent(c);
			break;

		case UNDO:
			undo();
			break;

		case REDO:
			redo();
			break;
		}

		saveToRedoTaskList();
		s.saveStorage();
		printPrev();
		printTaskList();
		return " ";
	}

	public static void printPrev() {
		System.out.println("prevTaskList: ");
		for(Task task : prevTaskList)
			System.out.println(task.displayAll());
	}
	public static void printTaskList() {
		System.out.println("TaskList: ");
		for(Task task : taskList)
			System.out.println(task.displayAll());
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
			transferTasksFromTo(prevTaskList, taskList);

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
			transferTasksFromTo(redoTaskList, taskList);
			/*
			 * for (Task task : redoTaskList) { taskList.add(task); }
			 */
			System.out.println(REDO_SUCCESS_MESSAGE);
		} else if (isValidUndoRedoDisplayCommand() && redoTaskList.isEmpty()) {
			System.out.println(REDO_UNSUCCESSFUL_MESSAGE);
		} else {
			System.out.println(INVALID_COMMAND_MESSAGE);
		}
	}

	/**
	 * 
	 * checkConflict: check conflict of time and date, return true if there are
	 * conflict
	 * 
	 * @author Khaleef
	 * @param void
	 * @return Boolean
	 * 

	public static boolean checkConflict() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		String s1Input;
		String e1Input;
		// copy endDay & endMonth & endYear to startDay & startMonth & startYear
		if (c.getStartDay() == null) {
			s1Input = c.getEndDay() + "/" + c.getEndMonth() + "/"
					+ c.getEndYear() + " " + c.getStartHours() + ":"
					+ c.getStartMins();
		}
		// copy endHours & endMins to startHours & startMins
		else if (c.getStartHours() == null) {
			s1Input = c.getStartDay() + "/" + c.getStartMonth() + "/"
					+ c.getStartYear() + " " + c.getEndHours() + ":"
					+ c.getEndMins();
		} else if (c.getStartHours() == null && c.getStartDay() == null) {
			s1Input = c.getEndDay() + "/" + c.getEndMonth() + "/"
					+ c.getEndYear() + " " + c.getEndHours() + ":"
					+ c.getEndMins();
		} else {
			s1Input = c.getStartDay() + "/" + c.getStartMonth() + "/"
					+ c.getStartYear() + " " + c.getStartHours() + ":"
					+ c.getStartMins();
		}


		if (c.getEndDay() == null) {
			e1Input = c.getStartDay() + "/" + c.getStartMonth() + "/"
					+ c.getStartYear() + " " + c.getEndHours() + ":"
					+ c.getEndMins();
		}
		// copy endHours & endMins to startHours & startMins
		else if (c.getEndHours() == null) {
			e1Input = c.getEndDay() + "/" + c.getEndMonth() + "/"
					+ c.getEndYear() + " " + c.getStartHours() + ":"
					+ c.getStartMins();
		} else if (c.getEndHours() == null && c.getEndDay() == null) {
			e1Input = c.getStartDay() + "/" + c.getStartMonth() + "/"
					+ c.getStartYear() + " " + c.getStartHours() + ":"
					+ c.getStartMins();
		} else {
			e1Input = c.getEndDay() + "/" + c.getEndMonth() + "/" + c.getEndYear()
					+ " " + c.getEndHours() + ":" + c.getEndMins();
		}

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

			if (taskList.get(index).getStartDay().equals("null")) {
				s2Task = taskList.get(index).getEndDay() + "/" + taskList.get(index).getEndMonth() + "/"
						+ taskList.get(index).getEndYear() + " " + taskList.get(index).getStartHours() + ":"
						+ taskList.get(index).getStartMins();
			}
			// taskList.get(index)opy endHours & endMins to startHours & startMins
			else if (taskList.get(index).getStartHours().equals("null")) {
				s2Task = taskList.get(index).getStartDay() + "/" + taskList.get(index).getStartMonth() + "/"
						+ taskList.get(index).getStartYear() + " " + taskList.get(index).getEndHours() + ":"
						+ taskList.get(index).getEndMins();
			} else if (taskList.get(index).getStartHours().equals("null") && taskList.get(index).getStartDay().equals("null")) {
				s2Task = taskList.get(index).getEndDay() + "/" + taskList.get(index).getEndMonth() + "/"
						+ taskList.get(index).getEndYear() + " " + taskList.get(index).getEndHours() + ":"
						+ taskList.get(index).getEndMins();
			} else {
				s2Task = taskList.get(index).getStartDay() + "/" + taskList.get(index).getStartMonth() + "/"
						+ taskList.get(index).getStartYear() + " " + taskList.get(index).getStartHours() + ":"
						+ taskList.get(index).getStartMins();
			}


			if (taskList.get(index).getEndDay().equals("null")) {
				e2Task = taskList.get(index).getStartDay() + "/" + taskList.get(index).getStartMonth() + "/"
						+ taskList.get(index).getStartYear() + " " + taskList.get(index).getEndHours() + ":"
						+ taskList.get(index).getEndMins();
			}
			// taskList.get(index)opy endHours & endMins to startHours & startMins
			else if (taskList.get(index).getEndHours().equals("null")) {
				e2Task = taskList.get(index).getEndDay() + "/" + taskList.get(index).getEndMonth() + "/"
						+ taskList.get(index).getEndYear() + " " + taskList.get(index).getStartHours() + ":"
						+ taskList.get(index).getStartMins();
			} else if (taskList.get(index).getEndHours().equals("null") && taskList.get(index).getEndDay().equals("null")) {
				e2Task = taskList.get(index).getStartDay() + "/" + taskList.get(index).getStartMonth() + "/"
						+ taskList.get(index).getStartYear() + " " + taskList.get(index).getStartHours() + ":"
						+ taskList.get(index).getStartMins();
			} else {
				e2Task = taskList.get(index).getEndDay() + "/" + taskList.get(index).getEndMonth() + "/" + taskList.get(index).getEndYear()
						+ " " + taskList.get(index).getEndHours() + ":" + taskList.get(index).getEndMins();
			}


			s2Task = taskList.get(index).getStartDay() + "/"
					+ taskList.get(index).getStartMonth() + "/"
					+ taskList.get(index).getStartYear() + " "
					+ taskList.get(index).getStartHours() + ":"
					+ taskList.get(index).getStartMins();
			e2Task = taskList.get(index).getEndDay() + "/"
					+ taskList.get(index).getEndMonth() + "/"
					+ taskList.get(index).getEndYear() + " "
					+ taskList.get(index).getEndHours() + ":"
					+ taskList.get(index).getEndMins();

			s2DateTime = sdf.parse(s2Task);
			e2Datetime = sdf.parse(e2Task);

			s1 = s1DateTime.getTime();
			e1 = e1DateTime.getTime();
			s2 = s2DateTime.getTime();
			e2 = e2Datetime.getTime();

			isConflict = ((s1 >= s2) && (s1 <= e2))
					|| ((e1 >= s2) && (e2 <= e2)) || ((s1 <= s2) && (e1 >= e2));
			if (isConflict == true)
				System.out.println("There is a conflict of schedule with Task ID: "
								+ (taskList.indexOf(task) + 1));
			break;
		}
		return isConflict;
	}
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
	 * @author Richard
	 * @param void
	 * @return void
	 * 
	 */
	public static void saveToPrevTaskList() {
		resetPrevTaskList();
		transferTasksFromTo(taskList, prevTaskList);
		/*
		 * for (Task task : taskList) { prevTaskList.add(new Task(task)); }
		 */
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
		transferTasksFromTo(taskList, redoTaskList);

		/*
		 * for (Task task : taskList) { redoTaskList.add(new Task(task)); }
		 */
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
	 * isValidUndoRedoDisplayCommand: Checks if the user specified an invalid
	 * command where undo/redo/display is followed by another String.
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

}
