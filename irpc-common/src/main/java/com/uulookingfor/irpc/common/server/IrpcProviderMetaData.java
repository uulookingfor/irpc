package com.uulookingfor.irpc.common.server;

import lombok.Getter;
import lombok.Setter;

import com.uulookingfor.irpc.common.IrpcMetaData;
import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;

/**
 * @author suxiong.sx 
 */
public class IrpcProviderMetaData extends IrpcMetaData implements IrpcCommonConstants{

	@Getter @Setter private long timeoutMills = IGNORE_TIME_OUT;
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("IrpcProviderMetaData:[");
		
		sb.append("group:" + super.getGroup() + ",");
		sb.append("version:" + super.getVersion() + ",");
		sb.append("intefaceName:" + super.getInterfaceName() + ",");
		sb.append("serviceAddress:" + super.getServiceAddress() + ",");
		sb.append("timeoutMills:" + timeoutMills + ",");
		sb.append("]");
		
		return sb.toString();
	}
	
	
}
