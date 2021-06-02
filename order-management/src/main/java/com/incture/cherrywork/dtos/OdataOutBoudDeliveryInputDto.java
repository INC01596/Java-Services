package com.incture.cherrywork.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OdataOutBoudDeliveryInputDto {
	
	private String Vbeln;//soNumber
	private String Kunag ;//soItemNumber
	private String Btgew;//Delivery Quantity
	private String Lgnum;//Item Unit
	private String Ternr;
	private String Vstel;
	@JsonIgnore
	private String obdId;
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
	public String getBtgew() {
		return Btgew;
	}
	public void setBtgew(String btgew) {
		Btgew = btgew;
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
	
	
	public String getObdId() {
		return obdId;
	}
	public void setObdId(String obdId) {
		this.obdId = obdId;
	}
	@Override
	public String toString() {
		return "{"+" \"Vbeln\":"+"\""+ Vbeln +"\""+",\"Kunag\"" +":\""+ Kunag +"\"" + ",\"Btgew\":" +"\""+ Btgew +"\""+ ",\"Lgnum\":"
				+"\""+ Lgnum +"\"" +",\"Ternr\":" +"\""+ Ternr +"\""+",\"Vstel\":" +"\""+ Vstel +"\""+ "}";
	}
	public String getVstel() {
		return Vstel;
	}
	public void setVstel(String vstel) {
		Vstel = vstel;
	}
}
