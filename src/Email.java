//@author A0097961M

public class Email {
	private static final String NAME = "TaskTracker";
	private static final String EMAIL = "tasktrackernus@gmail.com";
	private static final String PASSWORD = "112233445566778899NUS";
	private static final String SALUTATIONS = "Dear Sir or Madam,\n\n";
	private static final String DETAILS = "Your notifications details is as follow:\n";
	private static final String REGARDS = "Regards,\nTaskTracker";
	private static final String SUBJECT = "Reminder From TaskTracker";
	private static final String EMAIL_K = "khaleef1990@gmail.com";
	private static final String EMAIL_R = "hellorichardpham@gmail.com";
	private static final String EMAIL_Y = "tyingyun@hotmail.com";
	private static final String EMAIL_J = "tianweizhou@gmail.com";

	// @author A0097961M
	/*
	 * emailUser: set up of the email name, host, password, recipients
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

		String subject = SUBJECT;

		String messageBody = SALUTATIONS + DETAILS + task_Info + "\n\n" + REGARDS;

		if (new MailUtil().sendMail(recipients, subject, messageBody, name,
				email, password, host)) {
		}
	}
}
