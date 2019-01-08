package gEapp.domain;

import java.util.ArrayList;
import java.util.List;

public class JSONResponse {
	
	String result, message;
	List<Member> memberList = new ArrayList<>();
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}
	@Override
	public String toString() {
		return "JSONResponse [result=" + result + ", message=" + message + ", memberList=" + memberList + "]";
	}
	
	

}
