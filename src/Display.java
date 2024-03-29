import java.util.ArrayList;
import java.util.Calendar;

public class Display extends Task {

	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list!\n";
	private static final String NO_RELATED_TASKS_MESSAGE = "There are no related tasks! \n";
	private static final String NO_TASKS_END = "There are no tasks that end in ";
	private static final String NO_TASKS_START = "There are no tasks that start in ";
	private static final String EMPTY_STRING = "";
	private static ArrayList<Task>[][] monthList;
	private static Command command = null;
	private static ArrayList<Task> taskList;
	public static ArrayList<String> months = new ArrayList<String>();
	public static ArrayList<String> days = new ArrayList<String>();
	private static ArrayList<Task> sortedTaskList = new ArrayList<Task>();
	private static ArrayList<Task>[][] startMonthList = new ArrayList[12][1];
	private static ArrayList<Task>[][] endMonthList = new ArrayList[12][1];

	// constructor
	public Display(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public Display(ArrayList<Task> taskList, Command command,
			ArrayList<Task>[][] monthList) {
		initializeMonths();
		initializeDays();
		initializeCalender();
		initializeMonthList();
		this.taskList = taskList;
		this.command = command;
		this.monthList = monthList;
		taskList = this.sort();
	}

	/**
	 * 
	 * display: display all tasks found in the taskList
	 * 
	 * @author A0085107J, A0097961M
	 * @param void
	 * @return void
	 */
	public String displayTaskList() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut += displayUncompleted();
		} else if (taskList.isEmpty()) {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		return dispOut + "\n";
	}

	//@author A0097961M
	/**
	 * 
	 * displayUncompleted: display all uncompleted tasks found in the taskList
	 * 
	 * @param void
	 * @return void
	 */
	public String displayUncompleted() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + printUncompletedListingHeader();
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
	 * @author A0085107J
	 * @param void
	 * @return void
	 */
	public String displayCompleted() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			dispOut = dispOut + printCompletedListingHeader();
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
	 * @author A0085107J
	 * @param void
	 * @return string
	 */
	public String displayDate() {
		String dispOut = "";
		if (!taskList.isEmpty()) {
			if (haveTasksWithMatchingDates()) {
				dispOut = setTasksWithMatchingDates();
			} else {
				dispOut = NO_RELATED_TASKS_MESSAGE;
			}
		} else {
			dispOut = TASKLIST_EMPTY_MESSAGE;
		}
		return dispOut + "\n";
	}

	/**
	 * setTasksWithMatchingDates: collates all related tasks with matching dates
	 * into one string
	 * 
	 * @author A0085107J
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
	 * haveTasksWithMatchingDates: check if taskList has any tasks that is
	 * related to the input date
	 * 
	 * @author A0085107J
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
	 * setTaskToDisplay: Given a single task, it formats the string to sent for
	 * printing including task index and all attributes.
	 * 
	 * @author A0085107J
	 * @param dispOut
	 * @param task
	 * @return String
	 */
	private String setTaskToDisplay(String dispOut, Task task) {
		String info = task.displayTask();
		dispOut = dispOut + printTaskWithIndex(task, info);
		return dispOut;
	}

	/**
	 * displayTaskForToday: Format string to contain all information of tasks that ends today
	 * 
	 * @author A0085107J
	 * @param void
	 * @return String
	 */
	public String displayTaskForToday() {
			String dispOut = "";
			if (!taskList.isEmpty()) {
				dispOut = dispOut + printTaskForTodayHeader();
				for (Task task : taskList) {
					if (isEndingToday(task)) {
						dispOut = setTaskToDisplay(dispOut, task);
					}
				}
			} else {
				dispOut = TASKLIST_EMPTY_MESSAGE;
			}
			return dispOut + "\n";
	}

	//@author A0085107J
	/**
	 * displayHelp: prints out basic information on how to user the program
	 * 
	 * @param void
	 * @return String
	 */
	public String displayHelp() {
		String feedback=EMPTY_STRING;
		feedback += "=====Help====\n"
				+ "List of keywords:\n"
				+ "Add, Delete,  Edit, Search, Display, Deadline, Done, Undo, Redo, "
				+ "Clear, Exit\n \n"

				+ "Possible tasks attributes:\n"
				+ "Time, Date, Priority, Location, Category\n \n"

				+ "List of possible shortcuts:\n"
				+ "Add: a, ad, create, cr8\n"
				+ "Delete: d, del, remove, rmv, rem, rm,\n"
				+ "Edit: e, ed, update, upd, ud, change, chge\n"
				+ "Search: s,srh, find, fd\n"
				+ "Displayd: dm, dispm, displaym, deadline, dd, dl, ends, end\n"
				+ "Display: d, dis, disp, show, shw, view, v\n"
				+ "Undo: u, un, und\n"
				+ "Redo: r, re, rd\n"
				+ "Clear: c, cl, clr\n"
				+ "Help: h, hp, hlp, ?, ??\n"
				+ "Email: em, send, sd\n"
				+ "Exit: ex, quit, qt, q\n \n"

				+ "Adding Format:\n"
				+ "    1. Details are necessary.\n"
				+ "    2. Input time should have 24-hour format and have 'hrs' e.g 2359hrs\n"
				+ "    3. Put \"//\" before location, priority and category. They can be represented by their first letter\n"
				+ "       e.g //priority high or //p high\n"
				+ "    4. For location, can replace \"//\" with \"@\" \n"
				+ "       e.g @school\n"
;
		return feedback;
	}

