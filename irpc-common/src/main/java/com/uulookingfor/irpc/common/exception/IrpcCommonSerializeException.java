package com.uulookingfor.irpc.common.exception;

public class IrpcCommonSerializeException extends IrpcCommonException{

	private static final long serialVersionUID = -3404582893017334314L;
	
	public IrpcCommonSerializeException() {
        super();
    }
	
	public IrpcCommonSerializeException(String message) {
        super(message);
    }
	
	public IrpcCommonSerializeException(Throwable cause) {
        super(cause);
    }
	
	public IrpcCommonSerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
