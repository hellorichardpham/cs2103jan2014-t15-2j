
public class TaskTracker {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String userInput;
		UI ui = new UI();
		
		ui.printWelcomeMessage();
		
		while (true) {
			userInput = ui.promptUser();
			exitIfUserWants(userInput);
			elseProcessInput(userInput);

		}
	}//end main

	private static void elseProcessInput(String userInput) throws Exception {
		ProcessCommand pc = new ProcessCommand();
		ExeCom ec = ExeCom.getInstance();
		
		Command c = pc.process(userInput);
		ec.executeCommand(c);
	}

	private static void exitIfUserWants(String userInput) {
		if(userInput.equals("stop")||userInput.equals("exit")||userInput.equals("quit")){
			System.exit(0);
		}		
	}
}//end class
