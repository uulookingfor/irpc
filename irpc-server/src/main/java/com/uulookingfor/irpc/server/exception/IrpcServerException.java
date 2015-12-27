package com.uulookingfor.irpc.server.exception;

import lombok.Getter;

public class IrpcServerException extends Exception{

	private static final long serialVersionUID = 4483209732907803402L;
	
	@Getter private IrpcServerExceptionCode code ;
	
	public IrpcServerException(String message){
		super(message);
	}
	
	public IrpcServerException(String message, Throwable e){
		super(message, e);
	}
	
	public IrpcServerException(Throwable e){
		super(e);
	}
	
	public IrpcServerException(IrpcServerExceptionCode code, String message){
		
		super(message);
		
		this.code = code;
	}
	
	public IrpcServerException(IrpcServerExceptionCode code, Throwable e){
		
		super(e);
		
		this.code = code;
	}
	
	public IrpcServerException(IrpcServerExceptionCode code, String message, Throwable e){
		
		super(message, e);
		
		this.code = code;
	}
}
