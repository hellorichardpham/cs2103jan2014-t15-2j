import java.util.ArrayList;
import java.util.Scanner;

public class ExeCom {
	private static ArrayList<Task> taskList;
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

	public static String executeCommand(String[] userCommandInfo) {
		info = userCommandInfo;
		String command = info[0];
		switch (command) {
		case ADD:
			addToTaskList();
			return " ";
		case DISPLAY:
			display();
			return " ";
		case DELETE:
			delete();
			return " ";
		case SEARCH:
			search();
			return " ";
		case UNDO:
			undo();
			return " ";
		case "print":
			printAllTaskInfo();
			return " ";
		}
		return " ";
	}

	private static void display() {
		if (taskList.size() > 0) {
			for (int i = 0; i < taskList.size(); i++) {
				System.out.println(taskList.get(i).getDetails());
			}
		}
		else {
			System.out.println(TASKLIST_EMPTY_MESSAGE);
		}
	}

	public static void addToTaskList() {
		// Create new instance of a task class to add into the taskList
		Task taskToAdd = new Task(info);
		System.out.println("Created a new blank task");
		/*
		 * Update the previous taskList to the instance before it was changed so
		 * that we can undo the action if we wanted to
		 */
		saveProgress();
		taskList.add(taskToAdd);
		System.out.println("Added to taskList");
		System.out.println("content of last item in taskList: "
				+ taskList.get(taskList.size() - 1).getDetails());
		System.out.println("size of taskList: " + taskList.size());
	}

	public static void delete() {
		// Go Through search results
		Scanner scan = new Scanner(System.in);
		boolean isFound = false;
		// The searchResults attributes now contain the tasks that matched
		// our search
		// Prompt user to input what line they would like to delete like in
		// CE2
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
		boolean isFound = false;
		reinitializeSearchResults();
		// Is this right? I don't think infoString is found in index 0.
		// Definitely not right.
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

	public static void reinitializeSearchResults() {
		searchResults = new ArrayList<Task>();
	}

	// prevTaskList holds all the information of taskList before tL is changed
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

	public static void printAllTaskInfo() {
		for (int i = 0; i < info.length; i++)
			System.out.print(info[i] + " ");
	}
}
