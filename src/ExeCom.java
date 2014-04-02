import java.util.ArrayList;
import java.util.Scanner;

public class ExeCom {
	private static Command c;
	private static ArrayList<Task> taskList;
	private  static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> redoTaskList;
	private static String feedback;

	private final static String ADD = "add";
	private final static String DISPLAY = "display";
	private final static String DELETE = "delete";
	private final static String SEARCH = "search";
	private final static String UNDO = "undo";
	private final static String EDIT = "edit";
	private static final String UPDATE = "update";
	private final static String REDO = "redo";
	private final static String EMAIL = "email";
	private static final String COMPLETED = "completed";
	private final static String ADD_SUCCESSFUL_MESSAGE = "That task has successfully been added to the Task List.\n";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.\n";
	private static final String REDO_SUCCESS_MESSAGE = "Action has successfully been redone.\n";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be undone.\n";
	private static final String REDO_UNSUCCESSFUL_MESSAGE = "There are no actions that can be redone.\n";
	private final static String INVALID_COMMAND_MESSAGE = "That is an invalid command.\n";
	private static final String NO_DETAILS_MESSAGE = "No details detected! This task is not added to the task list\n";
	private static final String INVALID_TIME_MESSAGE = "Time entered is invalid! This task is not added to the task list.\n";

	private static ExeCom theOne;

	public static String getFeedback() {
		return feedback;
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
	 * @author Richard, Joey, Ying Yun, Khaleef
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

		switch (keyWord) {
		case ADD:
			Add add = new Add(getTaskListInstance());
			if (isValidAddCommand()){
				if(isValidTime()){
					ArrayList<Integer> conflicts = new ArrayList<Integer>();
					conflicts = checkConflict();
					if (conflicts.size() <= 0) {
						saveToPrevTaskList();
						add.addToTaskList(command);
						feedback = feedback + ADD_SUCCESSFUL_MESSAGE;
						saveToRedoTaskList();
					}
					else {
						String input = add.handleConflict(command, conflicts);
						if (add.isWantToAdd(input)){
							saveToPrevTaskList();
							add.addToTaskList(command);
							saveToRedoTaskList();
						}
					}
				}else{
					feedback = feedback + INVALID_TIME_MESSAGE;
				}
			}else{
				feedback = feedback + NO_DETAILS_MESSAGE;
			}
			break;

		case DISPLAY:
			Display d = new Display(getTaskListInstance());
			if(isValidUndoRedoDisplayCommand()){
				feedback = feedback + d.displayTaskList();
			}else if(isDisplayCompleted()){
				feedback = feedback + d.displayCompleted();
			}else{
				feedback = feedback + INVALID_COMMAND_MESSAGE;
			}
			break;

		case DELETE:
			saveToPrevTaskList();
			Delete del = new Delete();
			feedback = feedback + del.delete(c);
			saveToRedoTaskList();
			// s.loadStorage(); // to update the taskList
			break;

		case COMPLETED:
			saveToPrevTaskList();
			Completed completed = new Completed();
			feedback = feedback + completed.markCompleted(c);
			saveToRedoTaskList();
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
			Add a = new Add(getTaskListInstance());
			ArrayList<Integer> conflicts = new ArrayList<Integer>();
			conflicts = checkConflict();
			if (conflicts.size() <= 0) {
				saveToPrevTaskList();
				Update u = new Update();
				feedback = feedback + u.editContent(c);
				saveToRedoTaskList();
				break;
			}else{
				a.handleConflict(command, conflicts);
			}

		case EMAIL:
			Email email = new Email(getTaskListInstance());
			email.emailUser();
			break;

		case UNDO:
			undo();
			break;

		case REDO:
			redo();
			break;
		}

		s.saveStorage();
		return feedback;
	}

	/**
	 * isValidTime: check if user entered valid time which is from 0000 t0 2359
	 * 
	 * @author yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidTime() {
		if(isValidHours() && isValidMins()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * isValidhours: check if user entered between 0 to 23 hours
	 * 
	 * @author yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidHours() {	
		if (c.getStartHours()!=null){
			int startHours = Integer.parseInt(c.getStartHours());
			if (0>startHours && startHours>=24){
				return false;
			}
		}

		if (c.getEndHours()!=null){
			int endHours = Integer.parseInt(c.getEndHours());
			if (0>endHours && endHours>=24){
				return false;
			}
		}

		return true;
	}

	/**
	 * isValidMins: check if user entered between 0 to 59 minutes
	 * 
	 * @author yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidMins() {
		if (c.getStartMins()!=null){
			int startMins = Integer.parseInt(c.getStartMins());
			if (0>startMins && startMins>=24){
				return false;
			}
		}

		if (c.getEndMins()!=null){
			int endMins = Integer.parseInt(c.getEndMins());
			if (0>endMins && endMins>=24){
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * isValidAddCommand: Check if user keyed in details (mandatory)
	 * 
	 * @author yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isValidAddCommand() {
		if(c.getDetails()!=null){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * isDisplayCompleted: Check if user wants to display list of completed tasks
	 * 
	 * @author yingyun
	 * @param void
	 * @return boolean
	 * 
	 */
	private boolean isDisplayCompleted() {
		if (c.getDetails().equals("completed") || c.getDetails().equals("completed tasks")){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * isTaskIDMatch: Checks if a task's taskID is equal to the userSpecified
	 * taskIdNumber that he's searching for.
	 * 
	 * @author Richard, yingyun
	 * @param String, int
	 * @return boolean
	 * 
	 */

	public boolean isTaskIDMatch(String specifiedTaskID, int taskIdNumber) {
		return Integer.parseInt(specifiedTaskID) == taskIdNumber;
	}

	/**
	 * retrieveTaskIdNumber: retrieves user-specified taskID. 
	 * 
	 * @author Richard, yingyun
	 * @param String
	 * @return int
	 * 
	 */

	public int retrieveTaskIdNumber(String taskID) {
		return Integer.parseInt(taskID);
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
		if (!prevTaskList.isEmpty() && isValidUndoRedoDisplayCommand()) {
			resetTaskList();
			transferTasksFromTo(prevTaskList, taskList);

			feedback = UNDO_SUCCESS_MESSAGE;
		} else if (isValidUndoRedoDisplayCommand() && prevTaskList.isEmpty()) {
			feedback = UNDO_UNSUCCESSFUL_MESSAGE;
		} else {
			feedback = INVALID_COMMAND_MESSAGE;
		}
	}

	/**
	 * redo: Reperforms any task that was done before undo() was called.
	 * 
	 * @author Richard
	 * @param void
	 * @return void
	 */
	public static void redo() {
		if ((!redoTaskList.isEmpty() && isValidUndoRedoDisplayCommand())
				|| !prevTaskList.isEmpty()) {
			resetTaskList();
			transferTasksFromTo(redoTaskList, taskList);
			/*
			 * for (Task task : redoTaskList) { taskList.add(task); }
			 */
			feedback = REDO_SUCCESS_MESSAGE;
		} else if (isValidUndoRedoDisplayCommand() && redoTaskList.isEmpty()) {
			feedback = REDO_UNSUCCESSFUL_MESSAGE;
		} else {
			feedback = INVALID_COMMAND_MESSAGE;
		}
	}

	/**
	 * checkConflict: check conflict of time and date, return ArrayList<Integer> with the elements
	 * being the indexes of conflicting tasks in tasklist
	 * 
	 * @author Tian Weizhou
	 * @param void
	 * @return ArrayList<Integer>
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

		if(comm.getEndHours()==null) {
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
	public void saveToPrevTaskList() {
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

	public void saveToRedoTaskList() {
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
