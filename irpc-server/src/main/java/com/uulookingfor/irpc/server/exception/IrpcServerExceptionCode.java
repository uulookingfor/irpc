package com.uulookingfor.irpc.server.exception;

import lombok.Getter;

/**
 * @author suxiong.sx 
 */
public enum IrpcServerExceptionCode {
	
	IRPC_SERVER_UNKNOWN(2000),
	
	IRPC_SERVER_INVOKE_EXCEPTION(2001),
	
	IRPC_SERVER_INTERFACE_NOT_EQUAL(2002),
	
	IRPC_SERVER_ADDRESS_NOT_EQUAL(2003),
	
	IRPC_SERVER_VERSION_NOT_EQUAL(2004),
	
	IRPC_SERVER_GROUP_NOT_EQUAL(2005),
	
	IRPC_SERVER_BUILD_ADDRESS_EXCEPTION(2006),
	
	IRPC_SERVER_CANT_FIND_PROVIDER(2007),
	
	IRPC_SERVER_UNKNOWN_PROVIDER_TRANSPACK(2008),
	;
	
	@Getter private int value;
	
	private IrpcServerExceptionCode(int value){
		this.value = value;
	}
	
}
