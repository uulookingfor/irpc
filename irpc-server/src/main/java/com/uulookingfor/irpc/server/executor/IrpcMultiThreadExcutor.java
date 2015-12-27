package com.uulookingfor.irpc.server.executor;

import java.util.concurrent.ExecutorService;

import com.uulookingfor.irpc.server.domain.IrpcServerConstants;

import io.netty.util.concurrent.DefaultExecutorServiceFactory;

public class IrpcMultiThreadExcutor implements IrpcServerConstants{
	
	public static final ExecutorService providerExcutor	= 
			new DefaultExecutorServiceFactory(PROVIDER_THREAD_FACTORY).newExecutorService(NUMBER_OF_PARALLE_THREAD);
	
}
