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
			ArrayList<Integer> conflicts = new ArrayList<Integer>();
			conflicts = checkConflict();
			if (conflicts.size() <= 0) {
				saveToPrevTaskList();
				Add a = new Add(getTaskListInstance());
				a.addToTaskList(command);
				saveToRedoTaskList();
			}
			else {
				System.out.println("There is a conflict.");
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

	public static ArrayList<Integer> checkConflict() {
		
		ArrayList<Integer> conflicts = new ArrayList<Integer>();
			
		for(int i=0; i<taskList.size(); i++) {
			
				Task current = taskList.get(i);
				int taskStart = setStartSignature(current);
				int taskEnd = setEndSignature(current);
				int commandStart = setStartSignature(c);
				int commandEnd = setEndSignature(c);
				
				if(taskStart == -1 && commandStart == -1) {
					if(taskEnd==commandEnd) {
						conflicts.add(i);
					}
				}
				if(taskStart != -1 && commandStart != -1) {
					if(commandStart >= taskStart && commandStart < taskEnd) {
						conflicts.add(i);
					}
					else if(commandEnd > taskStart && commandEnd <= taskEnd) {
						conflicts.add(i);
					}
				}
				if(taskStart != -1 && commandStart == -1) {
					if(commandEnd > taskStart && commandEnd < taskEnd) {
						conflicts.add(i);
					}
				}
				if(taskStart == -1 && commandStart != -1) {
					if(taskEnd > commandStart && taskEnd < commandEnd) {
						conflicts.add(i);
					}
				}
			}
		return conflicts;
	}

	private static int setEndSignature(Command comm) {
		int end = 0;
		
		end += 100000000*Integer.parseInt(comm.getEndYear());
		end += 1000000*Integer.parseInt(comm.getEndMonth());
		end += 10000*Integer.parseInt(comm.getEndDay());
		
		if(comm.getEndHours().equals("null")) {
			end += 2359;
		}
		else {
			end += 100*Integer.parseInt(comm.getEndHours());
			end += Integer.parseInt(comm.getEndMins());
		}
		
 		return end;
	}

	private static int setStartSignature(Command comm) {
		
		int start = -1;
		
		if(comm.getStartYear()!=null) {
			start += 100000000*Integer.parseInt(comm.getStartYear());
			start += 1000000*Integer.parseInt(comm.getStartMonth());
			start += 10000*Integer.parseInt(comm.getStartDay());
		}
		else if(comm.getStartMins()!=null) {
			start += 100000000*Integer.parseInt(comm.getEndYear());
			start += 1000000*Integer.parseInt(comm.getEndMonth());
			start += 10000*Integer.parseInt(comm.getEndDay());
		}
		else {
			return start;
		}
		
		if(comm.getStartMins()!=null) {
			start += 100*Integer.parseInt(comm.getStartHours());
			start += Integer.parseInt(comm.getStartMins());
		}
		return start;
		
	}

	private static int setEndSignature(Task task) {
		
		int end = 0;
		
		end += 100000000*Integer.parseInt(task.getEndYear());
		end += 1000000*Integer.parseInt(task.getEndMonth());
		end += 10000*Integer.parseInt(task.getEndDay());
		
		if(task.getEndHours().equals("null")) {
			end += 2359;
		}
		else {
			end += 100*Integer.parseInt(task.getEndHours());
			end += Integer.parseInt(task.getEndMins());
		}
		
 		return end;
	}

	private static int setStartSignature(Task task) {
int start = -1;
		
		if(!task.getStartYear().equals("null")) {
			start += 100000000*Integer.parseInt(task.getStartYear());
			start += 1000000*Integer.parseInt(task.getStartMonth());
			start += 10000*Integer.parseInt(task.getStartDay());
		}
		else if(!task.getStartMins().equals("null")) {
			start += 100000000*Integer.parseInt(task.getEndYear());
			start += 1000000*Integer.parseInt(task.getEndMonth());
			start += 10000*Integer.parseInt(task.getEndDay());
		}
		else {
			return start;
		}
		
		if(!task.getStartMins().equals("null")) {
			start += 100*Integer.parseInt(task.getStartHours());
			start += Integer.parseInt(task.getStartMins());
		}
		return start;
		
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
