
public class Task {
	private String details;
	private String startDay;
	private String startMonth;
	private String startYear;
	private String endDay;
	private String endMonth;
	private String endYear;
	private String startHours;
	private String startMin;
	private String endHours;
	private String endMins;
	private String location;
	private String priority;
	private String category;
	private String taskID;
	
	//constructor
	public Task(String[] info){
		details = info[1];
		startDay = info[2];
		startMonth = info[3];
		startYear = info[4];
		endDay = info[5];
		endMonth = info[6];
		endYear = info[7];
		startHours = info[8];
		startMin = info[9];
		endHours = info[10];
		endMins = info[11];
		location = info[12];
		priority = info[13];
		category = info[14];
		taskID = info[15];
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
		this.startMin = task.getStartMin();
		this.endHours = task.getEndHours();
		this.endMins = task.getEndMins();
		this.location = task.getLocation();
		this.priority = task.getPriority();
		this.category = task.getCategory();
		this.taskID = task.getTaskID();
	}

	public Task() {
		// TODO Auto-generated constructor stub
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

	public String getStartMin() {
		return startMin;
	}

	public void setStartMin(String startMin) {
		this.startMin = startMin;
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
		return details + " " + startDay + " " + startMonth + " " + startYear + " " + endDay + " " + endMonth + " " + endYear + " " + startHours + " " + startMin + " " + endHours + " " + endMins + " " + location + " " + priority + " " + category + " ";
	}
	
	public String displayAll() {
		return taskID + ": " + details + " " + startDay + " " + startMonth + " " + startYear + " " + endDay + " " + endMonth + " " + endYear + " " + startHours + " " + startMin + " " + endHours + " " + endMins + " " + location + " " + priority + " " + category;
	}

}
