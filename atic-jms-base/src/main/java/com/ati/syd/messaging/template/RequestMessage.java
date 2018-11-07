package com.ati.syd.messaging.template;

public class RequestMessage<T> {

	private String topic;
	private String group;
	private T request;
	
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public T getRequest() {
		return request;
	}
	public void setRequest(T request) {
		this.request = request;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String toString() {
		return "RequestMessage [topic=" + topic + ", group=" + group + ", request=" + request + "]";
	}
	
	
	
	
}
