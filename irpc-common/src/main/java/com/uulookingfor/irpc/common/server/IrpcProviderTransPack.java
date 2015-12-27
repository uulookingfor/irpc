package com.uulookingfor.irpc.common.server;

import com.uulookingfor.irpc.common.util.UidGenerator.Uid;

import lombok.Getter;
import lombok.Setter;

public class IrpcProviderTransPack {
	
	@Getter @Setter private Uid uid;
	
	@Getter @Setter private Object[] parameters;
	
	@Getter @Setter private Object result;
	
}

