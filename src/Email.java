import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Email {

	private Scanner input;
	@SuppressWarnings("unused")
	private ArrayList<Task> taskList;

	public Email(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public void emailUser() throws Exception {

		String name = "TaskTracker";
		String email = "tasktrackernus@gmail.com ";
		String password = "112233445566778899NUS";
		String recipient;

		// default host
		String host = "smtp.gmail.com";

		input = new Scanner(System.in);
		System.out.println("Please enter recipient email: ");
		recipient = input.nextLine();

		if (email.contains("gmail.com")) {
			host = "smtp.gmail.com";
		} else if (email.contains("hotmail.com") || email.contains("live.com")) {
			host = "smtp.live.com";
		}

		String[] recipients = new String[] { recipient };
		String subject = "Storage.txt";

		String messageBody;

		BufferedReader br = new BufferedReader(new FileReader("Storage.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append("\n");
			line = br.readLine();
		}

		messageBody = sb.toString();
		br.close();
		
		try {
			System.out.println("Sending email. Please wait.");
			if (new MailUtil().sendMail(recipients, subject, messageBody, name,
					email, password, host))
				System.out.println("Email sent. Thank you for waiting.");
		} catch (Exception e) {

		}

	}
}
