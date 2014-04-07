import java.util.ArrayList;

public class SortDate {
	public static ArrayList<String> months = new ArrayList<String>();
	public static ArrayList<String> days = new ArrayList<String>();
	private static ArrayList<Task> sortedTaskList = new ArrayList<Task>();
	private static ArrayList<Task>[][] monthList = new ArrayList[12][1];
	private static ArrayList<Task> taskList;

	// constructor
	public SortDate(ArrayList<Task> taskList) {
		initializeMonths();
		initializeDays();
		initializeCalender();
		initializeMonthList();
		this.taskList = taskList;
	}

	public static void initializeMonthList() {
		for (int i = 0; i < 12; i++) {
			monthList[i][0] = new ArrayList<Task>();
		}
	}

	public static void printSorted() {
		for (Task task : sortedTaskList) {
			System.out.println(task.displayTask());
		}
	}

	public static String printMonthList(int monthIndex) {
		String output = printMonthName(monthIndex);
		for (int j = 0; j < monthList[monthIndex][0].size(); j++) {
			String print = monthList[monthIndex][0].get(j).displayTask();
			output += printTaskWithIndex(monthList[monthIndex][0].get(j), print);
		}
		// New Line added to separate display from next command's feedback
		return output + "\n";
	}

	public static ArrayList<Task> sort() {
		for (int i = 0; i < months.size() - 1; i++) {
			for (int j = 0; j < taskList.size(); j++) {
				if (isMatchingTaskMonth(taskList.get(j), months.get(i))) {
					monthList[i][0].add(taskList.get(j));
					sortedTaskList.add(taskList.get(j));
				}
			}
		}
		return sortedTaskList;
	}

	public static boolean isMatchingTaskMonth(Task task, String month) {
		if (!task.getStartMonth().equals("null")) {
			System.out.println("not equal to null");
			return month.contains(task.getStartMonth());
		} else {
			System.out.println("this should be null: " + task.getStartMonth());
			return month.contains(task.getEndMonth());
		}

	}

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

	public static String printMonthName(int monthIndex) {
		switch (monthIndex) {
		case 0:
			return "Displaying all tasks in January: \n";
		case 1:
			return "Displaying all tasks in February: \n";
		case 2:
			return "Displaying all tasks in March: \n";
		case 3:
			return "Displaying all tasks in April: \n";
		case 4:
			return "Displaying all tasks in May: \n";
		case 5:
			return "Displaying all tasks in June: \n";
		case 6:
			return "Displaying all tasks in July: \n";
		case 7:
			return "Displaying all tasks in August: \n";
		case 8:
			return "Displaying all tasks in September: \n";
		case 9:
			return "Displaying all tasks in October: \n";
		case 10:
			return "Displaying all tasks in November: \n";
		case 11:
			return "Displaying all tasks in December: \n";
		}
		return "";
	}

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

	public static void initializeCalender() {
		for (int i = 0; i < months.size(); i++) {
			monthList[i][0] = new ArrayList<Task>();
		}
	}

	private static String printTaskWithIndex(Task task, String print) {
		return (taskList.indexOf(task) + 1) + ": " + print + "\n";
	}
}