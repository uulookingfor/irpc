package com.uulookingfor.irpc.common.domain;

import com.uulookingfor.irpc.common.serialize.ProtoStuffSerializer;
import com.uulookingfor.irpc.common.serialize.Serializer;

public interface IrpcCommonContext {
	
	Serializer serializer = new ProtoStuffSerializer();
	
}
