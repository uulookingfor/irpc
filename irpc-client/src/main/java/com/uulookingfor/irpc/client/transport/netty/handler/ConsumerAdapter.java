package com.uulookingfor.irpc.client.transport.netty.handler;

import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.client.exception.IrpcClientException;
import com.uulookingfor.irpc.client.exception.IrpcClientExceptionCode;
import com.uulookingfor.irpc.client.transport.netty.future.IrpcRequestFuture;
import com.uulookingfor.irpc.client.transport.netty.future.IrpcRequestFuturePool;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ConsumerAdapter extends ChannelHandlerAdapter implements IrpcClientContext{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof IrpcProviderTransPack){
			
			IrpcProviderTransPack pack = (IrpcProviderTransPack) msg;
			
			IrpcRequestFuture<Object> requestFuture = IrpcRequestFuturePool.futurePool.remove(pack.getUid());
			
			if(requestFuture == null){
				
				throw new IrpcClientException(
						IrpcClientExceptionCode.IRPC_CLIENT_EXCEPTION_ON_TRIG_FUTURE,
						"without this future for :" + pack.getUid());
			}
			
			if(!requestFuture.set(pack)){
				throw new IrpcClientException(
						IrpcClientExceptionCode.IRPC_CLIENT_EXCEPTION_ON_TRIG_FUTURE,
						"unKnow exception when set object to future:" + requestFuture.get());
			}
			
			return;
		}
		
		throw new IrpcClientException(
				IrpcClientExceptionCode.IRPC_CLIENT_UNKNOWN_PROVIDER_TRANSPACK,
				"unKnow provider trans pack, type:" + msg.getClass());
	}
		
}
