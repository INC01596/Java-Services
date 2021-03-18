package com.incture.cherrywork.dtos;

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
	@Override
	public String toString() {
		return "{"+" \"Vbeln\":"+"\""+ Vbeln +"\"" + ",\"Ternr\":" 
				+ "\""+ Ternr +"\"" + "}";
	}
	
	

}
