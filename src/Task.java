/**
 * Task (class) : This class acts as a blueprint for any task object. It
 * contains information regarding a single task.
 * 
 * @author A0085107J
 */
public class Task {
	protected String details;
	protected String startDay;
	protected String startMonth;
	protected String startYear;
	protected String endDay;
	protected String endMonth;
	protected String endYear;
	protected String startHours;
	protected String startMins;
	protected String endHours;
	protected String endMins;
	protected String location;
	protected String priority;
	protected String category;
	protected String taskID;
	protected String completed;
	private long timeNum; //used for sorting

	// constructor
	public Task(Command c) {
		details = c.getDetails();
		startDay = c.getStartDay();
		startMonth = c.getStartMonth();
		startYear = c.getStartYear();
		endDay = c.getEndDay();
		endMonth = c.getEndMonth();
		endYear = c.getEndYear();
		startHours = c.getStartHours();
		startMins = c.getStartMins();
		endHours = c.getEndHours();
		endMins = c.getEndMins();
		location = c.getLocation();
		priority = c.getPriority();
		category = c.getCategory();

		this.timeNum = 100000000*Long.parseLong(endYear) + 1000000*Long.parseLong(endMonth) + 10000*Long.parseLong(endDay);
		if(endHours!=null) {
			this.timeNum += 100*Long.parseLong(endHours) + Long.parseLong(endMins); 
		} else {
			this.timeNum += 2359;
		}
	}

	public Task(Task task) {
		this.details = task.getDetails();
		this.startDay = task.getStartDay();
		this.startMonth = task.getStartMonth();
		this.startYear = task.getStartYear();
		this.endDay = task.getEndDay();
		this.endMonth = task.getEndMonth();
		this.endYear = task.getEndYear();
		this.startHours = task.getStartHours();
		this.startMins = task.getStartMins();
		this.endHours = task.getEndHours();
		this.endMins = task.getEndMins();
		this.location = task.getLocation();
		this.priority = task.getPriority();
		this.category = task.getCategory();
		this.taskID = task.getTaskID();
		
		this.timeNum = 100000000*Long.parseLong(endYear) + 1000000*Long.parseLong(endMonth) + 10000*Long.parseLong(endDay);
		if(!endHours.equals("null")) {
			this.timeNum += 100*Long.parseLong(endHours) + Long.parseLong(endMins); 
		} else {
			this.timeNum += 2359;
		}
	}

	public Task() {
	}
	
	public long getTimeNum() {
		return timeNum;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getStartHours() {
		return startHours;
	}

	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}

	public String getStartMins() {
		return startMins;
	}

	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}

	public String getEndHours() {
		return endHours;
	}

	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}

	public String getEndMins() {
		return endMins;
	}

	public void setEndMins(String endMins) {
		this.endMins = endMins;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public boolean isCompleted() {
		if (completed.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	/**
	 * displayTask: display non-empty task attributes and their headers
	 * 
	 * @author A0085107J
	 * @param void
	 * @return String
	 */
	public String displayTask() {
		String info = null;
		if (!details.equals("null")) {
			info = "//DECS2103: " + details;
		}

		if ((!startDay.equals("null") && !startMonth.equals("null") && !startYear
				.equals("null"))) {
			info = info.concat("//SDCS2103: " + startDay + "/" + startMonth
					+ "/" + startYear);
		}

		if ((!endDay.equals("null") && !endMonth.equals("null") && !endYear
				.equals("null"))
				|| (endDay != null && endMonth != null && endYear != null)) {
			info = info.concat("//EDCS2103: " + endDay + "/" + endMonth + "/"
					+ endYear);
		}

		if (!startHours.equals("null") && !startMins.equals("null")) {
			info = info.concat("//STCS2103: " + startHours + ":" + startMins);
		}
		if ((!endHours.equals("null") && !endMins.equals("null"))) {
			info = info.concat("//ETCS2103: " + endHours + ":" + endMins);
		}

		if (!location.equals("null")) {
			info = info.concat("//LOCS2103: " + location);
		}

		if (!category.equals("null")) {
			info = info.concat("//CACS2103: " + category);
		}
		if (!priority.equals("null")) {
			info = info.concat("//PRCS2103: " + priority);
		}

		return info;
	}

	/**
	 * displayToStorage: display non-empty task attributes in the correct order
	 * to external .txt file
	 * 
	 * @author A0085107J, A0097961M
	 * @param void
	 * @return String
	 */
	public String displayToStorage() {
		return details + " " + startDay + " " + startMonth + " " + startYear
				+ " " + endDay + " " + endMonth + " " + endYear + " "
				+ startHours + " " + startMins + " " + endHours + " " + endMins
				+ " " + "//location " + location + " " + "//category "
				+ category + " " + "//priority " + priority + " "
				+ "//completed " + completed + " ";
	}

}