	/**
	 * displayTaskForToday: checks if a particular task's end date is today
	 * 
	 * @author A0085107J
	 * @param Task
	 * @return boolean
	 */
	private boolean isEndingToday(Task task) {
		Calendar cal = Calendar.getInstance();
		String currentDay = cal.get(Calendar.DAY_OF_MONTH) + "";
		String currentMonth = cal.get(Calendar.MONTH) + 1 + "";
		String currentYear = cal.get(Calendar.YEAR) + "";
		if (task.getEndDay().equals(currentDay) && task.getEndMonth().equals(currentMonth) 
				&& task.getEndYear().equals(currentYear))
			return true;
		else {
			return false;
		}
	}
	
	//@author A0118590A
	/**
	 * displayStartMonth: Displays the contents
	 *  
	 * @param void
	 * 
	 * @return void
	 */
	public String displayStartMonth() {
		String month = command.getEndMonth();
		switch (month) {
		case "01":
			return printStartMonthList(0);
		case "02":
			return printStartMonthList(1);
		case "03":
			return printStartMonthList(2);
		case "04":
			return printStartMonthList(3);
		case "05":
			return printStartMonthList(4);
		case "06":
			return printStartMonthList(5);
		case "07":
			return printStartMonthList(6);
		case "08":
			return printStartMonthList(7);
		case "09":
			return printStartMonthList(8);
		case "10":
			return printStartMonthList(9);
		case "11":
			return printStartMonthList(10);
		case "12":
			return printStartMonthList(11);
		}
		return "invalid month";
	}
	
	//@author A0118590A
	/**
	 * displayEndMonth: Displays the contents of the ArrayList that end in a
	 * particular month
	 * 
	 * @param void
	 * 
	 * @return String
	 */
	public String displayEndMonth() {
		String month = command.getEndMonth();
		switch (month) {
		case "01":
			return printEndMonthList(0);
		case "02":
			return printEndMonthList(1);
		case "03":
			return printEndMonthList(2);
		case "04":
			return printEndMonthList(3);
		case "05":
			return printEndMonthList(4);
		case "06":
			return printEndMonthList(5);
		case "07":
			return printEndMonthList(6);
		case "08":
			return printEndMonthList(7);
		case "09":
			return printEndMonthList(8);
		case "10":
			return printEndMonthList(9);
		case "11":
			return printEndMonthList(10);
		case "12":
			return printEndMonthList(11);
		}
		return "invalid month";
	}


	/**
	 * printCompletedListingHeader: print header for completed listing
	 * 
	 * @author A0085107J
	 * @param void
	 * @return string
	 */
	private String printCompletedListingHeader() {
		return "== Listing of completed tasks =====\n";
	}

	/**
	 * printTaskForTodayHeader: print header for today's tasks
	 * 
	 * @author A0085107J
	 * @param void
	 * @return string
	 */
	private String printTaskForTodayHeader() {
		
		return "== Today's Tasks =====\n";
	}

	/**
	 * printUncompletedListingHeader: print header for uncompleted listing
	 * 
	 * @author A0085107J
	 * @param void
	 * @return String
	 */
	private String printUncompletedListingHeader() {
		return "== Listing of pending tasks =====\n";
	}

	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author A0085107J
	 * @param Task
	 *            , String
	 * @return String
	 */
	private static String printTaskWithIndex(Task task, String print) {
		return (taskList.indexOf(task) + 1) + ": " + print + "\n";
	}

