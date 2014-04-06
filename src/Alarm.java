import java.text.SimpleDateFormat;
import java.util.*;

public class Alarm {

	private static final String EQUAL_NULL = "null";
	private static final String ZERO_ZERO_CONST = "00";
	private static final String EQUAL_SLASH = "/";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm";
	private static final String EQUAL_BLANK_SPACE = " ";
	private static final String EQUAL_COLON = ":";

	private static final int THOUSAND_CONST = 1000;
	private static final int TEN_THOUSAND_CONST = 10000;

	Timer timer;

	/**
	 * 
	 * setAlarm: set alarm by sending the timing to Alarm class
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	public static void setAlarm() throws Exception {
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
			// System.out.println("SET ALARM " + time);

			if (time > 0) {
				Alarm reminderBeep = new Alarm(time);
			}
		}
	}
	
	/**
	 * 
	 * Alarm: set alarm to the scheduler in RemindTask
	 * 
	 * @author Khaleef
	 * @param void
	 * @return void
	 */
	private Alarm(long sec) {
		// At this line a new Thread will be created
		timer = new Timer();
		// delay in milliseconds
		timer.schedule(new RemindTask(), sec * THOUSAND_CONST);
	}
	
	/**
	 * 
	 * RemindTask: using a timer to perform a task, if task is near, user will received an email on the specific task
	 * 
	 * @author Khaleef
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

					// System.out.println("-------------------------");
					// System.out.println("Max Time : " + (currentTime + 5000));
					// System.out.println("Current Time : " + currentTime);
					// System.out.println("Min Time : " + (currentTime - 5000));
					// System.out.println("Storage Time : " + time);
					// System.out.println(t.displayTask());
					// System.out.println("-------------------------");

					if (time >= (currentTime - TEN_THOUSAND_CONST)
							&& (time <= currentTime + TEN_THOUSAND_CONST)) {
						// System.out.println(t.displayTask());
						Email.emailUser(t.displayTask());
					}

				}
			} catch (Exception e) {

			}
		}
	}
}
