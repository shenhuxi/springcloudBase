package com.dingxin.common.exceptions.rest;

public class RestException extends Exception {
	
	public RestException(String msg){
		super(msg.toString());
	}

}
