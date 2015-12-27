package com.uulookingfor.irpc.common.client;

import com.uulookingfor.irpc.common.IrpcMetaData;

public class IrpcConsumerMetaData extends IrpcMetaData {
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("IrpcConsumerMetaData:[");
		
		sb.append("group:" + super.getGroup() + ",");
		sb.append("version:" + super.getVersion() + ",");
		sb.append("intefaceName:" + super.getInterfaceName() + ",");
		sb.append("serviceAddress:" + super.getServiceAddress() + ",");
		
		sb.append("]");
		
		return sb.toString();
	}
}
