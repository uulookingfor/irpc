package com.uulookingfor.irpc.client.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutionException;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.client.proxy.handler.IrpcBlockingInvokeHandler;
import com.uulookingfor.irpc.client.proxy.handler.IrpcInvokeHandler;
import com.uulookingfor.irpc.common.client.IrpcConsumerMetaData;

/**
 * @author suxiong.sx 
 */
public class IrpcProxy{
	
	@SuppressWarnings("unchecked")
	public static <I> I newProxyInstance(Class<?> clazz, IrpcConsumerMetaData irpcConsumerMetaData) throws ExecutionException{
		
		Asserts.notNull(clazz, "");
		
		if(clazz.isPrimitive()){
			return null;
		}
		
		ClassLoader cl = clazz.getClassLoader();
		
		Asserts.notNull(cl, clazz.getName() + " without classloader");
		
		IrpcInvokeHandler handler = new IrpcBlockingInvokeHandler(irpcConsumerMetaData);
		
		return (I) Proxy.newProxyInstance(cl, new Class<?>[] { clazz }, handler);
		
	}
	
	
}