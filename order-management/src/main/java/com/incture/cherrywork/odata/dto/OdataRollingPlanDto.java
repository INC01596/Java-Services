package com.incture.cherrywork.odata.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OdataRollingPlanDto {

	@Expose
	@SerializedName("Mandt")
	private String Mandt;
	
	@Expose
	@SerializedName("Zzmonth")
	private String Zzmonth;
	
	@Expose
	@SerializedName("Werks")
	private String Werks;
	
	@Expose
	@SerializedName("Matnr")
	private String Matnr;
	
	@Expose
	@SerializedName("ZzsectionGrp")
	private String ZzsectionGrp;
	
	@Expose
	@SerializedName("UpdateInd")
	private String UpdateInd;
	
	@Expose
	@SerializedName("ChangedOn")
	private String ChangedOn;
	
	@Expose
	@SerializedName("SyncStat")
	private String SyncStat;

	public String getMandt() {
		return Mandt;
	}

	public void setMandt(String mandt) {
		Mandt = mandt;
	}

	public String getZzmonth() {
		return Zzmonth;
	}

	public void setZzmonth(String zzmonth) {
		Zzmonth = zzmonth;
	}

	public String getWerks() {
		return Werks;
	}

	public void setWerks(String werks) {
		Werks = werks;
	}

	public String getMatnr() {
		return Matnr;
	}

	public void setMatnr(String matnr) {
		Matnr = matnr;
	}

	public String getZzsectionGrp() {
		return ZzsectionGrp;
	}

	public void setZzsectionGrp(String zzsectionGrp) {
		ZzsectionGrp = zzsectionGrp;
	}

	public String getUpdateInd() {
		return UpdateInd;
	}

	public void setUpdateInd(String updateInd) {
		UpdateInd = updateInd;
	}

	public String getChangedOn() {
		return ChangedOn;
	}

	public void setChangedOn(String changedOn) {
		ChangedOn = changedOn;
	}

	public String getSyncStat() {
		return SyncStat;
	}

	public void setSyncStat(String syncStat) {
		SyncStat = syncStat;
	}

	@Override
	public String toString() {
		return "RollingPlanDto [Mandt=" + Mandt + ", Zzmonth=" + Zzmonth + ", Werks=" + Werks + ", Matnr=" + Matnr
				+ ", ZzsectionGrp=" + ZzsectionGrp + ", UpdateInd=" + UpdateInd + ", ChangedOn=" + ChangedOn
				+ ", SyncStat=" + SyncStat + "]";
	}
}
