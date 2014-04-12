/**
 * Taken from http://codesstore.blogspot.sg/2011/10/send-email-in-java-with-gmail.html
 * Part of the util class for email to work
 * 
 * @author A0097961M-reused
 * 
 */

import java.io.UnsupportedEncodingException;  
import java.util.Properties;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  

public class MailUtil {

	public boolean sendMail(String[] recipients, String subject,
			String message, String FROM_NAME, String FROM_ADDRESS,
			String PASSWORD, String SMTP_HOST) {
		try {
			
			final String email = FROM_ADDRESS;
			final String pwd = PASSWORD;
			
			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "false");
			props.put("mail.smtp.ssl.enable", "true");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(email,
									pwd);
						}
					});
			Message msg = new MimeMessage(session);

			InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
			msg.setFrom(from);

			InternetAddress[] toAddresses = new InternetAddress[recipients.length];
			for (int i = 0; i < recipients.length; i++) {
				toAddresses[i] = new InternetAddress(recipients[i]);
			}
			msg.setRecipients(Message.RecipientType.TO, toAddresses);

			msg.setSubject(subject);
			msg.setContent(message, "text/plain");
			Transport.send(msg);
			return true;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null,
					ex);
			return false;

		} catch (MessagingException ex) {
			Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null,
					ex);
			return false;
		}
	}

}