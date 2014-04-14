import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

//@author A0097961M

public class Email {
	private static final String NAME = "TaskTracker";
	private static final String EMAIL = "tasktrackernus@gmail.com";
	private static final String PASSWORD = "112233445566778899NUS";
	private static final String SALUTATIONS = "Dear Sir or Madam,\n\n";
	private static final String DETAILS = "Your notifications details is as follow:\n";
	private static final String REGARDS = "Regards,\nTaskTracker";
	private static final String SUBJECT = "Reminder From TaskTracker";
	
	private static String userEmail;
	private static Email theEmail;
	
	public static Email getTheEmailInstance() throws FileNotFoundException {
		if(theEmail == null) {
			theEmail = new Email();
			userEmail = "";
		}
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader("emailAddress.txt"));
		} catch (FileNotFoundException e) {
			PrintWriter writer = new PrintWriter("emailAddress.txt");
			input = new BufferedReader(new FileReader("emailAddress.txt"));
			writer.close();
		}
		try {
			userEmail = input.readLine();
		} catch (IOException e) {
		}
		return theEmail;
	}
	
	//A0083093E
	//constructor
	public static void setUserEmail(String userEm) {
		userEmail = userEm;
	}
	
	//A0083093E
	public static String getUserEmail() {
		return userEmail;
	}
	
	// @author A0097961M
	/*
	 * emailUser: set up of the email name, host, password, recipients
	 * 
	 * @param Task
	 * 
	 * @return void
	 */
	public void emailUser(String task_Info) throws Exception {
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
		String[] recipients = new String[] { userEmail };

		String subject = SUBJECT;

		String messageBody = SALUTATIONS + DETAILS + task_Info + "\n\n" + REGARDS;

		if (new MailUtil().sendMail(recipients, subject, messageBody, name,
				email, password, host)) {
		}
	}
}
