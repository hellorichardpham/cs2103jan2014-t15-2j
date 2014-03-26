import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProcessCommand {

	private static final String EMPTY_STRING = "";
	private static final String INVALID_PRIORITY_MESSAGE = "Priority could not be set. Valid priorities: low, medium, high.";
	private Command c;

	/**
	 * process: Extracts information from the userInput String and stores them
	 * to a Command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            userInput
	 * @return Command
	 * 
	 */
	public Command process(String userInput) {

		c = new Command();
		String[] splitInput = new String[100];
		splitInput = userInput.split(" ");

		processFirstWordAsCommand(splitInput);
		processPriorityCategoryLocation(splitInput);
		processLocation(splitInput);
		processDate(splitInput);

		String timeDetails = extractTime(splitInput);
		processTime(timeDetails);
		processDetails(splitInput);

		
		 //to check command object 
		 System.out.println("command: " + c.getKeyword());
		 System.out.println("details: " + c.getDetails());
		 System.out.println("Start time: " + c.getStartHours() + c.getStartMins());
		 System.out.println("end time: " + c.getEndHours() + c.getEndMins());
		 System.out.println("Start date: " + c.getStartDay() + c.getStartMonth() +c.getStartYear());
		 System.out.println("end date: " + c.getEndDay() + c.getEndMonth() +c.getEndYear());
		 System.out.println("location: " + c.getLocation());
		 System.out.println("priority: " + c.getPriority());
		 System.out.println("category: " + c.getCategory());
		 System.out.println("id: " + c.getTaskID());
	

		return c;
	}

	/**
	 * processTime: process the timeDetails String to extract out the time in
	 * the correct format String and store in into the command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            timeDetails
	 * 
	 */
	public void processTime(String timeDetails) {
		// if user did not provide any time in input
		if (timeDetails == null) {
			c.setStartHours(null);
			c.setStartMins(null);
			c.setEndHours(null);
			c.setEndMins(null);
		} else {
			// assume 4 characters before "hr" will be the time
			int index = timeDetails.indexOf("hr");
			String startHours = timeDetails.substring(index - 4, index - 2);
			String startMins = timeDetails.substring(index - 2, index);
			c.setStartHours(startHours);
			c.setStartMins(startMins);

			// check if user input end time only or both the start and end time
			if (isRange(timeDetails)) {
				index = timeDetails.indexOf("hr", index + 1);
				String endHours = timeDetails.substring(index - 4, index - 2);
				String endMins = timeDetails.substring(index - 2, index);
				c.setEndHours(endHours);
				c.setEndMins(endMins);
			} else {
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

	/**
	 * processDate: process the splitInput array to Identify month signatures
	 * and store them into the command object in the correct format
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
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
					c.setStartDay(splitInput[i - 1]);
					c.setStartYear(splitInput[i + 1]);
					if (!splitInput[i + 2].equals("")) {
						splitInput[i + 2] = EMPTY_STRING;
					}
				}
				splitInput[i] = EMPTY_STRING;
				splitInput[i - 1] = EMPTY_STRING;
				splitInput[i + 1] = EMPTY_STRING;
			}
		}
		// if user did not enter any date, set date line of task as current date
		if (c.getEndMonth() == null) {
			String dateDetails = getCurrentDate();
			c.setEndDay(dateDetails.substring(8, 10));
			c.setEndMonth(dateDetails.substring(5, 7));
			c.setEndYear(dateDetails.substring(0, 4));
		}
	}

	/**
	 * processProrityCategoryLocation: Extracts Location, Priority and Category
	 * information by looking for keywords in the splitInput array and stores
	 * them into the command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
	private void processPriorityCategoryLocation(String[] splitInput) {
		for (int i = 0; i < splitInput.length; i++) {
			if (splitInput[i] != null) {
				switch (splitInput[i].toLowerCase()) {
				case "//location":
				case "l":
					String locationDetails = extractDetails(splitInput, i);
					c.setLocation(locationDetails);
					splitInput[i] = EMPTY_STRING;
					splitInput[i + 1] = EMPTY_STRING;
					break;
				case "//priority":
				case "//p":
					String priorityDetails = extractDetails(splitInput, i);
					if(isValidPriority(priorityDetails)) {
					c.setPriority(priorityDetails);
					splitInput[i] = EMPTY_STRING;
					splitInput[i + 1] = EMPTY_STRING;
					} else {
						System.out.println(INVALID_PRIORITY_MESSAGE);
						splitInput[i] = EMPTY_STRING;
						splitInput[i + 1] = EMPTY_STRING;
					}
					break;
				case "//category":
				case "//c":
					String categoryDetails = extractDetails(splitInput, i);
					c.setCategory(categoryDetails);
					splitInput[i] = EMPTY_STRING;
					splitInput[i + 1] = EMPTY_STRING;
					break;
				}
			}
		}
	}

	/**
	 * extractDetails: Extracts Location/Priority/Category details by
	 * concatenating Strings in between // identifier
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput, int index of first //
	 */
	private String extractDetails(String[] splitInput, int i) {
		String details = EMPTY_STRING;
		for (int j = i + 1; j < splitInput.length
				&& !splitInput[j].contains("//"); j++) {
			details = details + splitInput[j] + " ";
			splitInput[j] = EMPTY_STRING;
		}
		return details.trim();
	}

	/**
	 * processLocation: Extracts Location by alternative indentifier (@)
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
	private void processLocation(String[] splitInput) {
		for (int i = 0; i < splitInput.length; i++) {
			if (splitInput[i].contains("@")) {
				c.setLocation(splitInput[i].substring(1));
				splitInput[i] = EMPTY_STRING;
			}
		}

	}

	/**
	 * processDetails: Extracts Task details and stores them into Command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
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

	/**
	 * extractTime: Extracts initial time data from the userInput String and
	 * includes cases for flexible input. Reformats data and returns as standard
	 * String.
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
	private String extractTime(String[] splitInput) {
		String time = EMPTY_STRING;
		for (int i = 2; i < splitInput.length; i++) {
			if (splitInput[i].contains("hrs")) {
				time = time + splitInput[i];
				splitInput[i] = EMPTY_STRING;
				if (i + 1 < splitInput.length
						&& splitInput[i + 1].contains("hrs")) {
					time = time + splitInput[i + 1];
					splitInput[i + 1] = EMPTY_STRING;
				} else if (i + 2 < splitInput.length
						&& splitInput[i + 2].contains("hrs")) {
					time = time + splitInput[i + 1] + splitInput[i + 2];
					splitInput[i + 1] = EMPTY_STRING;
					splitInput[i + 2] = EMPTY_STRING;
				}
				return time;
			}
		}
		return null;
	}

	/**
	 * processFirstWordAsCommand: Extracts the first word in the userInput
	 * String as the Command keyword
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
	private String processFirstWordAsCommand(String[] splitInput) {

		String firstWord = splitInput[0];
		c.setKeyword(firstWord);
		splitInput[0] = EMPTY_STRING;

		switch (firstWord.toLowerCase()) {
		case "delete":

			int size = splitInput.length; // number of task specified by user
			ArrayList<String> specifiedTasks = new ArrayList<String>(size);
			for (int i = 1; i < size; i++) {
				specifiedTasks.add(splitInput[i]);
				splitInput[i] = EMPTY_STRING;
			}
			c.setTargetedTasks(specifiedTasks); // assign user specified TaskID

			break;

		case "edit":
		case "update":
			c.setTaskID(splitInput[1]);
			splitInput[1] = EMPTY_STRING;
			// System.out.println("reached process command update/delete");
			break;
		default:

		}
		return firstWord;
	}

	/**
	 * 
	 * isRange: checks if the input time is a range or just an end time
	 * 
	 * @author yingyun
	 * @param String
	 * @return boolean
	 */
	private boolean isRange(String timeDetails) {
		if (timeDetails.length() > 7) {
			return true;
		} else
			return false;
	}
	

	private static boolean isValidPriority(String categoryDetails) {
		return (categoryDetails.equalsIgnoreCase("low")
				|| categoryDetails.equalsIgnoreCase("medium") || categoryDetails
					.equalsIgnoreCase("high"));
	}
}
