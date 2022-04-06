package com.incture.cherrywork.services;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.incture.cherrywork.util.ApplicationConstantsM;



@Service
@Transactional
public class AttachEmail {
	
	
	@Autowired
	private AbbyOcrService abbyOcrService;
	
	Session session = null;
	static int count=0;
	Store store = null;
	Folder emailFolder = null;
	private static final Logger logger = LoggerFactory.getLogger(AttachEmail.class);
	public  Object receiveEmail() throws MessagingException{
		String host="imap-mail.outlook.com";
		String userName="Sandeep.k@incture.com\\CW.COM";
		String password="Rhythmsk1#";
		
		try {
			// If already opened and not closed
			logger.error("[try to close if already opened]");
			emailFolder.close();
			store.close();
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		Properties properties = new Properties();
		properties.setProperty("mail.imaps.auth.plain.disable", "true");
		properties.setProperty("mail.store.protocol", "imaps");
		
		

		session = javax.mail.Session.getInstance(properties);
		store = session.getStore("imaps");

		store.connect(ApplicationConstantsM.OUTLOOK_HOST, ApplicationConstantsM.OUTLOOK_PORT,
				userName, password);

		emailFolder = store.getFolder(ApplicationConstantsM.INBOX_FOLDER.toUpperCase());
		// use READ_ONLY if you don't wish the messages to be marked as read
		// after retrieving its content
		emailFolder.open(Folder.READ_WRITE);
//		List<String> emailFrom=new ArrayList<>();
//          emailFrom.add("Awadhesh Kumar awadhesh.kumar@incture.com");
//		    // Get the Session object"
	Message[] messages1 = getUnreadEmails();
	System.err.println("filteredEmails "+messages1.length);
	//Message[] filteredEmails1=fetchUnReadMessages(host,userName,password);
	
//		 
			//Retrieve the messages from the folder object.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message" +messages1.length);
			for(int i=0;i<messages1.length;i++)
			{
				List<File>lAttach= getAttachmentFromEmail(messages1[i]);
				if(lAttach.size()>0){
				System.err.println("attachment "+lAttach.get(0).getName());
				abbyOcrService.uploadDocToTrans(lAttach.get(0));
				}
				
			}	
		    
		   
		    return null;
	}
	
	private Message[] getUnreadEmails() throws MessagingException {
		logger.error("[Inside getUnreadEmail method]");
		Message[] messages = null;
		Message[] filteredEmails = null;
		
		

		Flags seen = new Flags(Flags.Flag.SEEN);
		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		messages = emailFolder.search(unseenFlagTerm);
		//filteredEmails = emailFolder.search(emailFromSearchTerm, messages);
		logger.error("[getUnreadEmail] Completed");

		return messages;
	}

	public Message[] fetchUnReadMessages(String host, String user, String password) {
  logger.error("[Inside fetchUnread}");
		try {
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "imaps");
			Session emailSession = Session.getDefaultInstance(properties);
			Store store = emailSession.getStore();
			store.connect(host, user, password);
			Folder emailFolder = store.getFolder("INBOX");
			
			// use READ_ONLY if you don't wish the messages to be marked as read
			// after retrieving its content
			emailFolder.open(Folder.READ_WRITE);
			// search for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			//String EMAIL_FROM = "Dipanjan Baidya <dipanjan.baidya@incture.com>";
			String EMAIL_FROM = "Sandeep Kumar <sk18021998@gmail.com>";
			// creates a search criterion
			SearchTerm searchCondition = new SearchTerm() {

				@Override
				public boolean match(Message msg) {
					Address[] addresses;
					try {
						addresses = msg.getFrom();
						for (int i = 0; i < addresses.length; i++) {
							if (addresses[i].toString().equalsIgnoreCase(EMAIL_FROM)) {
								return true;
							}
						}
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
					return false;
				}
			};

			// searchConditions
			Message[] messages = emailFolder.search(unseenFlagTerm);
			Message[] filteredMessages = emailFolder.search(searchCondition, messages);

			for (Message message : filteredMessages) {
				// check for the attachment
				getAttachmentFromEmail(message);
				//
				// pass the pdfs to the abbyy server.

				// after processing the email move to the processed folder.
			}
			logger.error("[fetchUnread completed]");
			return filteredMessages;
		} catch (Exception e) {
			logger.error("[ApAutomation][EmailRepository][downloadEmailAttachments][Exception] = fetchUnReadMessages"
					+ e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public List<File> getAttachmentFromEmail(Message message) {
		List<File> files = new ArrayList<>();
		try {
			logger.error(message.getSubject());
			logger.error(message.getFileName());
			//logger.error(message.getFlags());
			count++;
			System.err.println("count "+count);
			Multipart multiPart = (Multipart) message.getContent();
			int numberOfParts = multiPart.getCount();
			logger.error("number of parts = " + numberOfParts);
			Boolean isAttachmentFound = false;
			Boolean isAttachmentIsPdf = false;
			for (int partCount = 0; partCount < numberOfParts; partCount++) {
				MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
				
				logger.error("part.getDisposition()   ------------->"+part.getDisposition());
				if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
					isAttachmentFound = Boolean.TRUE;
					logger.error("isAttachmentFound  ----------->" + isAttachmentFound);
					String fileName = part.getFileName();
					logger.error("FileName::::::" + fileName);
					if ("pdf".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".") + 1))
							|| "json".equalsIgnoreCase(fileName.substring(fileName.lastIndexOf(".") + 1))) {
						
						isAttachmentIsPdf = Boolean.TRUE;
						logger.error("IF PDF FILE FOUND " + isAttachmentIsPdf);
						File file = new File(fileName);
						part.saveFile(file);
						files.add(file);
					}
				}
			}
			return files;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return files;
		}

	}

