package com.uulookingfor.irpc.client.transport.netty.handler;

import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.client.exception.IrpcClientException;
import com.uulookingfor.irpc.client.exception.IrpcClientExceptionCode;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.protocol.IrpcProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author suxiong.sx 
 */
public class ConsumerProtocolEncoder extends MessageToByteEncoder<Object> implements IrpcClientContext{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
			throws Exception {
		
		if(msg instanceof IrpcConsumerTransPack){
			
			IrpcProtocol.encodeIrpcConsumerTransPack((IrpcConsumerTransPack) msg, out);
			
			return;
			
		}
		
		throw new IrpcClientException(IrpcClientExceptionCode.IRPC_CLIENT_UNKNOWN_CONSUMER_TRANSPACK,
				"Trans pack's class type " + msg.getClass());

	}
	
	

}
