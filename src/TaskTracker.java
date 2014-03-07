import java.util.Scanner;

public class TaskTasker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Scanner scanner = new Scanner(System.in);
		  ExeCom test = new ExeCom();
		  boolean go = true;
		  while(go) {
		  System.out.println("Enter Command: ");
		  String userInput = scanner.nextLine();
		  if(userInput.equals("stop")) {
			  go = false;
		  }
		  ProcessCommand pc = new ProcessCommand();
		  String[] info = pc.process(userInput);
		  test.executeCommand(info);
		  }
		  scanner.close();
		 }
	}


