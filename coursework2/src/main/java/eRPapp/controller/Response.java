package eRPapp.controller;

import eRPapp.domain.Option;

public class Response {
	
	private String status;
	private Option data;
	
	public Response() {
		
	}
	
	public Response(String status, Option data) {	
		this.status = status;
		this.data = data;
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Option data) {
		this.data = data;
	}
	
}