	// public static void main(String[] args) throws MessagingException {
	// Email email = new Email();
	// System.out.println("For the first method:::"+System.currentTimeMillis());
	// Message[] messages =
	// email.fetchUnReadMessagesFromSharedMailBox("outlook.office365.com", 993,
	// ApplicationConstants.SHARED_MAIL_ID_ALLIAS,
	// ApplicationConstants.ACCPAY_EMAIL_PASSWORD,
	// ApplicationConstants.INBOX_FOLDER, ApplicationConstants.UNSEEN_FLAGTERM,
	// ApplicationConstants.EMAIL_FROM);
	// for (Message message : messages) {
	// System.err.println(message.getFrom()[0]);
	//// email.moveMessage(message, "INBOX", "PROCESSED");
	// }
	// System.out.println("For the first method:::"+System.currentTimeMillis());
	//
	// }

	public void moveMessage(Message message, String inputFolderName, String outputFolderName) {

//		try {
//
//			Properties properties = new Properties();
//			properties.setProperty("mail.imaps.auth.plain.disable", "true");
//			Session session = Session.getInstance(properties);
//			Store store = session.getStore("imaps");
//			store.connect(ApplicationConstants.OUTLOOK_HOST, 993, ApplicationConstants.SHARED_MAIL_ID_ALLIAS,
//					ApplicationConstants.ACCPAY_EMAIL_PASSWORD);
//			Folder emailFolder = store.getFolder(inputFolderName);
//
//			// use READ_ONLY if you don't wish the messages to be marked as read
//			// after retrieving its content
//			emailFolder.open(Folder.READ_ONLY);
//			message.getFolder().copyMessages(new Message[] { message }, store.getFolder(outputFolderName));
//			message.setFlag(Flag.DELETED, true);
//			// message.getFolder().expunge();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

//	public Message[] fetchUnReadMessagesFromSharedMailBox(String host, Integer port, String user, String password,
//			String folderName, String flagTerm, List<String> emailFromList) {
//		Message[] filteredMessages = null;
//		Session session = null;
//		Store store = null;
//		Folder emailFolder = null;
//		try {
//			logger.error("inside fetchUnReadMessagesFromSharedMailBox");
//			Properties properties = new Properties();
//			properties.setProperty("mail.imaps.auth.plain.disable", "true");
//			properties.setProperty("mail.store.protocol", "imaps");
//			session = Session.getInstance(properties);
//			store = session.getStore("imaps");
//			store.connect(host, port, user, password);
//
//			emailFolder = store.getFolder(folderName.toUpperCase());
//			// use READ_ONLY if you don't wish the messages to be marked as read
//			// after retrieving its content
//			emailFolder.open(Folder.READ_WRITE);
//			SearchTerm emailFromSearchTerm = new SearchTerm() {
//
//				/**
//				 * 
//				 */
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				public boolean match(Message msg) {
//					Address[] addresses;
//					try {
//						addresses = msg.getFrom();
//						for (int i = 0; i < addresses.length; i++) {
//							// System.err.println(addresses[i]);
//							String addressFrom = addresses[i].toString().toLowerCase();
//							
//							for(String emailFrom : emailFromList){
//								if (addressFrom.contains(emailFrom.toLowerCase())
//										|| addresses[i].toString().equalsIgnoreCase(emailFrom)) {
//
//									return true;
//								}
//							}
//							
//						}
//					} catch (MessagingException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						return false;
//					}
//					return false;
//				}
//			};
//			Message[] messages = null;
//			if (ApplicationConstants.UNSEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
//				Flags seen = new Flags(Flags.Flag.SEEN);
//				FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
//				messages = emailFolder.search(unseenFlagTerm);
//			} else if (ApplicationConstants.SEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
//				Flags seen = new Flags(Flags.Flag.SEEN);
//				FlagTerm seenFlagTerm = new FlagTerm(seen, true);
//				messages = emailFolder.search(seenFlagTerm);
//			} else {
//				messages = emailFolder.getMessages();
//			}
////			for (Message message : messages) {
////				logger.error("Email Addresses::: " + message.getFrom()[0]);
////			}
//			// searchConditions
//			if (!(ServiceUtil.isEmpty(messages) && ServiceUtil.isEmpty(emailFromSearchTerm))) {
//				filteredMessages = emailFolder.search(emailFromSearchTerm, messages);
//			} else {
//				if (!ServiceUtil.isEmpty(messages)) {
//					filteredMessages = messages;
//				} else {
//					filteredMessages = emailFolder.search(emailFromSearchTerm);
//				}
//			}
//			logger.error("inside fetchUnReadMessagesFromSharedMailBox");
//
//			// for (Message message : filteredMessages) {
//			//
//			// message.getFolder().copyMessages(new Message[] { message },
//			// store.getFolder("PROCESSED"));
//			// message.setFlag(Flag.SEEN, true);
//			//// message.getFolder().expunge();
//			// }
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			try {
//				emailFolder.close();
//				store.close();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//				logger.error("Error in closing store and folder");
//			}
//		}
//		return filteredMessages;
//	}
//
//	public Message[] readEmail(String host, String user, String password, String folderName, String flagTerm) {
//
////		try {
////			Properties properties = new Properties();
////			properties.put("mail.store.protocol", "imaps");
////			Session emailSession = Session.getDefaultInstance(properties);
////			Store store = emailSession.getStore();
////			store.connect(host, user, password);
////			Folder emailFolder = store.getFolder(folderName);
////			// Folder outputFolder = store.getFolder("Processed");
////			// use READ_ONLY if you don't wish the messages to be marked as read
////			// after retrieving its content
////			emailFolder.open(Folder.READ_WRITE);
////			// search for all "unseen" messages
////			Message[] messages = null;
////			if (ApplicationConstants.UNSEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
////				Flags seen = new Flags(Flags.Flag.SEEN);
////				FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
////				messages = emailFolder.search(unseenFlagTerm);
////			} else if (ApplicationConstants.SEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
////				Flags seen = new Flags(Flags.Flag.SEEN);
////				FlagTerm seenFlagTerm = new FlagTerm(seen, true);
////				messages = emailFolder.search(seenFlagTerm);
////			} else {
////				messages = emailFolder.getMessages();
////			}
////			String EMAIL_FROM = "Dipanjan Baidya <dipanjan.baidya@incture.com>";
////			// creates a search criterion
////			SearchTerm searchCondition = new SearchTerm() {
////
////				@Override
////				public boolean match(Message msg) {
////					Address[] addresses;
////					try {
////						addresses = msg.getFrom();
////						for (int i = 0; i < addresses.length; i++) {
////							if (addresses[i].toString().equalsIgnoreCase(EMAIL_FROM)) {
////								return true;
////							}
////						}
////					} catch (MessagingException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////						return false;
////					}
////					return false;
////				}
////			};
////			Message[] filteredMessages = null;
////			// searchConditions
////			if (!(ServiceUtil.isEmpty(messages) && ServiceUtil.isEmpty(searchCondition))) {
////				filteredMessages = emailFolder.search(searchCondition, messages);
////			} else {
////				if (!ServiceUtil.isEmpty(messages)) {
////					filteredMessages = messages;
////				} else {
////					filteredMessages = emailFolder.search(searchCondition);
////				}
////			}
////			return filteredMessages;
////		} catch (Exception e) {
////			logger.error("[ApAutomation][EmailRepository][downloadEmailAttachments][Exception] = fetchUnReadMessages"
////					+ e.getMessage());
////			e.printStackTrace();
////		}
//		return null;
//	}
//
////	public List<File> getAttachmentsFromEmail(String host, Integer port, String user, String password,
////			String folderName, String flagTerm, String emailFrom) {
////		List<File> attachments = new ArrayList<>();
////		Message[] filteredMessages = null;
////		try {
////			Properties properties = new Properties();
////			properties.setProperty("mail.imaps.auth.plain.disable", "true");
////			Session session = Session.getInstance(properties);
////			Store store = session.getStore("imaps");
////			store.connect(host, port, user, password);
////
////			Folder emailFolder = store.getFolder(folderName.toUpperCase());
////			// use READ_ONLY if you don't wish the messages to be marked as read
////			// after retrieving its content
////			emailFolder.open(Folder.READ_WRITE);
////
////			SearchTerm emailFromSearchTerm = new SearchTerm() {
////
////				/**
////				 * 
////				 */
////				private static final long serialVersionUID = 1L;
////
////				@Override
////				public boolean match(Message msg) {
////					Address[] addresses;
////					try {
////						addresses = msg.getFrom();
////						for (int i = 0; i < addresses.length; i++) {
////							// System.err.println(addresses[i]);
////							if (addresses[i].toString().toLowerCase().contains(emailFrom.toLowerCase())
////									|| addresses[i].toString().equalsIgnoreCase(emailFrom)) {
////								return true;
////							}
////						}
////					} catch (MessagingException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////						return false;
////					}
////					return false;
////				}
////			};
////			Message[] messages = null;
////			if (ApplicationConstants.UNSEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
////				Flags seen = new Flags(Flags.Flag.SEEN);
////				FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
////				messages = emailFolder.search(unseenFlagTerm);
////			} else if (ApplicationConstants.SEEN_FLAGTERM.equalsIgnoreCase(flagTerm)) {
////				Flags seen = new Flags(Flags.Flag.SEEN);
////				FlagTerm seenFlagTerm = new FlagTerm(seen, true);
////				messages = emailFolder.search(seenFlagTerm);
////			} else {
////				messages = emailFolder.getMessages();
////			}
////			// searchConditions
////			if (!(ServiceUtil.isEmpty(messages) && ServiceUtil.isEmpty(emailFromSearchTerm))) {
////				filteredMessages = emailFolder.search(emailFromSearchTerm, messages);
////			} else {
////				if (!ServiceUtil.isEmpty(messages)) {
////					filteredMessages = messages;
////				} else {
////					filteredMessages = emailFolder.search(emailFromSearchTerm);
////				}
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			logger.error("Email.getAttachmentsFromEmail():::Error:" + e.getMessage());
////			// TODO: handle exception
////		} finally {
////
////		}
////		return attachments;
////	}
//
//	
//	
//	

}

