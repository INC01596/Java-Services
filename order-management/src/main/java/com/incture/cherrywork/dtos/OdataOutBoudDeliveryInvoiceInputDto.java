package com.incture.cherrywork.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OdataOutBoudDeliveryInvoiceInputDto {
	
	private String Vbeln ;
	private String Ternr;
	
	public String getVbeln() {
		return Vbeln;
	}
	public void setVbeln(String vbeln) {
		Vbeln = vbeln;
	}
	
	public String getTernr() {
		return Ternr;
	}
	public void setTernr(String ternr) {
		Ternr = ternr;
	}
	@JsonIgnore
	private String invId;
	
	public String getInvId() {
		return invId;
	}
	public void setInvId(String invId) {
		this.invId = invId;
	}
	
	@Override
	public String toString() {
		return "{"+" \"Vbeln\":"+"\""+ Vbeln +"\"" + ",\"Ternr\":" 
				+ "\""+ Ternr +"\"" + "}";
	}
	
	

}
