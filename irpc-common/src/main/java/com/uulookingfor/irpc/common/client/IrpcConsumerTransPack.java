package com.uulookingfor.irpc.common.client;

import com.uulookingfor.irpc.common.util.UidGenerator.Uid;

import lombok.Getter;
import lombok.Setter;

/**
 * @author suxiong.sx 
 */
public class IrpcConsumerTransPack {
	
	@Getter @Setter private IrpcConsumerMetaData irpcConsumerMetaDate;
	
	@Getter @Setter private String methodName; 
			
	@Getter @Setter private Class<?>[] parameterTypes;
	
	@Getter @Setter private Object[] parameters;
			
	@Getter @Setter private Uid uid;
	
}
