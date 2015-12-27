package com.uulookingfor.irpc.client.exception;

import lombok.Getter;

public class IrpcClientException extends Exception{

	private static final long serialVersionUID = 1801117699301937594L;
	
	@Getter private IrpcClientExceptionCode code ;
	
	public IrpcClientException(String message){
		super(message);
	}
	
	public IrpcClientException(String message, Throwable e){
		super(message, e);
	}
	
	public IrpcClientException(Throwable e){
		super(e);
	}
	
	public IrpcClientException(IrpcClientExceptionCode code, String message){
		
		super(message);
		
		this.code = code;
	}
	
	public IrpcClientException(IrpcClientExceptionCode code, Throwable e){
		
		super(e);
		
		this.code = code;
	}
	
	public IrpcClientException(IrpcClientExceptionCode code, String message, Throwable e){
		
		super(message, e);
		
		this.code = code;
	}
}
