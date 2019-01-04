package gEapp.domain;

public class JsonResponse {
	
	String result;
	String message;
	
	public String getResult() {
		return result;
	}
	public void setResult(String jsResult) {
		this.result = jsResult;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String jsMessage) {
		message = jsMessage;
	}
	
	@Override
	public String toString() {
		return "JsonResponse [Result=" + result + ", Message=" + message + "]";
	}
	
}
