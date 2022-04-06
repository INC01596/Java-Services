//package com.incture.cherrywork.services;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//import java.util.UUID;
//
//import javax.mail.Address;
//import javax.mail.Flags;
//import javax.mail.Flags.Flag;
//import javax.mail.Folder;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.Store;
//import javax.mail.search.FlagTerm;
//import javax.mail.search.SearchTerm;
//
//import org.apache.commons.io.FilenameUtils;
//import org.modelmapper.ModelMapper;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import com.google.gson.Gson;
//import com.incture.cherrywork.sales.constants.ApplicationConstants;
//import com.incture.cherrywork.util.ApplicationConstantsM;
//import com.incture.cherrywork.util.Email;
//import com.incture.cherrywork.util.ServicesUtil;
//
//@Service
//public class EmailReaderService {
//
//	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EmailReaderService.class);
//
//	
//
//	
//
//	@Autowired
//	Email emailUtil;
//	@Autowired
//	private AbbyOcrService abbyOcrService;
//	
//	@Autowired
//	DocumentModelService documentModelService;
//	
////	@Autowired
////	DestinationService destinationService;
//
//	javax.mail.Session session = null;
//	Store store = null;
//	Folder emailFolder = null;
//
////	List<SchedulerCycleLogDo> schedulerCycleLogDoList = new ArrayList<>();
////
////	List<InvoiceEmailDo> invoiceEmailList = new ArrayList<>();
//
//	public void extractInvoiceFromSharedEmailBox(SchedulerConfigurationDo entity) {
//		int noOfEmailspicked = 0;
//		int noOfAttachement = 0;
//		int noOfEmailsReadSuccessfully = 0;
//		int noOfPDFs = 0;
//		int noOfFilesSendToOCR = 0;
//		
//		Gson gson = new Gson();
//
//		logger.error("Email Scheduler Running");
//		
//		//ConfigurationDo currentConfig = configurationRepository.getVersion("CURRENT");
//		
//		///logger.error("Current configuration: "+currentConfig.toString());
//		
//		Integer emailPickupCount =0;
// 
//		//OCREngine ocrEngine = ocrEngineFactory.createOCREngine(currentConfig.getOcrEngine());
//		
//		//String ocrConfigurationJSON = destinationService.getConfiguration(currentConfig.getOcrEngine());
//		//logger.error("ocrConfigurationJSON " + ocrConfigurationJSON);
//		
////		String mailBoxConfigurationJSON = destinationService.getConfiguration(ApplicationConstants.EMAILBOX_DESTINATION_NAME);
////		logger.error("mailBoxConfigurationJSON: " + mailBoxConfigurationJSON);
////		
////		MailboxConfigurationDto mailboxConfiguration = gson.fromJson(mailBoxConfigurationJSON,
////				MailboxConfigurationDto.class);
//
////		SchedulerCycleDo cycleEntity = new SchedulerCycleDo();
////		cycleEntity.setStartDateTime(TimeUtil.getCurrentTimestamp());
////		cycleEntity.setSchedulerCycleID(UUID.randomUUID().toString());
////		Boolean lifeCycleStatus = false;
//		
//	//	Object ocrConfiguration = ocrEngineFactory.createOCRConfiguration(currentConfig.getOcrEngine(),ocrConfigurationJSON);
//
//		//boolean connectionEstablished = ocrEngine.establishConnectionToOCR(ocrConfiguration);
//
//		
//			try {
//				connectToEmailServerAndOpenEmailFolder();
////				Message[] unReadEmails = getUnreadEmails();
//				List<String> emailFrom = new ArrayList<String>();
//				emailFrom.add("sk18021998@gmail.com");
//				Message[] filteredEmails = getUnreadEmails(emailFrom);
//
//				if (ServicesUtil.isEmpty(filteredEmails)) {
//					logger.error("[No filteredEmails]");
//					//addCycleLogToList("No Email to read");
//				} else {
//
//					Message[] emailToRead = selectEmailToRead(filteredEmails,emailPickupCount);
//
//					if (ServicesUtil.isEmpty(emailToRead)) {
//						logger.error("Email toRead is empty");
////						lifeCycleStatus = false;
////						addCycleLogToList("Email toRead is empty");
//					} else {
//
//						noOfEmailspicked = emailToRead.length;
//						logger.error("noOfEmailspicked" + noOfEmailspicked);
//
//						for (Message email : emailToRead) {
//							email.setFlag(Flag.SEEN, true);
//							Date emailReceivedDate = email.getReceivedDate();
//							logger.error("[Email Recieved at] : " + emailReceivedDate);
//
//							String emailSubject = email.getSubject();
//							String emailSender = "" + email.getFrom()[0];
//							logger.error("[emailSubject]" + emailSubject);
//							logger.error("[emailSender]" + emailSender);
//							//addCycleLogToList("Reading Email With Subject :" + emailSubject + " from " + emailSender);
//
//							noOfEmailsReadSuccessfully = noOfEmailsReadSuccessfully++;
//
//							String contentType = email.getContentType();
//
//							if (!contentType.contains("multipart")) {
//								//lifeCycleStatus = true;
//								logger.error("No Attachment found");
//								//addCycleLogToList("No Attachment found");
//							} else {
//
//								Multipart multiPart = (Multipart) email.getContent();
//								noOfAttachement += (multiPart.getCount() - 1);
//								logger.error("No of attachments" + noOfAttachement);
//
//								List<File> filesFromEachMail = new ArrayList<File>();
//								filesFromEachMail = emailUtil.getAttachmentFromEmail(email);
//								logger.error("No of filesFromEachMail" + filesFromEachMail.size());
//
//								if (ServicesUtil.isEmpty(filesFromEachMail)) {
//									logger.error("No PDF Attachment found");
//									//lifeCycleStatus = true;
//									//addCycleLogToList("No PDF Attachment found");
//								} else {
//
//									noOfPDFs += filesFromEachMail.size();
//									logger.error("noOfEmailspicked" + noOfEmailspicked);
//
//									for (File file : filesFromEachMail) {
//										//String TranscrionId = ocrEngine.createTranscrionId();
//
////										String requestId = seqService
////												.getSequenceNoByMappingId(ApplicationConstants.INVOICE_SEQUENCE, "INV");
////										logger.error("Request ID :" + requestId + " for the file " + file.getName());
//
//										// Rename file name to requestId
//										File fileToUpload = null;
////										fileToUpload = renameFile(file,
////												requestId + "." + FilenameUtils.getExtension(file.getAbsolutePath()));
////										addCycleLogToList(file.getName() + " renaming to " + fileToUpload.getName());
////
////										UploadFileResponse uploadResponse = ocrEngine.uploadFileToOCR(fileToUpload,
////												requestId);
////
////										noOfFilesSendToOCR++;
//
//										//ocrEngine.startProcessing();
//
//										ModelMapper modelMapper = new ModelMapper();
//										//TransactionDetailsResponse transactionDetailsResponse = ocrEngine
//												//.getTransctonDetails(requestId);
//									//	OcrTranscationDetailsDo transactionDetails = modelMapper
//												//.map(transactionDetailsResponse, OcrTranscationDetailsDo.class);
//										
////										Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
////										logger.error("File: "+requestId+"Processing start: " + currentTimestamp);
////										transactionDetails.setFileProcessingStartTimestamp(currentTimestamp);
////																		
////										ocrDetailsRepository.save(transactionDetails);
////
////										addCycleLogToList(uploadResponse.getMessage());
////										logger.error("[uploadResponse]" + uploadResponse.getMessage());
//
////										if (!uploadResponse.isUploaded()) {
////											logger.error("file failed uploading");
////											break;
////										}
//
////										addToInvoiceEmailList(emailReceivedDate, emailSubject, emailSender, requestId,
////												fileToUpload,cycleEntity.getSchedulerCycleID());
////
////										// Add to DMS
////										documentModelService.uploadFile(fileToUpload, requestId);
////										
//										
////										AttachmentDo attachmentDo = new AttachmentDo();
////										attachmentDo.setAttachmentId(UUID.randomUUID().toString());
////										attachmentDo.setRequestId(requestId);
////										attachmentDo.setCreatedAt(ServiceUtil.getEpocTime());
////										attachmentDo.setCreatedBy(emailSender);
////										attachmentDo.setFileName(fileToUpload.getName());
////										attachmentDo.setFileBase64(ServiceUtil.convertFileToBase64(fileToUpload));
////										attachmentDo
////												.setFileType(ServiceUtil.getFileExtension(attachmentDo.getFileName()));
////										attachmentRepository.save(attachmentDo);
//
//										logger.error("file saved in attachments");
//									} // file for loop
//								} // File from each mail
//							} // Multipart Email
//						} // Email for loop
//							// After email for loop
//						logger.error("Setting as seen");
//						emailFolder.setFlags(emailToRead, new Flags(Flags.Flag.SEEN), true);
//						logger.error("Saving email list");
//						//invoiceEmailRepository.saveAll(invoiceEmailList);
//					} // Email to read
//				} // Filtered Emails
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("[Error with extractInvoiceFromEmail" + e.getMessage());
//				//lifeCycleStatus = false;
//				//addCycleLogToList("Error : " + e.getMessage());
//			} finally {
//				try {
//					// Clean up
//					emailFolder.close();
//					store.close();
//					logger.error("email folder closed and store colsed");
//
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//					logger.error("Error in finnaly block" + e.getMessage());
//				}
//			}
//		}
//	
//
////	private void addToInvoiceEmailList(Date emailReceivedDate, String emailSubject, String emailSender,
////			String requestId, File fileToUpload, String cycleId) {
////		InvoiceEmailDo emailEntity = new InvoiceEmailDo();
////		emailEntity.setGuid(UUID.randomUUID().toString());
////		emailEntity.setCreatedAt(ServiceUtil.getEpocTime());
////		emailEntity.setEmailFrom(emailSender);
////		emailEntity.setEmailReceivedAt(ServiceUtil.getEpocTimeFromDate(emailReceivedDate));
////		emailEntity.setEmailSubject(emailSubject);
////		emailEntity.setRequestId(requestId);
////		emailEntity.setPdfName(fileToUpload.getName());
////		emailEntity.setSchedulerCycleID(cycleId);
////		invoiceEmailList.add(emailEntity);
////	}
//
//	public static File renameFile(File toBeRenamed, String new_name) throws IOException {
//		// need to be in the same path
//		File fileWithNewName = new File(toBeRenamed.getParent(), new_name);
//		if (fileWithNewName.exists()) {
//			throw new IOException("file exists");
//		}
//		// Rename file (or directory)
//		boolean success = toBeRenamed.renameTo(fileWithNewName);
//		if (success) {
//			return fileWithNewName;
//
//		} else {
//			throw new IOException();
//		}
//	}
//
//	private Message[] selectEmailToRead(Message[] filteredEmails, Integer emailPickupCount) {
//		Message[] emailToRead = null;
//		if (!ServicesUtil.isEmpty(filteredEmails)) {
//			if (filteredEmails.length < emailPickupCount) {
//				emailToRead = new Message[filteredEmails.length];
//			} else {
//				emailToRead = new Message[emailPickupCount];
//			}
//			logger.error("[selectEmailToRead] :" + emailToRead);
//			if (!ServicesUtil.isEmpty(emailToRead)) {
//				for (int i = 0; i < emailToRead.length; i++) {
//					emailToRead[i] = filteredEmails[i];
//				}
//			}
//			logger.error("[selectEmailToRead] [noOfEmailspicked] : " + emailToRead.length);
//		} else {
//			//addCycleLogToList("No filteredEmails");
//		}
//		return emailToRead;
//	}
//
////	private void addCycleLogToList(String Message) {
////		SchedulerCycleLogDo cycleLogDo = new SchedulerCycleLogDo();
////		cycleLogDo.setUuId(UUID.randomUUID().toString());
////		cycleLogDo.setLogMsgText(ServiceUtil.tochar(Message));
////		cycleLogDo.setTimestampIST(ServiceUtil.getCurrentDateByZone(ApplicationConstants.IST_TIMEZONE));
////		cycleLogDo.setTimestampKSA(ServiceUtil.getCurrentDateByZone(ApplicationConstants.KSA_TIMEZONE));
////		schedulerCycleLogDoList.add(cycleLogDo);
////	}
//
////	private Message[] filterEmailBasedOnFrom(Message[] unReadEmails, List<String> emailFrom) throws MessagingException {
////		Message[] filteredEmails = null;
////
////		SearchTerm emailFromSearchTerm = new SearchTerm() {
////			private static final long serialVersionUID = 1L;
////
////			@Override
////			public boolean match(Message msg) {
////				Address[] addresses;
////				try {
////					addresses = msg.getFrom();
////					for (int i = 0; i < addresses.length; i++) {
////						String addressFrom = addresses[i].toString().toLowerCase();
////						for (String emailFrom : emailFrom) {
////							if (addressFrom.contains(emailFrom.toLowerCase())
////									|| addresses[i].toString().equalsIgnoreCase(emailFrom)) {
////								return true;
////							}
////						}
////
////					}
////				} catch (MessagingException e) {
////					e.printStackTrace();
////					return false;
////				}
////				return false;
////			}
////		};
////
////		if (!ServiceUtil.isEmpty(unReadEmails) && !ServiceUtil.isEmpty(emailFromSearchTerm)) {
////			filteredEmails = emailFolder.search(emailFromSearchTerm, unReadEmails);
////			logger.error("[filterEmailBasedOnFrom] [filteredMessages] :" + filteredEmails);
////		} else {
////			if (!ServiceUtil.isEmpty(unReadEmails)) {
////				filteredEmails = unReadEmails;
////			} else {
////				filteredEmails = emailFolder.search(emailFromSearchTerm);
////			}
////			logger.error("[filterEmailBasedOnFrom] [filteredMessages] [else] :" + filteredEmails);
////
////		}
////		return filteredEmails;
////	}
//
//	private Message[] getUnreadEmails(List<String> emailFrom) throws MessagingException {
//		Message[] messages = null;
//		Message[] filteredEmails = null;
//		
//		SearchTerm emailFromSearchTerm = new SearchTerm() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public boolean match(Message msg) {
//				Address[] addresses;
//				try {
//					addresses = msg.getFrom();
//					for (int i = 0; i < addresses.length; i++) {
//						String addressFrom = addresses[i].toString().toLowerCase();
//						for (String emailFrom : emailFrom) {
//							if (addressFrom.contains(emailFrom.toLowerCase())
//									|| addresses[i].toString().equalsIgnoreCase(emailFrom)) {
//								return true;
//							}
//						}
//
//					}
//				} catch (MessagingException e) {
//					e.printStackTrace();
//					return false;
//				}
//				return false;
//			}
//		};
//
//		Flags seen = new Flags(Flags.Flag.SEEN);
//		FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
//		messages = emailFolder.search(unseenFlagTerm);
//		filteredEmails = emailFolder.search(emailFromSearchTerm, messages);
//		return filteredEmails;
//	}
//
//	private void connectToEmailServerAndOpenEmailFolder(MailboxConfigurationDto mailboxConfiguration) throws MessagingException {
//		try {
//			// If already opened and not closed
//			logger.error("[try to close if already opened]");
//			emailFolder.close();
//			store.close();
//		} catch(Exception e) {
//			logger.error(e.getMessage());
//		}
//		
//		Properties properties = new Properties();
//		properties.setProperty("mail.imaps.auth.plain.disable", "true");
//		properties.setProperty("mail.store.protocol", "imaps");
//
//		session = javax.mail.Session.getInstance(properties);
//		store = session.getStore("imaps");
//
//		store.connect(ApplicationConstantsM.OUTLOOK_HOST, ApplicationConstantsM.OUTLOOK_PORT,
//				mailboxConfiguration.getUserName(), mailboxConfiguration.getPassword());
//
//		emailFolder = store.getFolder(ApplicationConstantsM.INBOX_FOLDER.toUpperCase());
//		// use READ_ONLY if you don't wish the messages to be marked as read
//		// after retrieving its content
//		emailFolder.open(Folder.READ_WRITE);
//	}
//}
