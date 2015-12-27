package com.uulookingfor.irpc.client.transport.netty.future;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.uulookingfor.irpc.client.domain.IrpcClientConstants;
import com.uulookingfor.irpc.common.util.UidGenerator.Uid;

/**
 * @author suxiong.sx 
 */
public class IrpcRequestFuturePool implements IrpcClientConstants{

	public static ConcurrentMap<Uid, IrpcRequestFuture<Object>> futurePool = new ConcurrentHashMap<Uid, IrpcRequestFuture<Object>>(DEFAULT_REQUEST_POOL_SIZE);

}
