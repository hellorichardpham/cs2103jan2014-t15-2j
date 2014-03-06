import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcessCommand {
	/*
	 * info array of Strings[16] 0.) command 1.) details 2.) startDay 3.)
	 * startMonth 4.) startYear 5.) endDay 6.) endMonth 7.) endYear 8.)
	 * startHours 9.) startMins 10.) endHours 11.) endMins 12.) location 13.)
	 * priority 14.) category 15.) id
	 */
	private String[] info = new String[16];

	public ProcessCommand() {
	}

	public String[] process(String userInput) {

		String[] original = new String[100];
		original = userInput.split(" ");

		processFirstWordAsCommand(original);
		processLocationPriorityCategory(original);
		processDate(original);

		String timeDetails = extractTime(original);
		processTime(timeDetails);
		processDetails(original);

		return info;
	}

	public void processTime(String timeDetails){
		if (timeDetails==null){
			info[8] = null;
			info[9] = null;
			info[10] = null;
			info[11] = null;
		}else{			
			//assume 4 characters before "hr" will be the time
			int index = timeDetails.indexOf("hr");
			info[8] = timeDetails.substring(index-4, index-2);
			info[9] = timeDetails.substring(index-2, index);

			if(isRange(timeDetails)){
				index = timeDetails.indexOf("hr", index+1);
				info[10] = timeDetails.substring(index-4, index-2);
				info[11] = timeDetails.substring(index-2, index);
			}else{
				info[10] = null;
				info[11] = null;
			}
		}	
	}

	/**
	 * 
	 * isRange: checks if the input time is a range or just an end time
	 * @author yingyun
	 * @param timeDetails
	 * @return true if time is in range format, otherwise return false
	 */
	private boolean isRange(String timeDetails) {
		if (timeDetails.length() > 7){
			return true;
		}else
			return false;
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

	private void processDate(String[] original) {
		for (int i = original.length - 2; i > 0; i--) {

			String month = null;

			switch (original[i].toLowerCase()) {
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
				if (info[6] == null) {
					info[6] = month;
					info[5] = original[i - 1];
					info[7] = original[i + 1];
				} else {
					info[3] = month;
					info[2] = original[i - 1];
					info[4] = original[i + 1];
					if (!original[i + 2].equals("")) {
						original[i + 2] = "";
					}
				}
				original[i] = "";
				original[i - 1] = "";
				original[i + 1] = "";
			}
		}
		if (info[6] == null) {
			String dateDetails = getCurrentDate();
			info[5] = dateDetails.substring(8, 10);
			info[6] = dateDetails.substring(5, 7);
			info[7] = dateDetails.substring(0, 4);
		}
	}

	private void processLocationPriorityCategory(String[] original) {
		for (int i = 0; i < original.length; i++) {
			if (original[i] != null) {
				switch (original[i]) {
				case "//Location":
				case "//location":
				case "//L":
				case "//l":
					info[12] = original[i + 1];
					original[i] = "";
					original[i + 1] = "";
					break;
				case "//Priority":
				case "//priority":
				case "//P":
				case "//p":
					info[13] = original[i + 1];
					original[i] = "";
					original[i + 1] = "";
					break;
				case "//Category":
				case "//category":
				case "//C":
				case "//c":
					info[14] = original[i + 1];
					original[i] = "";
					original[i + 1] = "";
					break;
				}
			}
		}
	}

	private void processDetails(String[] original) {
		String details = "";
		for (int i = 0; i < original.length; i++) {
			if (!original[i].equals("")) {
				details = details + original[i] + " ";
			}
		}
		if (!details.equals("")) {
			info[1] = details;
		} else {
			info[1] = null;
		}
	}

	private String extractTime(String[] original) {
		String time = "";
		for (int i = 2; i < original.length; i++) {
			if (original[i].contains("hrs")) {
				time = time + original[i];
				original[i] = "";
				if (i + 1 < original.length && original[i + 1].contains("hrs")) {
					time = time + original[i + 1];
					original[i + 1] = "";
				} else if (i + 2 < original.length
						&& original[i + 2].contains("hrs")) {
					time = time + original[i + 1] + original[i + 2];
					original[i + 1] = "";
					original[i + 2] = "";
				}
				return time;
			}
		}
		return null;
	}

	private void processFirstWordAsCommand(String[] original) {

		String firstWord = original[0];
		info[0] = firstWord;
		original[0] = "";

		switch (firstWord) {
		case "Edit":
		case "edit":
		case "update":
		case "Update":
		case "Delete":
		case "delete":
			info[15] = original[1];
			original[1] = "";
			break;
		default:

		}
	}
}
