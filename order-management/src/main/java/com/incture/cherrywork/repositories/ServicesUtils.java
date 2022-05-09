package com.incture.cherrywork.repositories;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.incture.cherrywork.dtos.ItemDataInReturnOrderDto;

public class ServicesUtils {

	public static boolean isEmpty(String str) {

		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(BigDecimal o) {
		if (o == null || BigDecimal.ZERO.compareTo(o) == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		return false;
	}

	public static String listToString(List<String> list) {
		String response = "";
		try {
			for (String s : list) {
				response = "'" + s + "', " + response;
			}
			response = response.substring(0, response.length() - 2);
		} catch (Exception e) {
			System.err.println("[ServicesUtils][listToString] Exception : " + e.getMessage());
			e.printStackTrace();
		}
		return response;
	}

	public static String DateToString(Date date) {
		if (date == null) {
			return null;
		}

		String newstr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return newstr;
	}

	static public ResponseEntity<Object> mail(List<String> receiver, List<String> receiverInCc, String name,
			String subject, String text) {

		String FROM_MAIL_ID = "awadhesh.kumar@incture.com";
		String FROM_MAIL_ID_PASSWORD = "aW1533@9#";
		String MAIL_HOST_NAME = "smtp.office365.com";// "smtp.gmail.com"; (In
														// case
														// FROM_MAIL_ID is
														// outlook
														// email)
		String MAIL_PORT_NUMBER = "587";
		// String FROM_MAIL_ID = "notification@sulb.com.bh";
		// String FROM_MAIL_ID_PASSWORD = "P@ssw0rd";
		// String MAIL_HOST_NAME = "mail.sulb.com.bh";
		// String MAIL_PORT_NUMBER = "25";

		// String APP_URL =
		// "https://foulath-holding-bsc-foulathdev-cesp-app-login.cfapps.eu10.hana.ondemand.com/Login/index.html#";
		// String APP_URL =
		// "https://foulath-foulathqas-qas-unit-login.cfapps.eu10.hana.ondemand.com/Login/index.html";
		// String APP_URL =
		// "https://foulath-foulathprd-prd-unit-login.cfapps.eu10.hana.ondemand.com";
		String APP_URL = "https://incture-technologies-hrapps-com-dev-mta-com-approuter.cfapps.eu10.hana.ondemand.com/cominctureCOM/index.html#/ManageOrder";
		// String APP_URL = "http://192.168.1.8:8080";

		Properties prop = new Properties();
		prop.put("mail.smtp.host", MAIL_HOST_NAME);
		prop.put("mail.smtp.port", MAIL_PORT_NUMBER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", MAIL_PORT_NUMBER);
		// prop.put("mail.smtp.socketFactory.class",
		// "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_MAIL_ID, FROM_MAIL_ID_PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(FROM_MAIL_ID));
			System.out.println("receiver: " + receiver);
			for (String receiverEmail : receiver) {
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
			}
			if (!receiverInCc.isEmpty())
				for (String userInCc : receiverInCc) {
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(userInCc));
				}
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("awadhesh.kumar@incture.com"));
			message.setSubject(subject);
			message.setText("Hi " + name + ",\n\n" + text + " \n\n " + "Application URL :" + APP_URL);
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		// return ResponseEntity<Object>.status(HttpStatus.OK).header("message",
		// "Mail Sent Successfully to " + receiver).body(null);
		return ResponseEntity.status(HttpStatus.SC_ACCEPTED).header("message", "Mail Sent Successfully to " + receiver)
				.body(null);
	}

	public static boolean isPresent(int index, String orderId, String material, List<ItemDataInReturnOrderDto> list) {
		System.err.println("isPresent Starts with index: " + index + " orderId: " + orderId + " material: " + material);
		int i = 0;
		for (ItemDataInReturnOrderDto item : list) {
			if (i != index) {
				System.err.println("in for loop in service utils ispresent(), orderNum: " + item.getOrderNum()
						+ " material: " + item.getMaterialNum() + " i: " + i);
				if ((item.getOrderNum().equals(orderId)) && (item.getMaterialNum().equals(material)))
					return true;
			}
			i++;
		}
		return false;
	}

}
