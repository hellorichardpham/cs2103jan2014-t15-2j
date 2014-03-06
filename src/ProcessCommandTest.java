	import java.util.Scanner;
	
	public class ProcessCommandTest {
	 
	 public static void main(String[] args) {
	  
	  Scanner scanner = new Scanner(System.in);
	  ExeCom test = new ExeCom();
	  boolean go = true;
	  while(true) {
	  System.out.println("Enter Command: ");
	  String userInput = scanner.nextLine();
	  ProcessCommand pc = new ProcessCommand();
	  String[] info = pc.process(userInput);
	  test.executeCommand(info);
	  }
	  scanner.close();
	  
	 }
	
	}
