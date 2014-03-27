import java.util.ArrayList;

	/**
	 * Command (class) : This class acts as a blueprint for any task object. It contains
	 * information regarding a single task.
	 * @author yingyun
	 */
public class Command {

	private String keyword;
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
	private String taskID; //for update as each update command can only have 1 taskID
	private ArrayList<String> TargetedTasks; //for deleting/searching
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
		this.priority = priority.toLowerCase();
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
	
	public ArrayList<String> getTargetedTasks() {
		return TargetedTasks;
	}
	
	public void setTargetedTasks(ArrayList<String> targetedTasks) {
		this.TargetedTasks = targetedTasks;
	}
}
