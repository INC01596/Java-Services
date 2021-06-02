package com.incture.cherrywork.util;

import java.util.Calendar;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MailAlertUtil {

	private static final Logger logger = LoggerFactory.getLogger(MailAlertUtil.class);

	private static final String MAIL_UTF8_CONTENT_TYPE = "text/html; charset=utf-8";
	private final String EMAIL_ALERT_ON = "TRUE";
	private final String EMAIL_ALERT_OFF = "FALSE";
	private final String SMTP_AUTH = "mail.smtp.auth";
	private final String SMTP_TTLS = "mail.smtp.starttls.enable";
	private final String MAIL_HOST = "mail.smtp.host";
	private final String SMTP_PORT = "mail.smtp.port";
	private final String TRANSPORT_PROTOCOL = "mail.transport.protocol";
	private final String BOUNCER_ID = "xyz@abc.com";
	private final String BOUNCER_PORT = "mail.smtp.from";
	private final String SENDER_MAILID = "awadhesh.kumar@incture.com";
	private final String SENDER_PASSWORD = "aW1533@9#";	
	private final String SUCCESS = "Success";
	private final String FAILURE = "Failure";

	public String sendMailAlert(String receipentId,String receipentIdCC, String subjectName, String message, String userName ) {

		Properties props = new Properties();
		props.put(SMTP_AUTH, "true");
		props.put(SMTP_TTLS, "true");
		props.put(MAIL_HOST, "outlook.office365.com");
		props.put(SMTP_PORT, "587");
		props.put(TRANSPORT_PROTOCOL,"smtp");
		props.put(BOUNCER_PORT,BOUNCER_ID);
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER_MAILID, SENDER_PASSWORD);
			}
		});
		
		logger.debug("session " + session);
		MimeMessage mimeMesg = new MimeMessage(session);
		try {
			mimeMesg.setFrom(new InternetAddress(SENDER_MAILID));

			getMailBody(receipentId,receipentIdCC, mimeMesg, subjectName, message, userName);
			Transport.send(mimeMesg);

		} catch (Exception e) {
			logger.error("[sendMailtoClient]:Exception while sending mail " + e.getMessage());
			e.printStackTrace();
            return FAILURE;
		}
		return SUCCESS;
	}
	
	
	
	private void getMailBody(String receipentId,String receipentIdCC, MimeMessage message, String subjectName, String messagebody,
			String userName) {
		Object msg=null;
	
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("CST"));
			// For TO in mail
			InternetAddress[] parse = InternetAddress.parse(receipentId);
			message.setRecipients(Message.RecipientType.TO, parse);
			// For CC in mail
			if(!HelperClass.checkString(receipentIdCC))
				message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(receipentIdCC));
			message.setSubject(subjectName);
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			
			String [] splitedMessage = messagebody.split("To");
			
			msg = 	"<html><body>" + "Dear Sir" + ",</br>" +""+"</br>"+ splitedMessage[0]+"</br>"+
                           splitedMessage[1]+"</br></br>Thank you.</br></br>"
					+ "</br><font color=grey>This is a system generated e-mail. Do-not reply.</font></br>"
					+ "</body></html>";
			
			messageBodyPart.setContent(msg, MAIL_UTF8_CONTENT_TYPE);
			multipart.addBodyPart(messageBodyPart);
			
			message.setContent(multipart);
			message.saveChanges();
	
		} catch (Exception ex) {
			logger.error("Exception While Sending Mail Alert" + ex.getMessage());
			ex.printStackTrace();
		}
	}
}