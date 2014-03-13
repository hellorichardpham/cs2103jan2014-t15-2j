import java.util.ArrayList;
import java.util.Scanner;

public class ExeCom {
	static ArrayList<Task> taskList;
	private static ArrayList<Task> prevTaskList;
	private static ArrayList<Task> searchResults;
	private static String[] info;
	private final static String ADD = "add";
	private final static String DISPLAY = "display";
	private final static String DELETE = "delete";
	private final static String SEARCH = "search";
	private final static String UNDO = "undo";
	private final static String UNDO_SUCCESS_MESSAGE = "Action has successfully been undone.";
	private final static String UNDO_UNSUCCESSFUL_MESSAGE = "Cannot perform undo on consecutive actions";
	private final static String PROMPT_USER_DELETE_MESSAGE = "Select which task you would like to delete based on its Task ID Number.";
	private final static String TASK_NOT_FOUND_MESSAGE = "That task could not be found.";
	private final static String TASKID_NOT_FOUND_MESSAGE = "That Task ID Number was not found";
	private final static String TASKLIST_EMPTY_MESSAGE = "There are no tasks in the task list.";

	ExeCom() {
		taskList = new ArrayList<Task>();
		prevTaskList = new ArrayList<Task>();

	}

	public static String executeCommand(String[] userCommandInfo) throws Exception {
		info = userCommandInfo;
		String command = info[0];
		Storage s = new Storage();
		switch (command) {
		case ADD:
			addToTaskList();
			s.saveStorage();
			return " ";
		case DISPLAY:
			display();
			return " ";
		case DELETE:
			delete();
			s.saveStorage();
			return " ";
		case SEARCH:
			search();
			return " ";
		case UNDO:
			undo();
			s.saveStorage();
			return " ";
		case "print":
			printAllTaskInfo();
			return " ";
		}
		return " ";
	}

	private static void display() {
		if (!taskList.isEmpty()) {
			System.out.println("~~~~~ Listing of all tasks ~~~~~");
			for (int i = 0; i < taskList.size(); i++) {
				String print;
				print = (i+1) + ")" + " " + taskList.get(i).getAll();
				print = print.replace("null ", "");
				System.out.println(print);
			}
		} else {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		}
	}


	public static void addToTaskList() {
		Task taskToAdd = new Task(info);
		saveProgress();
		taskList.add(taskToAdd);
		System.out.println("Added to taskList");
		System.out.println("content of last item in taskList: "
				+ taskList.get(taskList.size() - 1).getDetails());
		System.out.println("size of taskList: " + taskList.size());
	}

	public static void delete() {
		Scanner scan = new Scanner(System.in);
		boolean isFound = false;
		System.out.println(PROMPT_USER_DELETE_MESSAGE);
		int taskIdNumber = scan.nextInt();
		
		for (int i = 0; i < searchResults.size(); i++) {
			if (searchResults.get(i).getTaskID() == taskIdNumber) {
				Task taskToDelete = searchResults.get(i);
				saveProgress();
				taskList.remove(taskToDelete);
				System.out.println("Deleted: " + taskToDelete.getDetails());
				isFound = true;
			}
		}

		if (!isFound) {
			System.out.println(TASKID_NOT_FOUND_MESSAGE);
		}
		scan.close();
	}

	public static void printSearch() {
		if (!searchResults.isEmpty()) {
			for (int i = 0; i < searchResults.size(); i++) {
				System.out.println(searchResults.get(i).getTaskID() + " "
						+ searchResults.get(i).getDetails());
			}
		} else {
			System.out.println(TASK_NOT_FOUND_MESSAGE);
		}
	}

	public static void displayAll(ArrayList<Task> taskList) {
		for (int counter = 0; counter < taskList.size(); counter++) {
			System.out.println(taskList.get(counter).getDetails());
		}
	}

	public static void search() {
		if (info[1] != null) {
			boolean isFound = false;
			reinitializeSearchResults();
			String infoString = info[1];
			for (int counter = 0; counter < taskList.size(); counter++) {
				if (taskList.get(counter).getDetails().contains(infoString)) {
					searchResults.add(taskList.get(counter));
					isFound = true;
				}
			}
			if (isFound) {
				printSearch();
				delete();
			} else {
				System.out.println(TASK_NOT_FOUND_MESSAGE);
			}
		}
		else {
			System.out.println("That is an Invalid Command.");
		}
	}

	public static void reinitializeSearchResults() {
		searchResults = new ArrayList<Task>();
	}

	
	public static void saveProgress() {
		reinitializePrevTaskList();
		for (int i = 0; i < taskList.size(); i++) {
			prevTaskList.add(taskList.get(i));
		}
	}

	public static void reinitializePrevTaskList() {
		prevTaskList = new ArrayList<Task>();
	}

	public static void reinitializeTaskList() {
		taskList = new ArrayList<Task>();
	}

	// This needs to be changed so that tL holds pTL data
	public static void undo() {
		reinitializeTaskList();
		System.out.println("taskList has been reinitialized");
		for (int counter = 0; counter < prevTaskList.size(); counter++) {
			taskList.add(prevTaskList.get(counter));
			System.out.println("added to taskList: "
					+ prevTaskList.get(counter));
		}
		
	}
	
	public void editContent(String[] info) {
		int id = Integer.parseInt(info[15]);
		// Task task = new Task();
		int counter;
		for (counter = 1; counter < info.length; counter++) {
			System.out.println("Counter: " + counter + " " + info[counter]);
			if (info[counter] != "" || info[counter] != null) {
				switch (counter) {
				case 1:
					taskList.get(id).setDetails(info[counter]);
					break;
				case 2:
					taskList.get(id).setStartDay(info[counter]);
					break;
				case 3:
					taskList.get(id).setStartMonth(info[counter]);
					break;
				case 4:
					taskList.get(id).setStartYear(info[counter]);
					break;
				case 5:
					taskList.get(id).setEndDay(info[counter]);
					break;
				case 6:
					taskList.get(id).setEndMonth(info[counter]);
					break;
				case 7:
					taskList.get(id).setEndYear(info[counter]);
					break;
				case 8:
					taskList.get(id).setStartHours(info[counter]);
					break;
				case 9:
					taskList.get(id).setStartMin(info[counter]);
					break;
				case 10:
					taskList.get(id).setEndHours(info[counter]);
					break;
				case 11:
					taskList.get(id).setEndMins(info[counter]);
					break;
				case 12:
					taskList.get(id).setLocation(info[counter]);
					break;
				case 13:
					taskList.get(id).setLocation(info[counter]);
					break;
				case 14:
					taskList.get(id).setPriority(info[counter]);
					break;
				case 15:
					taskList.get(id).setCategory(info[counter]);
					break;
				default:
					// invalid message
				}
			}
		}
	}
}
