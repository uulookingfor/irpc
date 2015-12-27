package com.uulookingfor.irpc.server.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import lombok.NonNull;

import com.google.common.collect.Maps;
import com.uulookingfor.irpc.server.exception.IrpcServerException;
import com.uulookingfor.irpc.server.exception.IrpcServerExceptionCode;

import net.sf.cglib.reflect.FastClass;

public class CglibFastClass{

	private static final Map<Class<?>, FastClass> fastClassCache = Maps.newConcurrentMap();
	
	public static Object invoke(
			@NonNull String methodName, 
			@NonNull Class<?>[] parameterTypes,
			@NonNull Object obj, 
			Object[] args) throws IrpcServerException {
		
		FastClass fastClass = getFastClass(obj.getClass());
		
		try {
			
			return fastClass.invoke(methodName, parameterTypes, obj, args);
			
		} catch (InvocationTargetException e) {
			
			throw new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_INVOKE_EXCEPTION,
					exceptionMsg(methodName, parameterTypes, obj, args),
					e);
			
		}
		
	}
	
	private static FastClass getFastClass(Class<?> clazz){
		
		FastClass ret = fastClassCache.get(clazz);
		
		if(ret == null){
			
			fastClassCache.put(clazz, FastClass.create(clazz));
			
			ret = fastClassCache.get(clazz);
			
		}
		
		return ret;
	}
	
	private static String exceptionMsg(
			String methodName, 
			Class<?>[] parameterTypes,
			Object obj, 
			Object[] args){
		
		String prefix = "CglibFastClass==>";
		String fieldSplitor = ", ";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(prefix);
		
		sb.append("methodName:" + methodName).append(fieldSplitor);
		sb.append("parameterTypes:").append(arrayMsg(parameterTypes)).append(fieldSplitor);
		sb.append("obj:" + obj).append(fieldSplitor);
		sb.append("args:").append(arrayMsg(args)).append(fieldSplitor);
			
		return sb.toString();
	}

	private static String arrayMsg(Object[] objs){
		
		String prefix = "[";
		String suffix = "]";
		String connector = "-";
		String fieldSplitor = ", ";
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(prefix);
		
		if(objs != null){
			for(int i=0; i<objs.length; i++){
				sb.append(i).append(connector).append(objs[i]).append(fieldSplitor);
			}
		}
		
		sb.append(suffix);
		
		return sb.toString();
	}
}
