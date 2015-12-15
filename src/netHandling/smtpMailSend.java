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
	}

	public void postMail() throws MessagingException, AuthenticationFailedException, Exception
	{
		try{
			//Set the host smtp address
			Properties props = new Properties();
			props.put("mail.smtps.host", SMTP_HOST_NAME);
			props.put("mail.smtps.starttls.enable", "true");
			props.put("mail.smtps.ssl.enable", "true");
			props.put("mail.smtps.socketFactory.port", "465");
			props.put("mail.smtps.sokectFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtps.port", "465");
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getDefaultInstance(props, auth);
			Transport transport = session.getTransport("smtps");

			// Set to true to activate SMTP debugging
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
			msg.saveChanges();

			transport.connect();
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception ex){
			throw ex;
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator{
		@Override
		public PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		}
	}
}