	/**
	 * isMatchingStartDate: checks if the command's start date is same as task's
	 * start date
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartDate(Task task) {
		if (isMatchingStartDay(task) && isMatchingStartMonth(task)
				&& isMatchingStartYear(task)) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * isMatchingStartDay: checks if the command's start day is same as task's
	 * start day
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartDay(Task task) {
		if (task.getStartDay().equals(command.getStartDay())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingStartMonth: checks if the command's start month is same as
	 * task's start month
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartMonth(Task task) {
		if (task.getStartMonth().equals(command.getStartMonth())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingStartYear: checks if the command's start year is same as task's
	 * start year
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingStartYear(Task task) {
		if (task.getStartYear().equals(command.getStartYear())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingEndDate: checks if the command's end date is same as task's end
	 * date (including day,month and year)
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndDate(Task task) {
		if (isMatchingEndDay(task) && isMatchingEndMonth(task)
				&& isMatchingEndYear(task)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingEndDay: checks if the command's end day is same as task's end
	 * day
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndDay(Task task) {
		if (task.getEndDay().equals(command.getEndDay())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingEndMonth: checks if the command's end month is same as task's
	 * end month
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndMonth(Task task) {
		if (task.getEndMonth().equals(command.getEndMonth())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * isMatchingEndYear: checks if the command's end year is same as task's end
	 * year
	 * 
	 * @author A0085107J
	 * @param task
	 * @return boolean
	 */
	private boolean isMatchingEndYear(Task task) {
		if (task.getEndYear().equals(command.getEndYear())) {
			return true;
		} else {
			return false;
		}
	}

	//@author A0118590A
	/*
	 * printSorted: Displays the entire taskList in descending order based on
	 * start month
	 * 
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	public static void printSorted() {
		for (Task task : sortedTaskList) {
			System.out.println(task.displayTask());
		}
	}
	
	//@author A0118590A
	/*
	 * printStartMonthList: Displays all tasks that begin in the specified month
	 *  
	 * @param int
	 * 
	 * @return String
	 */
	public String printStartMonthList(int monthIndex) {
		boolean isFound = false;
		String output = printStartMonthName(monthIndex);
		for (int j = 0; j < startMonthList[monthIndex][0].size(); j++) {
			isFound = true;
			String print = startMonthList[monthIndex][0].get(j).displayTask();
			output += printTaskWithIndex(startMonthList[monthIndex][0].get(j),
					print);
		}
		if (isFound) {
			return output + "\n";
		} else {
			return NO_TASKS_START + printMonthName(monthIndex) + "\n";
		}
	}

	//@author A0118590A
	/*
	 * printStartMonthList: Displays all tasks that end in the specified month
     * 
	 * @param int
	 * 
	 * @return String
	 */
	public static String printEndMonthList(int monthIndex) {
		boolean isFound = false;
		String output = printEndMonthName(monthIndex);
		for (int j = 0; j < endMonthList[monthIndex][0].size(); j++) {
			isFound = true;
			String print = endMonthList[monthIndex][0].get(j).displayTask();
			output += printTaskWithIndex(endMonthList[monthIndex][0].get(j),
					print);
		}
		if (isFound) {
			return output + "\n";
		} else {
			return NO_TASKS_END + printMonthName(monthIndex) + "\n";
		}

	}
	
	//@author A0118590A
	/*
	 * sort: Sorts tasks into two lists based on start/end month
	 * 
	 * 
	 * @param void
	 * 
	 * @return ArrayList<Task>
	 */
	public ArrayList<Task> sort() {
		for (int i = 0; i < months.size() - 1; i++) {
			for (int j = 0; j < taskList.size(); j++) {
				if (isMatchingTaskStartMonth(taskList.get(j), months.get(i))) {
					startMonthList[i][0].add(taskList.get(j));
					sortedTaskList.add(taskList.get(j));
				}
				if (isMatchingTaskEndMonth(taskList.get(j), months.get(i))) {
					endMonthList[i][0].add(taskList.get(j));
				}
			}
		}
		return sortedTaskList;
	}

	//@author A0118590A
	/*
	 * isMatchingTaskStartMonth: Returns True/False depending on if a task
	 * starts in the specified month
	 * 
	 * @param Task, String
	 * 
	 * @return boolean
	 */
	public static boolean isMatchingTaskStartMonth(Task task, String month) {
		if (!task.getStartMonth().equals("null")) {
			return month.contains(task.getStartMonth());
		} else {
			return false;
		}
	}

	//@author A0118590A
	/*
	 * isMatchingTaskEndMonth: Returns True/False depending on if a task ends in
	 * the specified month
	 * 
	 * 
	 * @param Task, String
	 * 
	 * @return boolean
	 */
	public static boolean isMatchingTaskEndMonth(Task task, String month) {
		return month.contains(task.getEndMonth());
	}
	
