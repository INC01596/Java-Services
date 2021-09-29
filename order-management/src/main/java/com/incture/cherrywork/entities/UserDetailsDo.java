package com.incture.cherrywork.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_DETAILS_TEST")
@TableGenerator(name="tab", initialValue=0, allocationSize=50)
public class UserDetailsDo {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="tab")
	@Column(name ="USER_DETAILS_ID")
	private long id;
	
	@Column(name ="USER_ID")
	private String userId;
	
	@Column(name = "APPLICATION_TAB")
	private String applicationId;
	
	
	@Column(name = "VARIANT_ID")
	private String variantId;

	
	
	


	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVariantId() {
		return variantId;
	}

	public void setVariantId(String variantId) {
		this.variantId = variantId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
	
	

}
