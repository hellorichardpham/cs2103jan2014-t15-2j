//Test

import java.util.ArrayList;

public class executeCommand {

	static ArrayList<Task> taskList = new ArrayList<Task>();
	ArrayList<Task> prevTaskList = new ArrayList<Task>();
	ArrayList<String> info = new ArrayList<String>();
	static ArrayList<Task> searchResults = new ArrayList<Task>();

	public void editContent(String[] info) {
		//int id;
		//id = Integer.parseInt(info[counter]);
		// Task task = new Task();
		int counter;
		for (counter = 1; counter < info.length; counter++) {
			if (info[counter] != null || info[counter] != "") {
		        switch (counter) {
		        case 1:
		        	taskList.get(counter).setDetails(info[counter]);
	            case 2:
	            	taskList.get(counter).setStartDay(info[counter]);
	            case 3:
	            	taskList.get(counter).setStartMonth(info[counter]);
	            case 4:
	            	taskList.get(counter).setStartYear(info[counter]);
	            case 5:
	            	taskList.get(counter).setEndDay(info[counter]);
	            case 6:
	            	taskList.get(counter).setEndMonth(info[counter]);
	            case 7:
	            	taskList.get(counter).setEndYear(info[counter]);
	            case 8:
	            	taskList.get(counter).setStartHours(info[counter]);
	            case 9:
	            	taskList.get(counter).setStartMin(info[counter]);
	            case 10:
	            	taskList.get(counter).setEndHours(info[counter]);
	            case 11:
	            	taskList.get(counter).setEndMins(info[counter]);
	            case 12:
	            	taskList.get(counter).setLocation(info[counter]);
	            case 13:
	            	taskList.get(counter).setLocation(info[counter]);
	            case 14:
	            	taskList.get(counter).setPriority(info[counter]);
	            case 15:
	            	taskList.get(counter).setCategory(info[counter]);
	            default: 
	                //invalid message
		        }
			}
		}
	}

	public void displayAll(ArrayList<Task> taskList) {
		int counter;
		for (counter = 0; counter < taskList.size(); counter++) {
			System.out.println(taskList.get(counter));
		}
	}

	public static ArrayList<Task> search(ArrayList<String> info) {

		searchResults = new ArrayList<Task>();

		String details;
		details = info.get(0).toString();
		int counter;
		// Task task = new Task();
		for (counter = 0; counter < taskList.size(); counter++) {
			if (taskList.get(counter).getDetails().contains(details)) {
				searchResults.add(taskList.get(counter));
			}
		}
		return searchResults;
	}

}
