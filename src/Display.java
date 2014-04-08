import java.util.ArrayList;

public class Display extends Task {

	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list!\n";
	private static final String NO_RELATED_TASKS_MESSAGE = "There are no related tasks!";
	private static final CharSequence EMPTY_STRING = "";
	private static ArrayList<Task>[][] monthList;
	private static Command command = null;
	private ArrayList<Task> taskList;
	SortDate sorted;

	// constructor
	public Display(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public Display(ArrayList<Task> taskList, Command command,
			ArrayList<Task>[][] monthList) {
		this.taskList = taskList;
		this.command = command;
		this.monthList = monthList;
		sorted = new SortDate(taskList);
		taskList = sorted.sort();
	}

	/**
	 * 
	 * display: display all tasks found in the taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public String displayTaskList() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + printListingHeader();
			for (Task task : taskList) {
				dispOut = setTaskToDisplay(dispOut, task);
			}
		} else if (taskList.isEmpty()) {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		return dispOut + "\n";
	}

	/**
	 * 
	 * display: display all uncompleted tasks found in the taskList
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public String displayUncompleted() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + printListingHeader();
			for (Task task : taskList) {
				if (task.isCompleted() == false) {
					dispOut = setTaskToDisplay(dispOut, task);
				}
			}
		} else if (taskList.isEmpty()) {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		if (dispOut.equals(null) || dispOut.isEmpty() || dispOut.equals(""))
			return dispOut = TASKLIST_EMPTY_MESSAGE;
		else
			return dispOut + "\n";
	}

	/**
	 * displayCompleted: prints out all completed tasks in taskList
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return void
	 */
	public String displayCompleted() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + "The following tasks are completed: \n";
			for (Task task : taskList) {
				if (task.isCompleted()) {
					dispOut = setTaskToDisplay(dispOut, task);
				}
			}
		} else {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		return dispOut + "\n";
	}

	
	/**
	 * displayDate: display all tasks that matches input date, if any.
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return string
	 */
	public String displayDate() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			if(haveTasksWithMatchingDates()){
				dispOut = setTasksWithMatchingDates();
			}else{
				dispOut = NO_RELATED_TASKS_MESSAGE;
			}
		}else {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		return dispOut + "\n";
	}

	/**
	 * setTasksWithMatchingDates: collates all related tasks with matching dates into one string
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return string
	 */
	private String setTasksWithMatchingDates() {
		String dispOut = "";
		dispOut = dispOut + "The following tasks are related to input date: \n";
		for (Task task : taskList) {
			if (isMatchingStartDate(task) || isMatchingEndDate(task)) {
				dispOut = setTaskToDisplay(dispOut, task);
			}
		}
		return dispOut;
	}

	/**
	 * haveTasksWithMatchingDates: check if taskList has any tasks that is related to the input date
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return boolean
	 */
	private boolean haveTasksWithMatchingDates() {
		for (Task task : taskList) {
			if (isMatchingStartDate(task) || isMatchingEndDate(task)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * setTaskToDisplay: Given a single task, it formats the string to sent for printing 
	 * including task index and all attributes.
	 * 
	 * @author yingyun
	 * @param dispOut
	 * @param task
	 * @return String
	 */
	private String setTaskToDisplay(String dispOut, Task task) {
		String info = task.displayTask();
		dispOut = dispOut + printTaskWithIndex(task, info);
		return dispOut;
	}

	public String displayStartMonth() {
		String month = command.getEndMonth();
		switch (month) {
		case "01":
			return sorted.printStartMonthList(0);
		case "02":
			return sorted.printStartMonthList(1);
		case "03":
			return sorted.printStartMonthList(2);
		case "04":
			return sorted.printStartMonthList(3);
		case "05":
			return sorted.printStartMonthList(4);
		case "06":
			return sorted.printStartMonthList(5);
		case "07":
			return sorted.printStartMonthList(6);
		case "08":
			return sorted.printStartMonthList(7);
		case "09":
			return sorted.printStartMonthList(8);
		case "10":
			return sorted.printStartMonthList(9);
		case "11":
			return sorted.printStartMonthList(10);
		case "12":
			return sorted.printStartMonthList(11);
		}
		return "invalid month";
	}

	public String displayEndMonth() {
		String month = command.getEndMonth();
		switch (month) {
		case "01":
			return sorted.printEndMonthList(0);
		case "02":
			return sorted.printEndMonthList(1);
		case "03":
			return sorted.printEndMonthList(2);
		case "04":
			return sorted.printEndMonthList(3);
		case "05":
			return sorted.printEndMonthList(4);
		case "06":
			return sorted.printEndMonthList(5);
		case "07":
			return sorted.printEndMonthList(6);
		case "08":
			return sorted.printEndMonthList(7);
		case "09":
			return sorted.printEndMonthList(8);
		case "10":
			return sorted.printEndMonthList(9);
		case "11":
			return sorted.printEndMonthList(10);
		case "12":
			return sorted.printEndMonthList(11);
		}
		return "invalid month";
	}

	/**
	 * printListingHeader: print header for listing
	 * 
	 * @author Ying Yun
	 * @param void
	 * @return void
	 */
	private String printListingHeader() {
		return "~~~~~ Listing of all tasks ~~~~~\n";
	}

	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author Ying Yun
	 * @param Task
	 *            , String
	 * @return void
	 */
	private String printTaskWithIndex(Task task, String print) {
		return (taskList.indexOf(task) + 1) + ": " + print + "\n";
	}

	/**
	 * isMatchingStartDate: checks if the command's start date is same as task's start date
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartDate(Task task) {
		if (isMatchingStartDay(task) && 
				isMatchingStartMonth(task) && 
				isMatchingStartYear(task)){
			return true;
		}else {
			return false;

		}
	}

	/**
	 * isMatchingStartDay: checks if the command's start day is same as task's start day
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartDay(Task task) {
		if (task.getStartDay().equals(command.getStartDay())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingStartMonth: checks if the command's start month is same as task's start month
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartMonth(Task task) {
		if (task.getStartMonth().equals(command.getStartMonth())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingStartYear: checks if the command's start year is same as task's start year
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartYear(Task task) {
		if (task.getStartYear().equals(command.getStartYear())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingEndDate: checks if the command's end date is same as task's end date (including day,month and year)
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndDate(Task task) {
		if (isMatchingEndDay(task) && 	isMatchingEndMonth(task) && isMatchingEndYear(task)){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingEndDay: checks if the command's end day is same as task's end day
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndDay(Task task) {
		if (task.getEndDay().equals(command.getEndDay())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingEndMonth: checks if the command's end month is same as task's end month
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndMonth(Task task) {
		if (task.getEndMonth().equals(command.getEndMonth())){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * isMatchingEndYear: checks if the command's end year is same as task's end year
	 * 
	 * @author yingyun
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndYear(Task task) {
		if (task.getEndYear().equals(command.getEndYear())){
			return true;
		}else {
			return false;
		}
	}
}// end class
