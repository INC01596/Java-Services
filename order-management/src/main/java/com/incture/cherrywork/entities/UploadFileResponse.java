package com.incture.cherrywork.entities;

import lombok.Data;

public @Data class UploadFileResponse {
	private boolean isUploaded;
	private String message;
}