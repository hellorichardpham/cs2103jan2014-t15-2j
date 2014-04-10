import java.util.ArrayList;

public class Email {
	private static final String NAME = "TaskTracker";
	private static final String EMAIL = "tasktrackernus@gmail.com";
	private static final String PASSWORD = "112233445566778899NUS";
	private static final String SUBJECT = "Reminder from TaskTracker";
	private static final String EMAIL_K = "khaleef1990@gmail.com";
	private static final String EMAIL_R = "hellorichardpham@gmail.com";
	private static final String EMAIL_Y = "tyingyun@hotmail.com";
	private static final String EMAIL_J = "tianweizhou@gmail.com";
	private static final String EMAIL_SUCCESS_MESSAGE = "Email sent. Thank you for waiting.";

	private static ArrayList<Task> taskList;

	public Email(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	/*
	 * emailUser: configurations of the email name, host, information, etc
	 * 
	 * @author A0097961M
	 * 
	 * @param Task
	 * 
	 * @return void
	 */
	public static void emailUser(String task_Info) throws Exception {

		String name = NAME;
		String email = EMAIL;
		String password = PASSWORD;

		// default host
		String host = "smtp.gmail.com";

		// if (email.contains("gmail.com")) {
		// host = "smtp.gmail.com";
		// } else if (email.contains("hotmail.com") ||
		// email.contains("live.com")) {
		// host = "smtp.live.com";
		// }

		// List of recipients to email
		String[] recipients = new String[] { EMAIL_K, EMAIL_R, EMAIL_Y, EMAIL_J };

		// String subject = "Storage.txt";
		String subject = SUBJECT;

		String messageBody = task_Info;

		taskList = ExeCom.getTaskListInstance();

		// System.out.println("Sending email. Please wait.");
		if (new MailUtil().sendMail(recipients, subject, messageBody, name,
				email, password, host)) {
			// ExeCom.setFeedback(EMAIL_SUCCESS_MESSAGE);
		}
	}

}
