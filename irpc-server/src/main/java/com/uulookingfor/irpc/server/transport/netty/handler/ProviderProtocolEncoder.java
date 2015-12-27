package com.uulookingfor.irpc.server.transport.netty.handler;

import com.uulookingfor.irpc.common.protocol.IrpcProtocol;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;
import com.uulookingfor.irpc.server.exception.IrpcServerException;
import com.uulookingfor.irpc.server.exception.IrpcServerExceptionCode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProviderProtocolEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		
		if(msg instanceof IrpcProviderTransPack){
			
			IrpcProtocol.encodeIrpcProviderTransPack((IrpcProviderTransPack) msg, out);
	
			return;
			
		}
		
		throw new IrpcServerException(IrpcServerExceptionCode.IRPC_SERVER_UNKNOWN_PROVIDER_TRANSPACK,
				"Trans pack's class type " + msg.getClass());
		
	}
	
}
