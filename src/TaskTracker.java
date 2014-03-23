
public class TaskTracker {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		String userInput;
		UI ui = new UI();
		
		//Storage s = null;
		//s = new Storage();
		//s.loadStorage();
		
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
		
		String[] info = pc.process(userInput);
		ec.executeCommand(info);
	}

	private static void exitIfUserWants(String userInput) {
		if(userInput.equals("stop")||userInput.equals("exit")||userInput.equals("quit")){
			System.exit(0);
		}		
	}
	
}//end class
