package com.uulookingfor.irpc.common.serialize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.common.exception.IrpcCommonSerializeException;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.NonNull;

public class ProtoStuffSerializer implements Serializer{

	private static final Objenesis objenesis = new ObjenesisStd(true);
	
	private static final Map<Class<?>, Schema<?>> cache = new ConcurrentHashMap<Class<?>, Schema<?>>();
	
	@Override
	public <T> byte[] serialize(@NonNull T obj) {
		
		@SuppressWarnings("unchecked")
		Schema<T> schema = getSchemaWithCache((Class<T>) obj.getClass());
		
		LinkedBuffer buffer = LinkedBuffer.allocate();
			
		return ProtostuffIOUtil.toByteArray(obj, schema, buffer);

	}

	@Override
	public <T> T deSerialize(@NonNull byte[] para, @NonNull Class<T> clazz) throws IrpcCommonSerializeException {
		
		T obj = objenesis.newInstance(clazz);
		
		Schema<T> schema = getSchemaWithCache(clazz);
		
		ProtostuffIOUtil.mergeFrom(para, obj, schema);
		
		return obj;
	}

	@SuppressWarnings("unchecked")
	private <T> Schema<T> getSchemaWithCache(Class<T> clazz){
		
		Schema<T> schema = null;
		
		if((schema = ((Schema<T>) cache.get(clazz))) != null){
			return schema;
		}
		
		cache.put(clazz, RuntimeSchema.createFrom(clazz));
		
		schema = (Schema<T>) cache.get(clazz);
		
		Asserts.notNull(schema, "schema for " + clazz + " excepion when put into map");
		
		return schema;
	}
}
