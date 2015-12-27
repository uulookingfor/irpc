package com.uulookingfor.irpc.common.util;

import lombok.NonNull;

/**
 * @author suxiong.sx 
 */
public class AddressParser {
	
	public static final int ADDRESS_FIELD_NUM = 2;
	
	public static final int HOST_FIELD = 0;
	
	public static final int PORT_FIELD = 1;
	
	public static final String SPLITOR = ":";
	
	public static String host(@NonNull String address){
		
		String[] splitAddress = address.split(SPLITOR);
		
		if(splitAddress.length != ADDRESS_FIELD_NUM){
			throw new RuntimeException("serviceAddress not valid:" + address);
		}
		
		return splitAddress[HOST_FIELD];
	}
	
	public static int port(String address){
		
		String[] splitAddress = address.split(SPLITOR);
		
		if(splitAddress.length != ADDRESS_FIELD_NUM){
			throw new RuntimeException("serviceAddress not valid:" + address);
		}
		
		return Integer.valueOf(splitAddress[PORT_FIELD]);
	}
	
}
