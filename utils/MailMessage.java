package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;

public class MailMessage {
	private String host;
	private int port;
	private String username;
	private String password;
	private Properties props;
	
	public void init(String type) {
		if(type != "QQ" || type != "CUGB")
			type = "QQ";
		InputStream in = MailMessage.class.getClassLoader().getResourceAsStream("mailsender.properties");
		Properties sender = new Properties();
		try {
			sender.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		host = sender.getProperty(type + "Host");
		port = Integer.parseInt(sender.getProperty(type + "Port"));
		username = sender.getProperty(type + "Username");
		password = sender.getProperty(type + "Password");
		props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		props.put("mail.smtp.port", port);
	}
	
	public MimeMessage createMessage(String to, String subject, String content)
	 throws MessagingException{
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		MimeMessage msg =  new MimeMessage(session);
		msg.setFrom(new InternetAddress(username));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		MimeMultipart mm = new MimeMultipart();
		MimeBodyPart mbp = new MimeBodyPart();
		mbp.setContent(content, "text/html;charset=UTF-8");
		mm.addBodyPart(mbp);
		msg.setContent(mm);
		
		return msg;
	}
}
