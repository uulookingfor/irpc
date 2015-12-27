package com.uulookingfor.irpc.server.domain;

public interface IrpcServerConstants {
	
	public static final String BOSS_THREAD_FACTORY = "boss.thread.factory";
	
	public static final String WORD_THREAD_FACTORY = "work.thread.factory";
	
	public static final String PROVIDER_THREAD_FACTORY = "provider.thread.factory";
	
	public static final int NUMBER_OF_PARALLE_THREAD = Runtime.getRuntime().availableProcessors() * 2;
}

