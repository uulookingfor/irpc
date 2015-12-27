package com.uulookingfor.irpc.client.exception;

import lombok.Getter;

/**
 * @author suxiong.sx 
 */
public enum IrpcClientExceptionCode {
	
	IRPC_CLIENT_UNKNOWN(1000),
	
	IRPC_CLIENT_INTERFACENAME_EMPTY(1001),
	
	IRPC_CLIENT_CLASS_NOT_FIND(1002),
	
	IRPC_CLIENT_PROXY_CREATE_EXCEPTION(1003),
	
	IRPC_CLIENT_UNKNOWN_CONSUMER_TRANSPACK(1004),
	
	IRPC_CLIENT_UNKNOWN_PROVIDER_TRANSPACK(1005),
	
	IRPC_CLIENT_EXCEPTION_ON_SEND(1006),
	
	IRPC_CLIENT_EXCEPTION_ON_TRIG_FUTURE(1007),
	
	IRPC_CLIENT_UNKNOW_PROVIDER_PACK(1008),
	;
	
	@Getter private int value;
	
	private IrpcClientExceptionCode(int value){
		this.value = value;
	}
	
}
