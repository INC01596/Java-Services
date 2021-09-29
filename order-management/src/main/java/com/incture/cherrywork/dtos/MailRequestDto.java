package com.incture.cherrywork.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailRequestDto {

	private String from;

	private String to;

	private String subject;

	private String body;

	private String cc;

	private String bcc;

	private String attachment;

	public MailRequestDto() {
	}

	public MailRequestDto(String from, String to, String subject, String body, String cc, String bcc,
			String attachment) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.cc = cc;
		this.bcc = bcc;
		this.attachment = attachment;
	}

}
