package com.incture.cherrywork.services;

import com.incture.cherrywork.dtos.MailRequestDto;
import com.incture.cherrywork.dtos.ResponseDto;

public interface EmailSenderService {
	ResponseDto triggerMail(MailRequestDto mailRequestDto);

}
