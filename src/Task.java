	/**
	 * Command (class) : This class acts as a blueprint for any task object. It contains
	 * information regarding a single task.
	 * @author yingyun
	 */
public class Task {
	private String details;
	private String startDay;
	private String startMonth;
	private String startYear;
	private String endDay;
	private String endMonth;
	private String endYear;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private String location;
	private String priority;
	private String category;
	private String taskID;
	
	//constructor
	public Task(Command c){
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
		taskID = c.getTaskID();
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
	}

	public Task() {
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
	
	public String display() {
		return details + " " + startDay + " " + startMonth + " " + startYear + " " + endDay + " " + endMonth + " " + endYear + " " + startHours + " " + startMins + " " + endHours + " " + endMins + " " + location + " " + priority + " " + category + " ";
	}
	
	public String displayAll() {
		return taskID + ": " + details + " " + startDay + " " + startMonth + " " + startYear + " " + endDay + " " + endMonth + " " + endYear + " " + startHours + " " + startMins + " " + endHours + " " + endMins + " " + location + " " + priority + " " + category;
	}

}
