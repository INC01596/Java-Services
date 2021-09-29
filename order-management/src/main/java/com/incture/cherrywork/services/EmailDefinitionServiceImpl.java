package com.incture.cherrywork.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.incture.cherrywork.WConstants.MailNotificationAppConstants;
import com.incture.cherrywork.dtos.ApplicationVariablesMasterDto;
import com.incture.cherrywork.dtos.EmailUiDto;
import com.incture.cherrywork.dtos.MailTriggerDto;
import com.incture.cherrywork.dtos.RecipientDto;
import com.incture.cherrywork.dtos.ResponseDto;
import com.incture.cherrywork.dtos.UserDto;
import com.incture.cherrywork.dtos.VariableDto;
import com.incture.cherrywork.entities.ApplicationVariablesMasterDo;
import com.incture.cherrywork.entities.ApplicationVariablesMasterDoPK;
import com.incture.cherrywork.entities.EmailDefinitionDo;
import com.incture.cherrywork.entities.RecipientDo;
import com.incture.cherrywork.repositories.ApplicationVariablesMasterRepo;
import com.incture.cherrywork.repositories.EmailDefinitionProcessMappingRepo;
import com.incture.cherrywork.repositories.EmailDefinitionRepo;
import com.incture.cherrywork.repositories.RecipientRepo;
import com.incture.cherrywork.util.CommonMethods;
import com.incture.cherrywork.util.EmailUtils;
import com.incture.cherrywork.util.ServicesUtil;

