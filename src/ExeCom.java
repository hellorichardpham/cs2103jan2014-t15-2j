import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	private static final String UPDATE = "update";
	private final static String REDO = "redo";
	private final static String EMAIL = "email";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.";
	private static final String REDO_SUCCESS_MESSAGE = "Action has successfully been redone";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be undone.";
	private static final String REDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be redone.";
	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.";
	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";
	// private static final String CONFLICT_FOUND =
	// "There is a conflict of schedule with Task ID: %1d";
	

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
			if (checkConflict() == false) {
				saveToPrevTaskList();
				Add a = new Add(getTaskListInstance());
				a.addToTaskList(command);
				saveToRedoTaskList();
			}
			break;

		case DISPLAY:
			if (isValidUndoRedoDisplayCommand()) {
				Display d = new Display(getTaskListInstance());
				d.displayTaskList();
			} else
				System.out.println(TASKLIST_EMPTY_MESSAGE);
			break;

		case DELETE:
			saveToPrevTaskList();
			Delete del = new Delete(getTaskListInstance());
			del.delete(c);
			saveToRedoTaskList();
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
		case UPDATE: 
			saveToPrevTaskList();
			Update u = new Update();
			u.editContent(c);
			saveToRedoTaskList();
			break;

		case UNDO:
			undo();
			break;

		case REDO:
			redo();
			break;
			
		case EMAIL:
			Email email = new Email(getTaskListInstance());
			email.emailUser();
			break;
		}

		s.saveStorage();
		return " ";
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
		if (isValidUndoRedoDisplayCommand()
				&& (!redoTaskList.isEmpty() || !prevTaskList.isEmpty())) {
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
	 */

	public static boolean checkConflict() throws Exception {

		boolean isConflict = false;

		String s1InputDay = c.getStartDay();
		String s1InputMonth = c.getStartMonth();
		String s1InputYear = c.getStartYear();
		String s1InputHours = c.getStartHours();
		String s1InputMins = c.getStartMins();

		String e1InputDay = c.getEndDay();
		String e1InputMonth = c.getEndMonth();
		String e1InputYear = c.getEndYear();
		String e1InputHours = c.getEndHours();
		String e1InputMins = c.getEndMins();

		String s1Input;
		String e1Input;

		// System.out.println(s1InputDay);
		// System.out.println(s1InputMonth);
		// System.out.println(s1InputYear);
		// System.out.println(s1InputHours);
		// System.out.println(s1InputMins);
		//
		// System.out.println(e1InputDay);
		// System.out.println(e1InputMonth);
		// System.out.println(e1InputYear);
		// System.out.println(e1InputHours);
		// System.out.println(e1InputMins);

		if (taskList.isEmpty()) {
			return isConflict;
		}

		if (s1InputHours == null && s1InputMins == null && e1InputHours == null
				&& e1InputMins == null) {
			return isConflict;
		}

		if (s1InputDay == null) {
			s1InputDay = c.getEndDay();
			s1InputMonth = c.getEndMonth();
			s1InputYear = c.getEndYear();
		}
		if (s1InputHours == null) {
			s1InputHours = c.getEndHours();
			s1InputMins = c.getEndMins();
		}

		s1Input = s1InputDay + "/" + s1InputMonth + "/" + s1InputYear + " "
				+ s1InputHours + ":" + s1InputMins;

		if (e1InputDay == null) {
			e1InputDay = c.getStartDay();
			e1InputMonth = c.getStartMonth();
			e1InputYear = c.getStartYear();
		}
		if (e1InputHours == null) {
			e1InputHours = c.getStartHours();
			e1InputMins = c.getStartDay();
		}

		e1Input = e1InputDay + "/" + e1InputMonth + "/" + e1InputYear + " "
				+ e1InputHours + ":" + e1InputMins;

		// System.out.println(s1Input);
		// System.out.println(e1Input);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Date s1DateTime = sdf.parse(s1Input);
		Date e1DateTime = sdf.parse(e1Input);

		String s2TaskDay;
		String s2TaskMonth;
		String s2TaskYear;
		String s2TaskHours;
		String s2TaskMins;

		String e2TaskDay;
		String e2TaskMonth;
		String e2TaskYear;
		String e2TaskHours;
		String e2TaskMins;

		String s2Task;
		String e2Task;

		int index;
		Date s2DateTime;
		Date e2Datetime;

		long s1;
		long e1;
		long s2;
		long e2;

		// System.out.println(s1Input);
		// System.out.println(e1Input);

		for (Task task : taskList) {
			index = taskList.indexOf(task);

			s2TaskDay = taskList.get(index).getStartDay();
			s2TaskMonth = taskList.get(index).getStartMonth();
			s2TaskYear = taskList.get(index).getStartYear();
			s2TaskHours = taskList.get(index).getStartHours();
			s2TaskMins = taskList.get(index).getStartMins();

			e2TaskDay = taskList.get(index).getEndDay();
			e2TaskMonth = taskList.get(index).getEndMonth();
			e2TaskYear = taskList.get(index).getEndYear();
			e2TaskHours = taskList.get(index).getEndHours();
			e2TaskMins = taskList.get(index).getEndMins();

			if (s2TaskHours.equals("null") & s2TaskMins.equals("null")
					& e2TaskHours.equals("null") & e2TaskMins.equals("null")) {
				continue;
			}

			if (s2TaskDay.equals("null")) {
				s2TaskDay = taskList.get(index).getEndDay();
				s2TaskMonth = taskList.get(index).getEndMonth();
				s2TaskYear = taskList.get(index).getEndYear();
			}

			if (s2TaskHours.equals("null")) {
				s2TaskHours = taskList.get(index).getEndHours();
				s2TaskMins = taskList.get(index).getEndMins();
			}

			s2Task = s2TaskDay + "/" + s2TaskMonth + "/" + s2TaskYear + " "
					+ s2TaskHours + ":" + s2TaskMins;

			if (e2TaskDay.equals("null")) {
				e2TaskDay = taskList.get(index).getStartDay();
				e2TaskMonth = taskList.get(index).getStartMonth();
				e2TaskYear = taskList.get(index).getStartYear();
			}

			if (e2TaskHours.equals("null")) {
				e2TaskHours = taskList.get(index).getStartHours();
				e2TaskMins = taskList.get(index).getStartMins();
			}

			e2Task = e2TaskDay + "/" + e2TaskMonth + "/" + e2TaskYear + " "
					+ e2TaskHours + ":" + e2TaskMins;

			s2DateTime = sdf.parse(s2Task);
			e2Datetime = sdf.parse(e2Task);

			s1 = s1DateTime.getTime();
			e1 = e1DateTime.getTime();
			s2 = s2DateTime.getTime();
			e2 = e2Datetime.getTime();

			// System.out.println(s2Task);
			// System.out.println(e2Task);

			// isConflict = ((s1 >= s2) && (s1 <= e2))
			// || ((e1 >= s2) && (e2 <= e2)) || ((s1 <= s2) && (e1 >= e2));

			if (s1 < s2) {
				if (e1 > s2) {
					isConflict = true; // overlap
				}
			} else {
				if (e2 > s1) {
					isConflict = true; // overlap
				}
			}

			if (isConflict == true) {
				System.out
						.println("There is a conflict of schedule with Task ID: "
								+ (taskList.indexOf(task) + 1));
				break;
			}
		}
		return isConflict;
	}

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
