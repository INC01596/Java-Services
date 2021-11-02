package com.incture.cherrywork.dtos;





import com.incture.cherrywork.exceptions.InvalidInputFault;
import com.incture.cherrywork.sales.constants.EnOperation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SalesRepCustDto extends BaseDto {

	private String primaryId;

	private String salesman_code;

	private String salesman_name;

	private String manager_code;

	private String manager_name;

	private String salesteam;

	private String cust_code;

	private String cust_name;

	private String type;

	private String branch;

	private String cust_grp_code;

	private String cust_group_name;

	private String trade_type_id;

	private String trade_type_name;

	private String region_code;

	private String region_name;

	private String area_code;

	private String area_name;

	private String territory;

	private String category;

	private String appr_1_code;

	private String appr_1;

	private String appr_2_code;

	private String appr_2;

	private String appr_3_code;

	private String appr_3;

	private String sales_organization;

	private String distribution_channel;

	private String division;

	private String controlling_area;

	private Boolean isVisited;

	private Boolean notVisited;
	
	private Double longitude;
	
	private Double latitude;

	
	@Override
	public Object getPrimaryKey() {
		return primaryId;
	}

	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean getValidForUsage() {
		// TODO Auto-generated method stub
		return null;
	}

}
