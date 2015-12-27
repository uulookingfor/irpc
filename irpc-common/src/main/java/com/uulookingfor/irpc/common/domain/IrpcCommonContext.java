package com.uulookingfor.irpc.common.domain;

import com.uulookingfor.irpc.common.serialize.ProtoStuffSerializer;
import com.uulookingfor.irpc.common.serialize.Serializer;

/**
 * @author suxiong.sx 
 */
public interface IrpcCommonContext {
	
	Serializer serializer = new ProtoStuffSerializer();
	
}
