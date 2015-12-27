package com.uulookingfor.irpc.server;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import lombok.NonNull;

import com.google.common.collect.Maps;

/**
 * @author suxiong.sx 
 */
public class IrpcProviderHolder {
	
	private static final Map<String, IrpcProvider> providers = Maps.newConcurrentMap();
	
	public static IrpcProvider put(@NonNull String key, @NonNull IrpcProvider val){
		
		return providers.put(key, val);
		
	}
	
	public static IrpcProvider get(@NonNull String key){
		
		return providers.get(key);
		
	}
	
	public static IrpcProvider remove(@NonNull String key){
		
		return providers.remove(key);
		
	}
	
	public static Set<String> keys(){
		
		return providers.keySet();
		
	}
	
	public static Collection<IrpcProvider> values(){
		
		return providers.values();
		
	}
	
	public static Set<Map.Entry<String, IrpcProvider>> entrySet(){
		
		return providers.entrySet();
		
	}
}
