import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProcessCommand {
	
	private Command c;
	
	/**
	 * process: Extracts information from the userInput String and stores them to a Command object
	 * 
	 * @author Tian Weizhou
	 * @param String userInput
	 * @return Command
	 * 
	 */
	public Command process(String userInput) {
		
		c = new Command();
		String[] splitInput = new String[100];
		splitInput = userInput.split(" ");
		
		processFirstWordAsCommand(splitInput);
		processPriorityCategory(splitInput);
		processLocation(splitInput);
		processDate(splitInput);
		
		String timeDetails = extractTime(splitInput);
		processTime(timeDetails);
		processDetails(splitInput);
		
		
		/*//to check command object
		System.out.println("command: " + c.getKeyword());
		System.out.println("details: " + c.getDetails());
		System.out.println("location: " + c.getLocation());
		System.out.println("priority: " + c.getPriority());
		System.out.println("category: " + c.getCategory());
		System.out.println("end time: " + c.getEndHours() + c.getEndMins());
		System.out.println("end date: " + c.getEndDay() + c.getEndMonth() +c.getEndYear());
		System.out.println("id: " + c.getTaskID());*/

		return c;
	}
	
	/**
	 * processTime:
	 * 
	 * @author Tian Weizhou
	 * @param String timeDetails
	 * 
	 */
	public void processTime(String timeDetails){
		//if user did not provide any time in input
		if (timeDetails==null){
			c.setStartHours(null);
			c.setStartMins(null);
			c.setEndHours(null);
			c.setEndMins(null);
		}else{			
			//assume 4 characters before "hr" will be the time
			int index = timeDetails.indexOf("hr");
			String startHours = timeDetails.substring(index-4, index-2);
			String startMins = timeDetails.substring(index-2, index);
			c.setStartHours(startHours);
			c.setStartMins(startMins);

			//check if user input end time only or both the start and end time
			if(isRange(timeDetails)){
				index = timeDetails.indexOf("hr", index+1);
				String endHours = timeDetails.substring(index-4, index-2);
				String endMins = timeDetails.substring(index-2, index);
				c.setEndHours(endHours);
				c.setEndMins(endMins);
			}else{
				c.setEndHours(null);
				c.setEndMins(null);
			}
		}	
	}

	/**
	 * getCurrentDate: retrieve current date from user's device
	 * 
	 * @author yingyun
	 * @param void
	 * @return formattedDate
	 * 
	 */
	private String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date currentDate = new Date();
		String formattedDate = dateFormat.format(currentDate);
		return formattedDate;
	}

	private void processDate(String[] splitInput) {
		for (int i = splitInput.length - 2; i > 0; i--) {

			String month = null;

			switch (splitInput[i].toLowerCase()) {
			case "jan":
			case "january":
				month = "01";
				break;
			case "feb":
			case "february":
				month = "02";
				break;
			case "mar":
			case "march":
				month = "03";
				break;
			case "apr":
			case "april":
				month = "04";
				break;
			case "may":
				month = "05";
				break;
			case "jun":
			case "june":
				month = "06";
				break;
			case "july":
			case "jul":
				month = "07";
				break;
			case "august":
			case "aug":
				month = "08";
				break;
			case "september":
			case "sep":
			case "sept":
				month = "09";
				break;
			case "october":
			case "oct":
				month = "10";
				break;
			case "november":
			case "nov":
				month = "11";
				break;
			case "december":
			case "dec":
				month = "12";
				break;
			}
			if (month != null) {
				if (c.getEndMonth() == null) {
					c.setEndMonth(month);
					c.setEndDay(splitInput[i - 1]);
					c.setEndYear(splitInput[i + 1]);
				} else {
					c.setStartMonth(month);
					c.setStartDay(splitInput[i-1]);
					c.setStartYear(splitInput[i+1]);
					if (!splitInput[i + 2].equals("")) {
						splitInput[i + 2] = empty(splitInput[i + 2]);
					}
				}
				splitInput[i] = empty(splitInput[i]);
				splitInput[i - 1] = empty(splitInput[i - 1]);
				splitInput[i + 1] = empty(splitInput[i + 1]);
			}
		}
		//if user did not enter any date, set date line of task as current date
		if (c.getEndMonth() == null) {
			String dateDetails = getCurrentDate();
			c.setEndDay(dateDetails.substring(8, 10));
			c.setEndMonth(dateDetails.substring(5,7));
			c.setEndYear(dateDetails.substring(0,4));
		}
	}

	private void processPriorityCategory(String[] splitInput) {
		for (int i = 0; i < splitInput.length; i++) {
			if (splitInput[i] != null) {
				switch (splitInput[i].toLowerCase()) {
				case "//priority":
				case "//p":
					c.setPriority(splitInput[i + 1]);
					splitInput[i] = empty(splitInput[i]);
					splitInput[i + 1] = empty(splitInput[i + 1]);
					break;
				case "//category":
				case "//c":
					c.setCategory(splitInput[i + 1]);
					splitInput[i] = empty(splitInput[i]);
					splitInput[i + 1] = empty(splitInput[i + 1]);
					break;
				}
			}
		}
	}

	private void processLocation(String[] splitInput) {
		for(int i = 0; i < splitInput.length; i++) {
			if(splitInput[i].contains("@")) {
				c.setLocation(splitInput[i].substring(1));
				splitInput[i] = empty(splitInput[i]);
			}
		}
		
	}

	private void processDetails(String[] splitInput) {
		String details = "";
		for (int i = 0; i < splitInput.length; i++) {
			if (!splitInput[i].equals("")) {
				details = details + splitInput[i] + " ";
			}
		}
		details = details.trim();
		if (!details.equals("")) {
			c.setDetails(details);
		} else {
			c.setDetails(null);
		}
	}

	private String extractTime(String[] splitInput) {
		String time = "";
		for (int i = 2; i < splitInput.length; i++) {
			if (splitInput[i].contains("hrs")) {
				time = time + splitInput[i];
				splitInput[i] = empty(splitInput[i]);
				if (i + 1 < splitInput.length && splitInput[i + 1].contains("hrs")) {
					time = time + splitInput[i + 1];
					splitInput[i + 1] = empty(splitInput[i + 1]);
				} else if (i + 2 < splitInput.length
						&& splitInput[i + 2].contains("hrs")) {
					time = time + splitInput[i + 1] + splitInput[i + 2];
					splitInput[i + 1] = empty(splitInput[i + 1]);
					splitInput[i + 2] = empty(splitInput[i + 2]);
				}
				return time;
			}
		}
		return null;
	}

	private String processFirstWordAsCommand(String[] splitInput) {

		String firstWord = splitInput[0];
		c.setKeyword(firstWord);
		splitInput[0] = empty(splitInput[0]);

		switch (firstWord.toLowerCase()) {
		case "delete":
			
			int size=splitInput.length;	//number of task specified by user
			ArrayList<String> specifiedTasks = new ArrayList<String>(size);
			for (int i=1; i<size;i++){
				specifiedTasks.add(splitInput[i]);
				splitInput[i] = empty(splitInput[i]);
			}
			c.setTargetedTasks(specifiedTasks); //assign user specified TaskID
			
			break;
			
		case "edit":
		case "update":
			c.setTaskID(splitInput[1]);
			splitInput[1] = empty(splitInput[1]);
			System.out.println("reached process command update/delete");
			break;
		default:

		}
		return firstWord;
	}

	/**
	 * 
	 * isRange: checks if the input time is a range or just an end time
	 * @author yingyun
	 * @param String
	 * @return boolean
	 */
	private boolean isRange(String timeDetails) {
		if (timeDetails.length() > 7){
			return true;
		}else
			return false;
	}
	
	/**
	 * 
	 * empty: converts identified information from splitInput[] to empty string
	 * @author Tian Wei Zhou
	 * @param String
	 * @return String
	 */
	private String empty(String string) {
		return string = "";
	}
}
