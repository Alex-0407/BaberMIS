package com.cqut.genhoo.bean;

public class ErrorPage extends Page{

	private Exception exception;
	
	public ErrorPage(Exception exception){
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
}
