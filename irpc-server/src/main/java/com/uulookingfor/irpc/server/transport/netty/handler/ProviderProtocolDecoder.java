package com.uulookingfor.irpc.server.transport.netty.handler;

import java.util.List;

import com.google.common.base.Optional;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.protocol.IrpcProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ProviderProtocolDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		
		Optional<IrpcConsumerTransPack> consumerTranPack =
				IrpcProtocol.decodeIrpcConsumerTransPack(in);
		
		if(consumerTranPack.isPresent()){
			out.add(consumerTranPack.get());
		}
		
	}

}
