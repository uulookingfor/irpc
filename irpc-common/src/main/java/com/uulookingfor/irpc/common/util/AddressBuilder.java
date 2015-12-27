package com.uulookingfor.irpc.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;

/**
 * @author suxiong.sx 
 */
public class AddressBuilder {
	
	public static String localAddress() throws UnknownHostException{
		
		StringBuffer localAddressBuffer = new StringBuffer();
		
		String ip = InetAddress.getLocalHost().getHostAddress();

		int port = IrpcCommonConstants.DEF_SERVER_PORT;
		
		localAddressBuffer.append(ip).append(AddressParser.SPLITOR).append(port);
		
		return localAddressBuffer.toString();
		
	}
	
}
