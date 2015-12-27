package com.uulookingfor.irpc.client.domain;

public interface IrpcClientConstants {
	
	public static final String DEFAULT_GROUP = "Irpc";
	
	public static final String DEFAULT_VERSION = "1.0.0";
	
	public static final int PROCESSOR_NUMBER = Runtime.getRuntime().availableProcessors();
	
	public static final int DEFAULT_FACTORY_CACHE_MAXIUMSIZE = 256;
	
	public static final int DEFAULT_FACTORY_CACHE_EXPIRETIEM = 29;
	
	public static final int DEFAULT_CONNECT_TIMEOUT = 3000;
	
	public static final int DEFAULT_SEND_TIMEOUT = 3000;
	
	public static final int DEFAULT_REQUEST_POOL_SIZE = 8096;
	
}
