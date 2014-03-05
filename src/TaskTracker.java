import java.util.ArrayList;
import java.util.Scanner;

public class TaskTracker {
	
	public void main(String[] args){
		String userInput;
		String messageToPrint;
		ArrayList<String> info;

		while(true){
			userInput = getUserInput();
			info = processCommand(userInput);
			messageToPrint = executeCommand(info);
			showToUser(messageToPrint);
		}
	}

	/**
	 * @param none
	 * @return userInput
	 */
	private String getUserInput() {
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		sc.close();
		return userInput;
	}

	/**
	 * @param userInput
	 * @return info array containing all attributes of a task
	 */
	private ArrayList<String> processCommand(String userInput) {
		ProcessCommand pc = new ProcessCommand(userInput);
		ArrayList<String> info = pc.breakDownCommand();
		return info;
	}

	/**
	 * @param info
	 * @return messageToPrint
	 */
	private String executeCommand(ArrayList<String> info) {
		ExecuteCommand ec = new ExecuteCommand(info);
		String messageToPrint = ec.executeCommand();
		return messageToPrint;
	}

	private void showToUser(String messageToPrint) {
		System.out.println(messageToPrint);
	}
}
