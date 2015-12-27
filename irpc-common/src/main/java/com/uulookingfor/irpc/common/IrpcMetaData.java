package com.uulookingfor.irpc.common;

import lombok.Getter;
import lombok.Setter;

public class IrpcMetaData {
	
	@Getter @Setter private String group ;
	
	@Getter @Setter private String version ;
	
	@Getter @Setter private String interfaceName;
	
	@Getter @Setter private String serviceAddress;
	
	public boolean isValid(){
		
		if(this.group == null){
			return false;
		}
		
		if(this.version == null){
			return false;
		}
		
		if(this.interfaceName == null){
			return false;
		}
		
		if(this.serviceAddress == null){
			return false;
		}
		
		return true;
	}
}