	//@author A0118590A
	/*
	 * initializeMonths: Resets months variable and adds the number that
	 * correlates to each month. (Jan == 01). Note that it contains both because
	 * the input may come as one or two digits
	 * 
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	public static void initializeMonths() {
		months = new ArrayList<String>(); // resets months so no overflow
		/*
		 * Each time we add single digits, we add 1 and 01 because the user
		 * might enter may 01 2014 instead. This allows us to catch both. We may
		 * want to force the month to be a certain way so we don't have to do
		 * this.
		 */
		months.add("1 01");
		months.add("2 02");
		months.add("3 03");
		months.add("4 04");
		months.add("5 05");
		months.add("6 06");
		months.add("7 07");
		months.add("8 08");
		months.add("9 09");
		months.add("10");
		months.add("11");
		months.add("12");
	}

	//@author A0118590A
	/*
	 * printStartMonthName: Prints the display header depending on which month
	 * we are about to display
	 * 
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public static String printStartMonthName(int monthIndex) {
		switch (monthIndex) {
		case 0:
			return "Displaying all tasks that start in January: \n";
		case 1:
			return "Displaying all tasks that start in February: \n";
		case 2:
			return "Displaying all tasks that start in March: \n";
		case 3:
			return "Displaying all tasks that start in April: \n";
		case 4:
			return "Displaying all tasks that start in May: \n";
		case 5:
			return "Displaying all tasks that start in June: \n";
		case 6:
			return "Displaying all tasks that start in July: \n";
		case 7:
			return "Displaying all tasks that start in August: \n";
		case 8:
			return "Displaying all tasks that start in September: \n";
		case 9:
			return "Displaying all tasks that start in October: \n";
		case 10:
			return "Displaying all tasks that start in November: \n";
		case 11:
			return "Displaying all tasks that start in December: \n";
		}
		return "";
	}

	//@author A0118590A
	/*
	 * printEndMonthName: Prints the display header depending on which month we
	 * are about to display
	 * 
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public static String printEndMonthName(int monthIndex) {
		switch (monthIndex) {
		case 0:
			return "Displaying all tasks that end in January: \n";
		case 1:
			return "Displaying all tasks that end in February: \n";
		case 2:
			return "Displaying all tasks that end in March: \n";
		case 3:
			return "Displaying all tasks that end in April: \n";
		case 4:
			return "Displaying all tasks that end in May: \n";
		case 5:
			return "Displaying all tasks that end in June: \n";
		case 6:
			return "Displaying all tasks that end in July: \n";
		case 7:
			return "Displaying all tasks that end in August: \n";
		case 8:
			return "Displaying all tasks that end in September: \n";
		case 9:
			return "Displaying all tasks that end in October: \n";
		case 10:
			return "Displaying all tasks that end in November: \n";
		case 11:
			return "Displaying all tasks that end in December: \n";
		}
		return "";
	}

	//@author A0118590A
	/*
	 * printMonthName: Prints the name of the month only. Used for display
	 * purposes
	 * 
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public static String printMonthName(int monthIndex) {
		switch (monthIndex) {
		case 0:
			return "January \n";
		case 1:
			return "February \n";
		case 2:
			return "March \n";
		case 3:
			return "April \n";
		case 4:
			return "May \n";
		case 5:
			return "June \n";
		case 6:
			return "July \n";
		case 7:
			return "August \n";
		case 8:
			return "September \n";
		case 9:
			return "October \n";
		case 10:
			return "November \n";
		case 11:
			return "December \n";
		}
		return "";
	}

	//@author A0118590A
	/*
	 * initializeDays: Initializes days ArrayList with each numerical day from
	 * 1-31
	 * 
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	public static void initializeDays() {
		days.add("1");
		days.add("2");
		days.add("3");
		days.add("4");
		days.add("5");
		days.add("6");
		days.add("7");
		days.add("8");
		days.add("9");
		days.add("10");
		days.add("11");
		days.add("12");
		days.add("13");
		days.add("14");
		days.add("15");
		days.add("16");
		days.add("17");
		days.add("18");
		days.add("19");
		days.add("20");
		days.add("21");
		days.add("22");
		days.add("23");
		days.add("24");
		days.add("25");
		days.add("26");
		days.add("27");
		days.add("28");
		days.add("29");
		days.add("30");
		days.add("31");
	}

	//@author A0118590A
	/*
	 * initializeCalender: Initializes start and end Month array
	 * 
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	public static void initializeCalender() {
		for (int i = 0; i < months.size(); i++) {
			startMonthList[i][0] = new ArrayList<Task>();
			endMonthList[i][0] = new ArrayList<Task>();
		}
	}

	//@author A0118590A
	/*
	 * initializeMonthList: sets new ArrayLists for the 2D Arrays holding the
	 * start/end month results
	 * 
	 * 
	 * @param void
	 * 
	 * @return void
	 */
	public static void initializeMonthList() {
		for (int i = 0; i < 12; i++) {
			startMonthList[i][0] = new ArrayList<Task>();
			endMonthList[i][0] = new ArrayList<Task>();
		}
	}
}// end class
