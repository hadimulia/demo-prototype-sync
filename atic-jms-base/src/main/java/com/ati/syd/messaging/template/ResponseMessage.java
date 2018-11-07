package com.ati.syd.messaging.template;

public class ResponseMessage<T> {

	private String requestId;
	private String code;
	private String message;
	private T request;
	
	public ResponseMessage() {
		
	}
	
	public  ResponseMessage(T t){
		this.request=t;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getRequest() {
		return request;
	}
	public void setRequest(T request) {
		this.request = request;
	}
	@Override
	public String toString() {
		return "ResponseMessage [requestId=" + requestId + ", code=" + code + ", message=" + message + ", request="
				+ request + "]";
	}
	
	
	
}
