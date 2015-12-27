package com.uulookingfor.irpc.client.transport.netty.future;

import com.google.common.util.concurrent.AbstractFuture;

public class IrpcRequestFuture<T> extends AbstractFuture<T>{
	
	@Override
	public boolean set(T obj){
		return super.set(obj);
	}
	
	@Override
	public boolean setException(Throwable throwable){
		return super.setException(throwable);
	}
	
}
