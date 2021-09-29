package com.incture.cherrywork.dtos;


import java.util.List;



import com.incture.cherrywork.entities.Address;

import lombok.Data;

public @Data class UserInfo {

	private List<Address> addresses;
	private String passwordStatus;
	private String displayName;
	private String sourceSystem;
	private String locale;
	private String passwordLoginTime;
	private List<PhoneNumber> phoneNumbers;
	private List<Email> emails;
	private String sapUserId;
	private String loginTime;
	private String id;
	private String mailVerified;
	private String passwordPolicy;
	private String passwordSetTime;
	private String timeZone;
	private Boolean active;
	private List<Group> groups;
	private String telephoneVerified;
	private String userName;
	private String passwordFailedLoginAttempts;
	private List<String> schemas;
	private Name name;
	private String userType;
	private UserCustom userCustomAttributes;
	private UserEnterprise userEnterprise;

}

