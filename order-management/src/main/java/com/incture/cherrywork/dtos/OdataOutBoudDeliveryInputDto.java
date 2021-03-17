package com.incture.cherrywork.dtos;

public class OdataOutBoudDeliveryInputDto {
	
	private String Vbeln;//soNumber
	private String Kunag ;//soItemNumber
	private String Btgew;//Delivery Quantity
	private String Lgnum;//Item Unit
	private String Ternr;
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
	

}
