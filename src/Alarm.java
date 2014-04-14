import java.text.SimpleDateFormat;
import java.util.*;

//@author A0097961M

public class Alarm {

	private static final String EQUAL_NULL = "null";
	private static final String ZERO_ZERO_CONST = "00";
	private static final String EQUAL_SLASH = "/";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";
	private static final String EQUAL_BLANK_SPACE = " ";
	private static final String EQUAL_COLON = ":";
	private static final String TASKID = "Task id no";

	private static final int THOUSAND_CONST = 1000;
	private static final int TEN_THOUSAND_CONST = 10000;

	Timer timer;

	// @author A0097961M
	/**
	 * 
	 * setAlarm: set alarm by sending the timing to Alarm class
	 * 
	 * @param void
	 * @return void
	 */
	public static void setNotification() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

		Storage s = new Storage();
		s.loadStorage();

		for (Task t : ExeCom.getTaskListInstance()) {
			String day, month, year, hours, mins;
			String input;
			Date dateTime;
			long time;
			Date date = new Date();

			day = t.getStartDay();
			month = t.getStartMonth();
			year = t.getStartYear();

			hours = t.getStartHours();
			mins = t.getStartMins();

			if (t.getStartDay().equals(EQUAL_NULL)) {
				day = t.getEndDay();
				month = t.getEndMonth();
				year = t.getEndYear();
			}
			if (t.getStartHours().equals(EQUAL_NULL)) {
				hours = t.getEndHours();
				mins = t.getEndMins();
			}
			if (t.getEndHours().equals(EQUAL_NULL)) {
				hours = ZERO_ZERO_CONST;
				mins = ZERO_ZERO_CONST;
			}

			input = day + EQUAL_SLASH + month + EQUAL_SLASH + year
					+ EQUAL_BLANK_SPACE + hours + EQUAL_COLON + mins;
			dateTime = sdf.parse(input);
			time = ((dateTime.getTime() - date.getTime()) / THOUSAND_CONST);
			
			if (time > 0) {
				Alarm reminderBeep = new Alarm(time);
			}
		}
	}

	// @author A0097961M
	/**
	 * 
	 * Alarm: set alarm to the scheduler by creating instance of RemindTask
	 * class
	 * 
	 * @param void
	 * @return void
	 */
	private Alarm(long sec) {
		timer = new Timer();
		// delay in milliseconds
		timer.schedule(new RemindTask(), sec * THOUSAND_CONST);
	}

	// @author A0097961M
	/**
	 * 
	 * RemindTask: when a scheduler is called, run() method will be
	 * automatically and finally the task(s) will be mailed to user(s)
	 * 
	 * @param void
	 * @return void
	 */
	class RemindTask extends TimerTask {

		public void run() {
			try {
				long currentTime;
				Date date = new Date();

				SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

				for (Task t : ExeCom.getTaskListInstance()) {
					String day, month, year, hours, mins;
					String input;
					Date dateTime;
					long time;

					day = t.getStartDay();
					month = t.getStartMonth();
					year = t.getStartYear();

					hours = t.getStartHours();
					mins = t.getStartMins();

					if (t.getStartDay().equals(EQUAL_NULL)) {
						day = t.getEndDay();
						month = t.getEndMonth();
						year = t.getEndYear();
					}
					if (t.getStartHours().equals(EQUAL_NULL)) {
						hours = t.getEndHours();
						mins = t.getEndMins();
					}
					if (t.getEndHours().equals(EQUAL_NULL)) {
						hours = ZERO_ZERO_CONST;
						mins = ZERO_ZERO_CONST;
					}
					input = day + EQUAL_SLASH + month + EQUAL_SLASH + year
							+ EQUAL_BLANK_SPACE + hours + EQUAL_COLON + mins;
					dateTime = sdf.parse(input);
					currentTime = date.getTime();
					time = dateTime.getTime();

					if (time >= (currentTime - TEN_THOUSAND_CONST)
							&& (time <= currentTime + TEN_THOUSAND_CONST)) {
						String task_info = TASKID + EQUAL_BLANK_SPACE
								+ (ExeCom.getTaskListInstance().indexOf(t) + 1)
								+ EQUAL_BLANK_SPACE + t.displayTaskEmail();

						Email em = Email.getTheEmailInstance();
						em.emailUser(task_info);
					}

				}
			} catch (Exception e) {

			}
		}
	}

}
