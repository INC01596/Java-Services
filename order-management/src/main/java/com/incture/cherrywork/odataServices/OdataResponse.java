package com.incture.cherrywork.odataServices;
import org.apache.http.HttpResponse;

public class OdataResponse {

	private Object responseObject;
	private int responseCode;
	private HttpResponse httpResponse;
	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	} 
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public HttpResponse getHttpResponse() {
		return httpResponse;
	}
	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
	@Override
	public String toString() {
		return "Response [responseObject=" + responseObject + ", responseCode=" + responseCode + ", httpResponse="
				+ httpResponse + "]";
	}
}