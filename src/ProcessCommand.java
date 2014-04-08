import java.util.ArrayList;
import java.util.Calendar;

/**
 * ProcessCommand (class) : This class acts as the language processing unit,
 * converting what the user inputs as a String to various attributes in the
 * Command Class
 * 
 * @author Tian Weizhou
 */
public class ProcessCommand {

	private static final String EMPTY_STRING = "";

	int indexOfDayOfWeek = Integer.MAX_VALUE;
	int indexOfMonth = Integer.MIN_VALUE;

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
		String commandType = splitInput[0];

		processFirstWordAsCommand(splitInput);
		processPriorityCategoryLocation(splitInput);
		processLocation(splitInput);

		//	processBy(splitInput);

		String timeDetails = extractTime(splitInput);
		processTime(timeDetails);

		processDayofWeek(splitInput);
		processTodayTmr(splitInput);
		processDate(splitInput, commandType);
		switchIfDatesAreReversed();

		processDetails(splitInput);

		//	to check command object 
		System.out.println("command: " +
				c.getKeyword()); System.out.println("details: " + c.getDetails());
				System.out.println("Start time: " + c.getStartHours() +
						c.getStartMins()); System.out.println("end time: " + c.getEndHours()
								+ c.getEndMins()); System.out.println("Start date: " +
										c.getStartDay() + c.getStartMonth() +c.getStartYear());
								System.out.println("end date: " + c.getEndDay() + c.getEndMonth()
										+c.getEndYear()); System.out.println("location: " + c.getLocation());
										System.out.println("priority: " + c.getPriority());
										System.out.println("category: " + c.getCategory());
										System.out.println("id: " + c.getTaskID());

