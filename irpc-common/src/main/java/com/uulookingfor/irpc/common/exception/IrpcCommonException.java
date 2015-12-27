package com.uulookingfor.irpc.common.exception;

/**
 * @author suxiong.sx 
 */
public class IrpcCommonException extends Exception{

	private static final long serialVersionUID = -1668878213333169673L;

	public IrpcCommonException() {
        super();
    }
	
	public IrpcCommonException(String message) {
        super(message);
    }
	
	public IrpcCommonException(Throwable cause) {
        super(cause);
    }
	
	public IrpcCommonException(String message, Throwable cause) {
        super(message, cause);
    }
}