@Service
@Transactional
public class EmailDefinitionServiceImpl implements EmailDefinitionService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EmailDefinitionRepo emailDefinitionRepo;

	@Autowired
	private EmailDefinitionProcessMappingRepo emailDefinitionProcessMappingRepo;

	@Autowired
	private RecipientRepo recipientRepo;

	@Autowired
	private EmailUtils emailUtils;
	
	

	@Autowired
	private ApplicationVariablesMasterRepo applicationVariablesMasterRepo;

	@Override
	public ResponseDto createEmailTemplate(EmailUiDto emailUiDto) {
		logger.info(" [EmailDefinitionServiceImpl]|[createEmailTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			Pageable pageable;
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
			Page<EmailDefinitionDo> page = null;
			page = emailDefinitionRepo.getEmailDefinitionBasedOnDetails(emailUiDto.getApplication(), null, null,
					emailUiDto.getName(), null, pageable);
			List<EmailDefinitionDo> emailDefinitionDoList = page.getContent();

//			List<EmailDefinitionDo> emailDefinitionDoList = emailDefinitionRepo.getEmailDefinitionBasedOnDetails(
//					emailUiDto.getApplication(), null, null, emailUiDto.getName(), null,pageable);

			if (ServicesUtil.isEmpty(emailDefinitionDoList)) {
				EmailDefinitionDo emailDefinitionDo = new EmailDefinitionDo();
				BeanUtils.copyProperties(emailUiDto, emailDefinitionDo);
				if (emailUiDto.getStatus() != null && emailUiDto.getStatus().equals("Active")) {
					emailDefinitionRepo.updateStatus(emailUiDto.getApplication(), emailUiDto.getProcess(),
							emailUiDto.getEntity(), "Deactivated", "Active");
				}
				EmailDefinitionDo entity = emailDefinitionRepo.save(emailDefinitionDo);
				// if (!ServicesUtil.isEmpty(emailUiDto.getProcessList())) {
//					List<EmailDefinitionProcessMappingDo> emailDefinitionProcessMappingDos = new ArrayList<EmailDefinitionProcessMappingDo>();
//					emailUiDto.getProcessList().forEach(obj -> {
//						obj.setEmailDefinitionId(entity.getEmailDefinitionId());
//						emailDefinitionProcessMappingDos.add(emailDefinitionProcessMappingRepo.importDto(obj));
//					});
//					emailDefinitionProcessMappingRepo.saveAll(emailDefinitionProcessMappingDos);
//				}

				List<RecipientDto> recipientDtoList = new ArrayList<RecipientDto>();
				getRecipientsAccordingToType(emailUiDto.getToList(), MailNotificationAppConstants.TO,
						entity.getEmailDefinitionId(), recipientDtoList);
				getRecipientsAccordingToType(emailUiDto.getCcList(), MailNotificationAppConstants.CC,
						entity.getEmailDefinitionId(), recipientDtoList);
				getRecipientsAccordingToType(emailUiDto.getBccList(), MailNotificationAppConstants.BCC,
						entity.getEmailDefinitionId(), recipientDtoList);
				List<RecipientDo> recipientDoList = recipientRepo.importDto(recipientDtoList);
				if (!ServicesUtil.isEmpty(recipientDoList)) {
					recipientRepo.saveAll(recipientDoList);
				}
				emailUiDto.setEmailDefinitionId(entity.getEmailDefinitionId());
				responseDto.setMessage("Email definition created successfully !");
			} else {
				responseDto.setStatus(Boolean.FALSE);
				responseDto.setMessage(
						"Email Template with Same name already Exist for Application " + emailUiDto.getApplication());
			}

			// responseDto.setData(emailUiDto);

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[createEmailTemplate]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}

		logger.info(" [EmailDefinitionServiceImpl]|[createEmailTemplate]  Execution end ");
		return responseDto;
	}

	@Override
	public ResponseDto updateEmailTemplate(String emailDefinitionId, EmailUiDto emailUiDto) {
		logger.info(" [EmailDefinitionServiceImpl]|[updateEmailTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			emailDefinitionRepo.findById(emailDefinitionId).ifPresent(entity -> {
				List<EmailDefinitionDo> emailDefinitionDoWithSameName = emailDefinitionRepo.validateName(
						emailUiDto.getApplication(), emailUiDto.getProcess(), emailUiDto.getEntity(),
						emailUiDto.getName(), entity.getEmailDefinitionId());
				if (ServicesUtil.isEmpty(emailDefinitionDoWithSameName)) {

					emailUiDto.setEmailDefinitionId(emailDefinitionId);
					EmailDefinitionDo emailDefinitionDo = new EmailDefinitionDo();
					BeanUtils.copyProperties(emailUiDto, emailDefinitionDo);
					if (emailUiDto.getStatus() != null && emailUiDto.getStatus().equals("Active")) {
						emailDefinitionRepo.updateStatus(emailUiDto.getApplication(), emailUiDto.getProcess(),
								emailUiDto.getEntity(), "Deactivated", "Active");
					}
					entity = emailDefinitionRepo.save(emailDefinitionDo);
//					emailDefinitionProcessMappingRepo.deleteByEmailDefinitionId(emailDefinitionId);
//					if (!ServicesUtil.isEmpty(emailUiDto.getProcessList())) {
//						List<EmailDefinitionProcessMappingDo> emailDefinitionProcessMappingDos = new ArrayList<EmailDefinitionProcessMappingDo>();
//						emailUiDto.getProcessList().forEach(obj -> {
//							obj.setEmailDefinitionId(emailDefinitionId);
//							emailDefinitionProcessMappingDos.add(emailDefinitionProcessMappingRepo.importDto(obj));
//						});
//						emailDefinitionProcessMappingRepo.saveAll(emailDefinitionProcessMappingDos);
//					}
					recipientRepo.deleteByIdEmailDefinitionId(emailDefinitionId);

					List<RecipientDto> recipientDtoList = new ArrayList<RecipientDto>();
					getRecipientsAccordingToType(emailUiDto.getToList(), MailNotificationAppConstants.TO,
							entity.getEmailDefinitionId(), recipientDtoList);
					getRecipientsAccordingToType(emailUiDto.getCcList(), MailNotificationAppConstants.CC,
							entity.getEmailDefinitionId(), recipientDtoList);
					getRecipientsAccordingToType(emailUiDto.getBccList(), MailNotificationAppConstants.BCC,
							entity.getEmailDefinitionId(), recipientDtoList);
					List<RecipientDo> recipientDoList = recipientRepo.importDto(recipientDtoList);
					if (!ServicesUtil.isEmpty(recipientDoList)) {
						recipientRepo.saveAll(recipientDoList);
					}
					responseDto.setMessage("Email definition updated successfully !");

				} else {
					responseDto.setStatus(Boolean.FALSE);
					responseDto.setMessage("Email Template with Same name already Exist for Application "
							+ emailUiDto.getApplication());
				}

			});

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[updateEmailTemplate]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}

		logger.info(" [EmailDefinitionServiceImpl]|[updateEmailTemplate]  Execution end ");
		return responseDto;
	}

	@Override
	public ResponseDto deleteEmailTemplate(String emailDefinitionId) {
		logger.info(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			responseDto.setMessage("Email definition deleted successfully !");
			recipientRepo.findByIdEmailDefinitionId(emailDefinitionId).stream()
					.filter(recipientEntity -> recipientEntity != null).forEach(recipientEntity -> {
						recipientRepo.deleteByIdEmailDefinitionId(emailDefinitionId);
					});
			// emailDefinitionProcessMappingRepo.deleteByEmailDefinitionId(emailDefinitionId);
			emailDefinitionRepo.findById(emailDefinitionId).ifPresent(entity -> {
				emailDefinitionRepo.deleteById(emailDefinitionId);
			});

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}

		logger.info(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]  Execution end ");
		return responseDto;
	}

	@Override
	public ResponseDto getEmailTemplate(String emailDefinitionId, String application, String process, String entityName,
			Pageable pageable, String searchString, Jwt jwt) {
		logger.info(" [EmailDefinitionServiceImpl]|[getEmailTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		ResponseDto temp = new ResponseDto();
		try {

			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			responseDto.setMessage("Email definitions fetched successfully  ");
			temp = getEmailTemplates(emailDefinitionId, application, process, entityName, null, null, Boolean.FALSE,
					pageable, searchString);
			responseDto.setData(temp.getData());
			responseDto.setTotalCount(temp.getTotalCount());

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[getEmailTemplate]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}

		logger.info(" [EmailDefinitionServiceImpl]|[getEmailTemplate]  Execution end ");
		return responseDto;
	}

	@Override
	public ResponseDto triggerMail(MailTriggerDto mailTriggerDto) {
		logger.info(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		try {
			Pageable pageable;
			pageable = PageRequest.of(0, Integer.MAX_VALUE);

			List<EmailUiDto> emailUiDtoList = (List<EmailUiDto>) (getEmailTemplates(
					mailTriggerDto.getEmailDefinitionId(), mailTriggerDto.getApplication(), mailTriggerDto.getProcess(),
					mailTriggerDto.getEntityName(), mailTriggerDto.getTemplateName(), null, Boolean.TRUE, pageable,
					null).getData());

			if (!ServicesUtil.isEmpty(emailUiDtoList)) {
				EmailUiDto emailUiDto = emailUiDtoList.get(0);
				emailUiDto.setSubject(
						mailTriggerDto.getSubject() != null ? mailTriggerDto.getSubject() : emailUiDto.getSubject());
				emailUiDto.setContent(mailTriggerDto.getEmailBody() != null ? mailTriggerDto.getEmailBody()
						: emailUiDto.getContent());
				emailUiDto.setAttachment(mailTriggerDto.getAttachment());

				if (mailTriggerDto.getSubjectVariables() != null && !mailTriggerDto.getSubjectVariables().isEmpty()) {
					mailTriggerDto.getSubjectVariables().entrySet().forEach(entry -> {
						emailUiDto.setSubject(emailUiDto.getSubject().replaceAll("&" + entry.getKey(),
								entry.getValue() != null ? entry.getValue().toString() : null));
					});
					// emailUiDto.setSubject(changedSub);
				}

				if (mailTriggerDto.getContentVariables() != null && !mailTriggerDto.getContentVariables().isEmpty()) {
					mailTriggerDto.getContentVariables().entrySet().forEach(entry -> {
						emailUiDto.setContent(emailUiDto.getContent().replaceAll("&" + entry.getKey(),
								entry.getValue() != null ? entry.getValue().toString() : null));
					});
					// emailUiDto.setContent(content);
				}
				if (!ServicesUtil.isEmpty(mailTriggerDto.getToList())) {
					if (!ServicesUtil.isEmpty(emailUiDto.getToList())) {
						emailUiDto.getToList().addAll(mailTriggerDto.getToList());
					} else {
						emailUiDto.setToList(mailTriggerDto.getToList());
					}

				}
				if (!ServicesUtil.isEmpty(mailTriggerDto.getCcList())) {
					if (!ServicesUtil.isEmpty(emailUiDto.getCcList())) {
						emailUiDto.getCcList().addAll(mailTriggerDto.getCcList());
					} else {
						emailUiDto.setCcList(mailTriggerDto.getCcList());
					}

				}
				if (!ServicesUtil.isEmpty(mailTriggerDto.getBccList())) {
					if (!ServicesUtil.isEmpty(emailUiDto.getBccList())) {
						emailUiDto.getBccList().addAll(mailTriggerDto.getBccList());
					} else {
						emailUiDto.setBccList(mailTriggerDto.getBccList());
					}

				}
				if (emailUiDto.getBccAllowed() == null || !emailUiDto.getBccAllowed()) {
					emailUiDto.setBccList(null);
				}
				if (!ServicesUtil.isEmpty(emailUiDto.getToList())) {
					responseDto = emailUtils.triggerMail(emailUiDto);
				} else {
					responseDto.setMessage("Recipients are required to trigger mail");
				}
			} else {
				responseDto.setMessage("Mail Template not defined for given inputs");
			}
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}

		logger.info(" [EmailDefinitionServiceImpl]|[deleteEmailTemplate]  Execution end ");
		return responseDto;
	}

	private ResponseDto getEmailTemplates(String emailDefinitionId, String application, String process,
			String entityName, String name, String status, Boolean isTrigger, Pageable pageable, String searchString)
			throws JsonMappingException, JsonProcessingException, URISyntaxException {
		ResponseDto responseDto = new ResponseDto();
		List<EmailUiDto> dtos = new ArrayList<>();
		List<EmailDefinitionDo> emailDefinitionDos = new ArrayList<>();
		Page<EmailDefinitionDo> page = null;

		if (emailDefinitionId != null && !ServicesUtil.isEmpty(emailDefinitionId)) {
			emailDefinitionDos = emailDefinitionRepo.findByEmailDefinitionId(emailDefinitionId);
			responseDto.setTotalCount((emailDefinitionDos != null) ? emailDefinitionDos.size() : 0);
		} else if (searchString != null && !searchString.isEmpty() && isTrigger == false) {
			page = emailDefinitionRepo.findByNameContainingIgnoreCase(searchString, pageable);
			if (page != null && !page.isEmpty()) {
				emailDefinitionDos = page.getContent();
				responseDto.setTotalCount((int) page.getTotalElements());
			}
		} else {

			if (isTrigger) {
				emailDefinitionDos = emailDefinitionRepo.getActiveEmailDefinition(application, process, entityName);
				responseDto.setTotalCount((emailDefinitionDos != null) ? emailDefinitionDos.size() : 0);
			} else {
				page = emailDefinitionRepo.getEmailDefinitionBasedOnDetails(application, process, entityName, name,
						status, pageable);
				emailDefinitionDos = page.getContent();
				responseDto.setTotalCount((page != null) ? (int) page.getTotalElements() : 0);
			}

		}

		if (!ServicesUtil.isEmpty(emailDefinitionDos)) {
			emailDefinitionDos = emailDefinitionDos.stream().distinct().collect(Collectors.toList());
			List<String> emailDefinitionIdList = emailDefinitionDos.stream().map(obj -> obj.getEmailDefinitionId())
					.collect(Collectors.toList());
			List<String> userIds = new ArrayList<String>();
			Map<String, List<String>> map = null;
			/*
			 * if (jwt != null) { map =
			 * emailUtils.getRecipientsRecordsFromWr(emailDefinitionIdList, jwt); }
			 */
			// map = emailUtils.getRecipientsRecordsFromWorkRules(emailDefinitionIdList);

			for (EmailDefinitionDo definitionDo : emailDefinitionDos) {
				EmailUiDto emailUiDto = new EmailUiDto();
				BeanUtils.copyProperties(definitionDo, emailUiDto);
				emailUiDto.setProcessList(emailDefinitionProcessMappingRepo.exportDtoList(
						emailDefinitionProcessMappingRepo.getByEmailDefinitionId(emailUiDto.getEmailDefinitionId())));
				if (map != null && !map.isEmpty()) {

					emailUiDto.setToList(map.get(emailUiDto.getEmailDefinitionId() + MailNotificationAppConstants.TO));
					emailUiDto.setCcList(map.get(emailUiDto.getEmailDefinitionId() + MailNotificationAppConstants.CC));

					emailUiDto
							.setBccList(map.get(emailUiDto.getEmailDefinitionId() + MailNotificationAppConstants.BCC));
				}
				if (definitionDo.getCreatedBy() != null) {
					userIds.add(definitionDo.getCreatedBy());
				}
				if (definitionDo.getUpdatedBy() != null) {
					userIds.add(definitionDo.getUpdatedBy());
				}
				dtos.add(emailUiDto);
			}
			if (!ServicesUtil.isEmpty(userIds) && !ServicesUtil.isEmpty(dtos)) {
				Map<String, UserDto> userMap = CommonMethods.getUserDetails(userIds); 
				if (userMap != null && !userMap.isEmpty()) {

					dtos.forEach(obj -> {
						if (obj.getCreatedBy() != null && userMap.get(obj.getCreatedBy().toLowerCase()) != null
								&& userMap.get(obj.getCreatedBy().toLowerCase()).getName() != null) {
							obj.setCreatedBy(userMap.get(obj.getCreatedBy().toLowerCase()).getName());
						}
						if (obj.getUpdatedBy() != null && userMap.get(obj.getUpdatedBy().toLowerCase()) != null
								&& userMap.get(obj.getUpdatedBy().toLowerCase()).getName() != null) {
							obj.setUpdatedBy(userMap.get(obj.getUpdatedBy().toLowerCase()).getName());
						}
					});
				}
			}
		}
		if (!ServicesUtil.isEmpty(dtos)) {
			dtos.sort(new Comparator<EmailUiDto>() {

				@Override
				public int compare(EmailUiDto o1, EmailUiDto o2) {
					if (o1 != null && o1.getName() != null && (o2 == null || o2.getName() == null)) {
						return 1;
					} else if ((o1 == null || o1.getName() == null) && o2 != null && o2.getName() != null) {
						return -1;
					} else if ((o1 == null || o1.getName() == null) && (o2 == null || o2.getName() == null)) {
						return 0;
					} else {
						return o1.getName().compareTo(o2.getName());
					}
				}
			});
		}
		System.out.println(responseDto.getTotalCount());
		// responseDto.setTotalCount(dtos.size());
		responseDto.setData(dtos);
		return responseDto;
	}

	private List<RecipientDto> getRecipientsAccordingToType(List<String> emails, String type, String emailDefinitionId,
			List<RecipientDto> recipients) {
		if (!ServicesUtil.isEmpty(emails)) {
			for (String mail : emails) {
				RecipientDto dto = new RecipientDto();
				dto.setEmailDefinitionId(emailDefinitionId);
				dto.setRecipientType(type);
				dto.setUserEmail(mail);
				recipients.add(dto);
			}
			return recipients;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getEmailTemplate(String application) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<EmailDefinitionDo> emailDefinitionDos = new ArrayList<>();
		Pageable pageable;
		pageable = PageRequest.of(0, Integer.MAX_VALUE);
		emailDefinitionDos = emailDefinitionRepo
				.getEmailDefinitionBasedOnDetails(application, null, null, null, null, pageable).getContent();

		if (!ServicesUtil.isEmpty(emailDefinitionDos)) {
			for (EmailDefinitionDo definitionDo : emailDefinitionDos) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("EMAIL_TEMPLATE", definitionDo.getEmailDefinitionId());
				map.put("EMAIL_TEMPLATE_NAME", definitionDo.getName());
				list.add(map);

			}
		}
		return list;
	}

	@Override
	public ResponseDto createVariable(ApplicationVariablesMasterDto variablesMasterDto) {
		logger.info(" [EmailDefinitionServiceImpl]|[createVariable]  Execution start");

		ResponseDto responseDto = new ResponseDto();
		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			responseDto.setMessage(variablesMasterDto.getVariable() + " variable created successfully in "
					+ variablesMasterDto.getApplicationName());
			ApplicationVariablesMasterDo entity = new ApplicationVariablesMasterDo();
			ApplicationVariablesMasterDoPK id = new ApplicationVariablesMasterDoPK();
			BeanUtils.copyProperties(variablesMasterDto, id);
			entity.setId(id);
			applicationVariablesMasterRepo.save(entity);

		} catch (Exception e) {
			logger.error(" [EmailDefinitionServiceImpl]|[createVariable]   "

					+ e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [EmailDefinitionServiceImpl]|[createVariable]  Execution end ");
		return responseDto;
	}

	@Override
	public ResponseDto getVariables(String application, String entity, String process) {
		logger.info(" [EmailDefinitionServiceImpl]|[getVariables]  Execution start");
		ResponseDto responseDto = new ResponseDto();
		List<VariableDto> variables = new ArrayList<VariableDto>();
		List<String> list = new ArrayList<String>();

		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			responseDto.setMessage(" Variables fetched successfully ");
			list = applicationVariablesMasterRepo.getVariablesByApplicationName(application, entity, process);
			for (String s : list) {
				VariableDto variableDto = new VariableDto();
				variableDto.setVariable(s);
				variables.add(variableDto);
			}
			responseDto.setData(variables);

		} catch (Exception e) {
			logger.info(" [EmailDefinitionServiceImpl]|[getVariables]  Execution start");
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [EmailDefinitionServiceImpl]|[getVariables]  Execution start");
		return responseDto;
	}

	@Override
	public ResponseDto validateActiveTemplate(String application, String entity, String process) {
		logger.info(" [EmailDefinitionServiceImpl]|[validateActiveTemplate]  Execution start");
		ResponseDto responseDto = new ResponseDto();

		try {
			responseDto.setStatus(Boolean.TRUE);
			responseDto.setStatusCode(200);
			Pageable pageable;
			pageable = PageRequest.of(0, Integer.MAX_VALUE);
			Page<EmailDefinitionDo> page = null;

			page = emailDefinitionRepo.getEmailDefinitionBasedOnDetails(application, process, entity, null, "Active",
					pageable);
			List<EmailDefinitionDo> emailDefinitionDoList = page.getContent();
			if (!ServicesUtil.isEmpty(emailDefinitionDoList)) {
				responseDto.setStatus(Boolean.FALSE);
				responseDto.setData(emailDefinitionDoList);
				responseDto.setStatusCode(200);
				responseDto.setMessage(
						"For Combination of application,entity,process  Active template already existed,If you Submit this template it will Deactivate older Template");
			} else {
				responseDto.setStatus(Boolean.TRUE);
				responseDto.setStatusCode(200);
			}

		} catch (Exception e) {
			logger.info(" [EmailDefinitionServiceImpl]|[validateActiveTemplate]  Exception Occured " + e.getMessage());
			responseDto.setStatus(Boolean.FALSE);
			responseDto.setStatusCode(500);
			responseDto.setMessage(e.getMessage());

		}
		logger.info(" [EmailDefinitionServiceImpl]|[validateActiveTemplate]  Execution End");
		return responseDto;
	}

	
}
