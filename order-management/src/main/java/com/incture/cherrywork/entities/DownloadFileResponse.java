package com.incture.cherrywork.entities;



import lombok.Data;
import org.json.JSONObject;

public @Data class DownloadFileResponse {
	String message;
	boolean filePresent;
	JSONObject fileData;
}
