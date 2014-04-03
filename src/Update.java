public class Update {

	/**
	 * 
	 * editContent: edit content of specific task using the taskID based on
	 * user's input
	 * 
	 * @author yingyun, weizhou
	 * @param void
	 * @return void
	 */
	public String editContent(Command c) {
		ExeCom ec = new ExeCom();
		int id = Integer.parseInt(c.getTaskID()); // user specified task ID
		String feedback = "";
		// loop through taskList to find matching task object
		int size = ExeCom.getTaskListInstance().size();
		for (int i = 0; i < size; i++) {
			Task currentTask = ExeCom.getTaskListInstance().get(i);
			int currentTaskID = Integer.parseInt(currentTask.getTaskID());
			if (currentTaskID == id) {
				ec.saveToPrevTaskList();
				currentTask.setDetails(merge(c.getDetails(),
						currentTask.getDetails()));

				currentTask.setStartDay(merge(c.getStartDay(),
						currentTask.getStartDay()));
				currentTask.setStartMonth(merge(c.getStartMonth(),
						currentTask.getStartMonth()));
				currentTask.setStartYear(merge(c.getStartYear(),
						currentTask.getStartYear()));

				currentTask.setEndDay(merge(c.getEndDay(),
						currentTask.getEndDay()));
				currentTask.setEndMonth(merge(c.getEndMonth(),
						currentTask.getEndMonth()));
				currentTask.setEndYear(merge(c.getEndYear(),
						currentTask.getEndYear()));

				currentTask.setStartHours(merge(c.getStartHours(),
						currentTask.getStartHours()));
				currentTask.setStartMins(merge(c.getStartMins(),
						currentTask.getStartMins()));

				currentTask.setEndHours(merge(c.getEndHours(),
						currentTask.getEndHours()));
				currentTask.setEndMins(merge(c.getEndMins(),
						currentTask.getEndMins()));

				currentTask.setLocation(merge(c.getLocation(),
						currentTask.getLocation()));
				currentTask.setCategory(merge(c.getCategory(),
						currentTask.getCategory()));
				currentTask.setPriority(merge(c.getPriority(),
						currentTask.getPriority()));

				int index = currentTaskID - 1;
				ExeCom.getTaskListInstance().set(index, currentTask);

				feedback = "Succesfully Updated: "
						+ ExeCom.getTaskListInstance().get(i).getDetails()
						+ "\n";
				
				return feedback;
			}
		}
		return feedback;
	}

	/**
	 * merge: returns selected information from either command object or task
	 * object.
	 * 
	 * @author yingyun
	 * @param String
	 *            , String
	 * @return String
	 */
	private static String merge(String fromCommand, String fromTask) {
		// user did not specify this attribute to be updated
		if (fromCommand == null) {
			// System.out.println("take from task");
			// use back the same information from task object
			return fromTask;
		} else {
			// update information from command object
			// System.out.println("take from command");
			return fromCommand;
		}
	}
}
