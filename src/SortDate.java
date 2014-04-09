import java.util.ArrayList;

public class SortDate {
	public static ArrayList<String> months = new ArrayList<String>();
	public static ArrayList<String> days = new ArrayList<String>();
	private static ArrayList<Task> sortedTaskList = new ArrayList<Task>();
	private static ArrayList<Task>[][] startMonthList = new ArrayList[12][1];
	private static ArrayList<Task>[][] endMonthList = new ArrayList[12][1];
	private static ArrayList<Task> taskList;

	// constructor
	public SortDate(ArrayList<Task> taskList) {
		initializeMonths();
		initializeDays();
		initializeCalender();
		initializeMonthList();
		this.taskList = taskList;
	}

	/*
	 * initializeMonthList: sets new ArrayLists for the 2D Arrays holding the
	 * start/end month results
	 * 
	 * @author A0118590A
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

	/*
	 * printSorted: Displays the entire taskList in descending order based on
	 * start month
	 * 
	 * @author A0118590A
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

	/*
	 * printStartMonthList: Displays all tasks that begin in the specified month
	 * 
	 * @author A0118590A
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public String printStartMonthList(int monthIndex) {
		String output = printStartMonthName(monthIndex);
		for (int j = 0; j < startMonthList[monthIndex][0].size(); j++) {
			String print = startMonthList[monthIndex][0].get(j).displayTask();
			output += printTaskWithIndex(startMonthList[monthIndex][0].get(j),
					print);
		}
		// New Line added to separate display from next command's feedback
		return output + "\n";
	}

	/*
	 * printStartMonthList: Displays all tasks that end in the specified month
	 * 
	 * @author A0118590A
	 * 
	 * @param int
	 * 
	 * @return String
	 */
	public String printEndMonthList(int monthIndex) {
		String output = printEndMonthName(monthIndex);
		for (int j = 0; j < endMonthList[monthIndex][0].size(); j++) {
			String print = endMonthList[monthIndex][0].get(j).displayTask();
			output += printTaskWithIndex(endMonthList[monthIndex][0].get(j),
					print);
		}
		// New Line added to separate display from next command's feedback
		return output + "\n";
	}

	/*
	 * sort: Sorts tasks into two lists based on start/end month
	 * 
	 * @author A0118590A
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

	/*
	 * isMatchingTaskStartMonth: Returns True/False depending on if a task
	 * starts in the specified month
	 * 
	 * @author A0118590A
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

	/*
	 * isMatchingTaskEndMonth: Returns True/False depending on if a task ends in
	 * the specified month
	 * 
	 * @author A0118590A
	 * 
	 * @param Task, String
	 * 
	 * @return boolean
	 */
	public static boolean isMatchingTaskEndMonth(Task task, String month) {
		return month.contains(task.getEndMonth());
	}

	/*
	 * initializeMonths: Resets months variable and adds the number that
	 * correlates to each month. (Jan == 01). Note that it contains both because
	 * the input may come as one or two digits
	 * 
	 * @author A0118590A
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

	/*
	 * printStartMonthName: Prints the display header depending on which month
	 * we are about to display
	 * 
	 * @author A0118590A
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

	/*
	 * printEndMonthName: Prints the display header depending on which month we
	 * are about to display
	 * 
	 * @author A0118590A
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

	/*
	 * initializeDays: Initializes days ArrayList with each numerical day from
	 * 1-31
	 * 
	 * @author A0118590A
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

	/*
	 * initializeCalender: Initializes start and end Month array
	 * 
	 * @author A0118590A
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

	/**
	 * 
	 * printTaskIndex: print index number of current task
	 * 
	 * @author A0085107J
	 * @param Task, String
	 * @return void
	 */
	private static String printTaskWithIndex(Task task, String print) {
		return (taskList.indexOf(task) + 1) + ": " + print + "\n";
	}
}