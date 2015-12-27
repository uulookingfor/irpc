package com.uulookingfor.irpc.common.serialize;

import com.uulookingfor.irpc.common.exception.IrpcCommonSerializeException;

public interface Serializer {
	
	<T> byte[] serialize(T obj);
	
	<T> T deSerialize(byte[] para, Class<T> clazz) throws IrpcCommonSerializeException;
}
