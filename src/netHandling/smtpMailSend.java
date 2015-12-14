package netHandling;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class smtpMailSend
{
	private static final String SMTP_HOST_NAME = "smtp.openmailbox.org";
	private static final String SMTP_AUTH_USER = "ScoachBot@openmailbox.org";
	private static final String SMTP_AUTH_PWD  = "eFx3s5792VuZ";
	private final String fromEmailAddress;
	private final String subjectTxt;
	private final String msgTxt;
	
	// Add List of Email address to who email needs to be sent to
	private final String[] toEmailList;

	public smtpMailSend(String[] to, String from, String subject, String message)
	{
		toEmailList = to.clone();
		fromEmailAddress = from;
		subjectTxt = subject;
		msgTxt = message;
		
		// smtpMailSender.postMail( toEmailList, emailSubjectTxt, emailMsgTxt, emailFromAddress);
		// System.out.println("Sucessfully Sent mail to All Users");
	}

	public void postMail() throws MessagingException, AuthenticationFailedException
	{
		//Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.sokectFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		
		session.setDebug(true);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(fromEmailAddress);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[toEmailList.length];
		for (int i = 0; i < toEmailList.length; i++)
			addressTo[i] = new InternetAddress(toEmailList[i]);
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subjectTxt);
		msg.setText(msgTxt);
		Transport.send(msg);
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator{
		@Override
		public PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		}
	}
}