										return c;
	}

	/**
	 * processFirstWordAsCommand: Extracts the first word in the userInput
	 * String as the Command keyword
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            []
	 */
	private String processFirstWordAsCommand(String[] splitInput) {

		String firstWord = splitInput[0];
		firstWord = convertMultipleCommandKeywords(firstWord);

		c.setKeyword(firstWord);
		splitInput[0] = EMPTY_STRING;

		switch (firstWord.toLowerCase()) {
		case "delete":
		case "completed":
		case "done":
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
			break;
		}
		return firstWord;
	}

	/**
	 * processProrityCategoryLocation: Extracts Location, Priority and Category
	 * information by looking for keywords in the splitInput array and stores
	 * them into the command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 */
	private void processPriorityCategoryLocation(String[] splitInput) {
		for (int i = 0; i < splitInput.length; i++) {
			if (splitInput[i] != null) {
				switch (splitInput[i].toLowerCase()) {
				case "//location":
				case "//l":
					String locationDetails = extractDetails(splitInput, i);
					c.setLocation(locationDetails);
					splitInput[i] = EMPTY_STRING;
					splitInput[i + 1] = EMPTY_STRING;
					break;
				case "//priority":
				case "//p":
					String priorityDetails = extractDetails(splitInput, i);
					c.setPriority(priorityDetails);
					splitInput[i] = EMPTY_STRING;
					splitInput[i + 1] = EMPTY_STRING;
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
	 * processLocation: Extracts Location by alternative indentifier (@)
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            []
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
	 * extractTime: Extracts initial time data from the userInput String and
	 * includes cases for flexible input. Reformats data and returns as standard
	 * String.
	 * 
	 * @author Tian Weizhou, yingyun
	 * @param String
	 *            []
	 */
	String extractTime(String[] splitInput) {
		String time = EMPTY_STRING;
		for (int i = 2; i < splitInput.length; i++) {
			if (splitInput[i].contains("hrs")) {
				time = time + splitInput[i];
				splitInput[i] = EMPTY_STRING;
				if(splitInput[i-1].equals("by")){
					splitInput[i-1] = EMPTY_STRING;
				}
				if (i + 1 < splitInput.length
						&& splitInput[i + 1].contains("hrs")) {
					time = time + splitInput[i + 1];
					splitInput[i + 1] = EMPTY_STRING;
				} else if (i + 2 < splitInput.length
						&& splitInput[i + 2].contains("hrs")) {
					time = time + splitInput[i + 2];
					splitInput[i + 1] = EMPTY_STRING;
					splitInput[i + 2] = EMPTY_STRING;
				}else if (splitInput[i].indexOf("hrs")!=splitInput[i].lastIndexOf("hrs")){
					time = splitInput[i].replace("-", EMPTY_STRING);
				}
				return time;
			}
		}
		return null;
	}

	/**
	 * processTime: process the timeDetails String to extract out the time in
	 * the correct format String and store in into the command object
	 * 
	 * @author Tan Ying Yun
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
			// check if user input end time only or both the start and end time
			if (isRange(timeDetails)) {
				// extract and set start time
				int index = timeDetails.indexOf("hr");
				String startHours = timeDetails.substring(index - 4, index - 2);
				String startMins = timeDetails.substring(index - 2, index);
				c.setStartHours(startHours);
				c.setStartMins(startMins);

				// extract and set end time
				index = timeDetails.indexOf("hr", index + 1);
				String endHours = timeDetails.substring(index - 4, index - 2);
				String endMins = timeDetails.substring(index - 2, index);
				c.setEndHours(endHours);
				c.setEndMins(endMins);

			} else { // user only enter end time
				// assume 4 characters before "hr" will be the time
				int index = timeDetails.indexOf("hr");
				String endHours = timeDetails.substring(index - 4, index - 2);
				String endMins = timeDetails.substring(index - 2, index);
				c.setEndHours(endHours);
				c.setEndMins(endMins);
				c.setStartHours(null);
				c.setStartMins(null);
			}
		}
	}

	/**
	 * isRange: checks if the input time is a range or just an end time
	 * 
	 * @author yingyun
	 * @param String
	 * @return boolean
	 */
	private boolean isRange(String timeDetails) {
		if (timeDetails.length() > 7) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * processDayOfWeek: Extracts date given the day of the week of user enters
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [] splitInput
	 * 
	 */
	private void processDayofWeek(String[] splitInput) {
		for (int i = splitInput.length - 1; i > 0; i--) {
			int inputDay = findDaysofWeek(splitInput[i]);

			if (inputDay != -1) {
				indexOfDayOfWeek = i;
				int isNextWeek = 0;

				switch (splitInput[i - 1]) {
				case "next":
					isNextWeek = 7;
				case "this":
				case "on":
				case "to":
				case "from":
				case "by":
					splitInput[i - 1] = EMPTY_STRING;
				}

				if (i - 2 > 0) {
					if (splitInput[i - 2].equals("-")
							|| splitInput[i - 2].equals("to")
							|| splitInput[i - 2].equals("from")
							|| splitInput[i - 2].equals("frm")) {
						splitInput[i - 2] = EMPTY_STRING;
					}
				}

				splitInput[i] = EMPTY_STRING;

				Calendar cal = Calendar.getInstance();
				int currentDayOfMonth = cal.get(Calendar.DAY_OF_WEEK);
				int daysToAdd = inputDay - currentDayOfMonth;
				if (daysToAdd < 0) {
					isNextWeek = 7;
				}
				if (daysToAdd == 0 && c.getEndHours() != null) {
					int currentHour = cal.get(Calendar.HOUR_OF_DAY);
					int currentMin = cal.get(Calendar.MINUTE);

					if (currentHour > Integer.parseInt(c.getEndHours())) {
						isNextWeek = 7;
					} else if (currentHour == Integer.parseInt(c.getEndHours())
							&& currentMin > Integer.parseInt(c.getEndMins())) {
						isNextWeek = 7;
					}
				}

				cal.add(Calendar.DAY_OF_MONTH, daysToAdd + isNextWeek);
				setDate(cal);

			}
		}

	}

	/**
	 * findDaysOfWeek: Identifies if a String contains a weekday and returns the
	 * day of the week as a integer
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            ithInput
	 * @return int day of week
	 * 
	 */
	private int findDaysofWeek(String ithInput) {
		int days = -1;
		switch (ithInput.toLowerCase()) {
		case "sunday":
		case "sun":
			days = 1;
			break;
		case "monday":
		case "mon":
			days = 2;
			break;
		case "tues":
		case "tuesday":
		case "tue":
			days = 3;
			break;
		case "wednesday":
		case "wed":
		case "weds":
			days = 4;
			break;
		case "thurs":
		case "thur":
		case "thursday":
			days = 5;
			break;
		case "friday":
		case "fri":
			days = 6;
			break;
		case "saturday":
		case "sat":
			days = 7;
			break;
		}

		return days;
	}

	/**
	 * processTodayTmr: Detects if user types "today" or "tmr" and interpret it
	 * as a date String as the Command keyword
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            []
	 */
	private void processTodayTmr(String[] splitInput) {
		for (int i = splitInput.length - 1; i > 0; i--) {
			Calendar cal = Calendar.getInstance();
			switch (splitInput[i].toLowerCase()) {
			case "tommorrow":
			case "tomorrow":
			case "tmrw":
			case "tmr":
				cal.add(Calendar.DAY_OF_MONTH, 1);
			case "today":
			case "2day":

				setDate(cal);
				splitInput[i] = EMPTY_STRING;
				if (i - 2 > 0) {
					if (splitInput[i - 1].equals("-")
							|| splitInput[i - 1].equals("to")
							|| splitInput[i - 1].equals("by")
							|| splitInput[i - 1].equals("from")
							|| splitInput[i - 1].equals("frm")) {
						splitInput[i - 1] = EMPTY_STRING;
					}
				}

			}

		}

	}

	/**
	 * setDate: set the appropriate date attributes depending on calendar
	 * 
	 * @author ying yun
	 * @param Calendar
	 * @return void
	 */
	private void setDate(Calendar cal) {
		if (c.getEndYear() == null) {
			setEndDate(cal);
		} else {
			setStartDate(cal);
		}
	}

	/**
	 * setStartDate: set start date of command depending on calendar
	 * 
	 * @author ying yun
	 * @param Calendar
	 * @return void
	 */
	private void setStartDate(Calendar cal) {
		c.setStartYear(cal.get(Calendar.YEAR) + "");
		c.setStartMonth(cal.get(Calendar.MONTH) + 1 + "");
		c.setStartDay(cal.get(Calendar.DAY_OF_MONTH) + "");
	}

	/**
	 * setEndDate: set end date of command depending on calendar
	 * 
	 * @author ying yun
	 * @param Calendar
	 * @return void
	 */
	private void setEndDate(Calendar cal) {
		c.setEndYear(cal.get(Calendar.YEAR) + "");
		c.setEndMonth(cal.get(Calendar.MONTH) + 1 + "");
		c.setEndDay(cal.get(Calendar.DAY_OF_MONTH) + "");
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
	private void processDate(String[] splitInput, String commandType) {

		for (int i = splitInput.length - 1; i > 0; i--) {

			String month = findMonth(splitInput[i]);

			if (!month.equals(EMPTY_STRING)) {
				indexOfMonth = i;
				if (c.getEndMonth() == null) {
					c.setEndMonth(month);
					c.setEndDay(splitInput[i - 1]);
					setEndYearIfUserNoInput(splitInput, i);
				} else {
					c.setStartMonth(month);
					c.setStartDay(splitInput[i - 1]);
					setStartYearIfUserNoInput(splitInput, i);
				}

				splitInput[i] = EMPTY_STRING;
				splitInput[i - 1] = EMPTY_STRING;

				if (i - 2 > 0 && (splitInput[i - 2].equals("-") || 
								splitInput[i - 2].equals("to") ||
								splitInput[i - 2].equals("by") ||
								splitInput[i - 2].equals("from"))) {
					splitInput[i - 2] = EMPTY_STRING;
				}
			}
		}

		// if user did not enter any date, set deadline of task as current date
		if (c.getEndMonth() == null
				&& !(c.getKeyword().equals("edit") || c.getKeyword().equals("update") || c.getKeyword().equals("display"))) {
			Calendar cal = Calendar.getInstance();
			if (c.getEndHours() != null) {
				if (Integer.parseInt(c.getEndHours()) < cal
						.get(Calendar.HOUR_OF_DAY)) {
					cal.add(Calendar.DATE, 1);
				} else if (Integer.parseInt(c.getEndHours()) == cal
						.get(Calendar.HOUR_OF_DAY)
						&& Integer.parseInt(c.getEndMins()) < cal
						.get(Calendar.MINUTE)) {
					cal.add(Calendar.DATE, 1);
				}
			}
			setEndDate(cal);
		}
	}

	/**
	 * findMonth: identifies if input String is a month and returns integer
	 * value of month
	 * 
	 * @author Tian Weizhou
	 * @param String
	 * @return String
	 */
	private String findMonth(String ithSplitInput) {
		String month = EMPTY_STRING;
		switch (ithSplitInput.toLowerCase()) {
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
		return month;
	}

	/**
	 * setStartYearIfNoInput: Sets the Start year as current year if the user
	 * did not input a year
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [], int
	 */
	private void setStartYearIfUserNoInput(String[] splitInput, int i) {
		if (i + 1 < splitInput.length) {
			try {
				int year = Integer.parseInt(splitInput[i + 1]);
				c.setStartYear(year + "");
				splitInput[i + 1] = EMPTY_STRING;
			} catch (NumberFormatException e) {
				setStartAsCurrentYear();
			}
		} else {
			setStartAsCurrentYear();
		}
	}

	/**
	 * setStartAsCurrentYEar: Sets the start year as current year
	 */
	private void setStartAsCurrentYear() {
		Calendar cal = Calendar.getInstance();
		c.setStartYear(cal.get(Calendar.YEAR) + "");
	}

	/**
	 * setEndYearIfNoInput: Sets the end year as current year if the user did
	 * not input a year
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [], int
	 */
	private void setEndYearIfUserNoInput(String[] splitInput, int i) {
		if (i + 1 < splitInput.length) {
			try {
				int year = Integer.parseInt(splitInput[i + 1]);
				c.setEndYear(year + "");
				splitInput[i + 1] = EMPTY_STRING;
			} catch (NumberFormatException e) {
				setEndAsCurrentYear();
			}
		} else {
			setEndAsCurrentYear();
		}
	}

	/**
	 * setEndAsCurrentYEar: Sets the end year as current year
	 */
	private void setEndAsCurrentYear() {
		Calendar cal = Calendar.getInstance();
		c.setEndYear(cal.get(Calendar.YEAR) + "");
	}

	/**
	 * switchIfDateAreReversed: Handles case where user inputs weekday followed
	 * by month as date input. Or when end date is earlier than start date.
	 * Switches mixed up attributes
	 * 
	 * @author Tian Weizhou
	 */
	private void switchIfDatesAreReversed() {
		if (indexOfMonth > indexOfDayOfWeek || ifEndDateEarlierThanStart()) {
			String tempDay = c.getStartDay();
			String tempMonth = c.getStartMonth();
			String tempYear = c.getStartYear();

			c.setStartDay(c.getEndDay());
			c.setStartMonth(c.getEndMonth());
			c.setStartYear(c.getEndYear());

			c.setEndDay(tempDay);
			c.setEndMonth(tempMonth);
			c.setEndYear(tempYear);
		}
	}

	/**
	 * processDetails: Extracts Task details and stores them into Command object
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            []
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
	 * ifEndDateEarlierThanStart: Checks if the user input end date is earlier
	 * than the start date
	 * 
	 * @author Tian Weizhou
	 * @return boolean
	 * 
	 */
	private boolean ifEndDateEarlierThanStart() {
		if (c.getStartYear() != null) {
			if (Integer.parseInt(c.getEndYear()) < Integer.parseInt(c
					.getStartYear())) {
				return true;
			}
			if (Integer.parseInt(c.getEndMonth()) < Integer.parseInt(c
					.getStartMonth())) {
				return true;
			}
			if (Integer.parseInt(c.getEndDay()) < Integer.parseInt(c
					.getStartDay())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * extractDetails: Extracts Location/Priority/Category details by
	 * concatenating Strings in between // identifier
	 * 
	 * @author Tian Weizhou
	 * @param String
	 *            [], int
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

	public static String convertMultipleCommandKeywords(String command) {
		command = command.toLowerCase();
		if (command.equals("disp") || command.equals("dis")
				|| command.equals("d") || command.equals("di")
				|| command.equals("show")) {
			command = "display";
		} else if (command.equals("a") || command.equals("ad")) {
			command = "add";
		} else if (command.equals("de") || command.equals("del")
				|| command.equals("dele") || command.equals("rm")
				|| command.equals("rem") || command.equals("rmv")) {
			command = "delete";
		} else if (command.equals("s") || command.equals("se")
				|| command.equals("sea") || command.equals("sear")
				|| command.equals("searc") || command.equals("find")) {
			command = "search";
		} else if (command.equals("u") || command.equals("un")
				|| command.equals("und")) {
			command = "undo";
		} else if (command.equals("r") || command.equals("re")
				|| command.equals("red")) {
			command = "redo";
		} else if (command.equals("e") || command.equals("ed")
				|| command.equals("edi") || command.equals("change")) {
			command = "edit";
		} else if (command.equals("em") || command.equals("ema")
				|| command.equals("send")) {
			command = "email";
		} else if (command.equals("c") || command.equals("cl")
				|| command.equals("cle") || command.equals("clea")
				|| command.equals("clr")) {
			command = "clear";
		} else if (command.equals("dm") || command.equals("dism")
				|| command.equals("dispm") || command.equals("displaym")
				|| command.equals("deadline") || command.equals("dispd")
				|| command.equals("dd") || command.equals("dead")
				|| command.equals("dl") || command.equals("deadl")
				|| command.equals("ends") || command.equals("end")) {
			command = "displayd";
		} else {
			// do nothing
		}
		return command;
	}
}
