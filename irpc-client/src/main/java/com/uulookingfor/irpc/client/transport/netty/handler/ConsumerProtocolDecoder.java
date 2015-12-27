package com.uulookingfor.irpc.client.transport.netty.handler;

import java.util.List;

import com.google.common.base.Optional;
import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.common.protocol.IrpcProtocol;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author suxiong.sx 
 */
public class ConsumerProtocolDecoder extends ByteToMessageDecoder implements IrpcClientContext{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		Optional<IrpcProviderTransPack> providerTranPack =
				IrpcProtocol.decodeIrpcProviderTransPack(in);
		
		if(providerTranPack.isPresent()){
			out.add(providerTranPack.get());
		}
		
	}

}
