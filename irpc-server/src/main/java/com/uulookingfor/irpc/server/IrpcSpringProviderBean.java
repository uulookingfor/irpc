package com.uulookingfor.irpc.server;

import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;
import com.uulookingfor.irpc.server.exception.IrpcServerException;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class IrpcSpringProviderBean{
	
	@Getter @Setter private String group ;
	
	@Getter @Setter private String version ;
	
	@Getter @Setter private String interfaceName;
	
	@Getter @Setter private long timeoutMills = IrpcCommonConstants.IGNORE_TIME_OUT;
	
	@Getter @Setter private Object instance;
	
	public void init() throws IrpcServerException{
		
		IrpcProviderHolder.put(this.interfaceName, convertToIrpcProvider(this));
		
	}
	
	public static IrpcProvider convertToIrpcProvider(@NonNull IrpcSpringProviderBean springProvider) throws IrpcServerException{
		
		return IrpcProvider.createIrpcProvider(
				
				springProvider.getGroup(), 
				springProvider.getVersion(), 
				springProvider.getInterfaceName(), 
				springProvider.getTimeoutMills(),
				springProvider.getInstance()
				
				);
	}
	
}
