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
		String output = "";
			for (int j = 0; j < monthList[monthIndex][0].size(); j++) {
				output += monthList[monthIndex][0].get(j).displayTask() + "\n";
			}
			return output;
	}

	public static ArrayList<Task> sort() {
		for (int i = 0; i < months.size() - 1; i++) {
			for (int j = 0; j < taskList.size(); j++) {
				if (taskList.get(j).getStartMonth().equals(months.get(i))) {
					monthList[i][0].add(taskList.get(j));
					sortedTaskList.add(taskList.get(j));
				}
			}
		}
		return sortedTaskList;
	}

	public static void initializeMonths() {
		months = new ArrayList<String>(); //resets months so no overflow
		months.add("01");
		months.add("02");
		months.add("03");
		months.add("04");
		months.add("05");
		months.add("06");
		months.add("07");
		months.add("08");
		months.add("09");
		months.add("10");
		months.add("11");
		months.add("12");
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

}