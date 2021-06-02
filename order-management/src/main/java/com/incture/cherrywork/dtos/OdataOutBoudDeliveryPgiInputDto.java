package com.incture.cherrywork.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OdataOutBoudDeliveryPgiInputDto {
	
	
	private String Vbeln ;//– Delivery number
	private String Kunag ;//– Item Number
	private String Traid ;// – Material Number
	private String Werks ;// – Plant Number
	private String Btgew ;// – Delivered Quantity
	private String Ntgew ;// – Picked Quantity
	private String Gewei ;// – UOM
	private String Lgnum ;// – Storage Location
	private String Ternr ;
	public String getVbeln() {
		return Vbeln;
	}
	public void setVbeln(String vbeln) {
		Vbeln = vbeln;
	}
	public String getKunag() {
		return Kunag;
	}
	public void setKunag(String kunag) {
		Kunag = kunag;
	}
	public String getTraid() {
		return Traid;
	}
	public void setTraid(String traid) {
		Traid = traid;
	}
	public String getWerks() {
		return Werks;
	}
	public void setWerks(String werks) {
		Werks = werks;
	}
	public String getBtgew() {
		return Btgew;
	}
	public void setBtgew(String btgew) {
		Btgew = btgew;
	}
	public String getNtgew() {
		return Ntgew;
	}
	public void setNtgew(String ntgew) {
		Ntgew = ntgew;
	}
	public String getGewei() {
		return Gewei;
	}
	public void setGewei(String gewei) {
		Gewei = gewei;
	}
	public String getLgnum() {
		return Lgnum;
	}
	public void setLgnum(String lgnum) {
		Lgnum = lgnum;
	}
	public String getTernr() {
		return Ternr;
	}
	public void setTernr(String ternr) {
		Ternr = ternr;
	}
	@JsonIgnore
	private String pgiId;
	
	public String getPgiId() {
		return pgiId;
	}
	public void setPgiId(String pgiId) {
		this.pgiId = pgiId;
	}
	@Override
	public String toString() {
		return "{"+" \"Vbeln\":"+"\""+ Vbeln +"\""+",\"Kunag\":" +"\""+ Kunag +"\""+",\"Traid\":" +"\""+ Traid +"\""+",\"Werks\""  
				+":\""+ Werks +"\""  + ",\"Btgew\":" +"\""+ Btgew +"\""+ ",\"Ntgew\":" +"\""+ Ntgew +"\"" + ",\"Gewei\":" +"\""+ Gewei +"\""+",\"Lgnum\":" + "\""+ Lgnum +"\"" + ",\"Ternr\":" 
				+ "\""+ Ternr +"\"" + "}";
		
	}

	
	
	
	

}
