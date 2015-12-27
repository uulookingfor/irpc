package com.uulookingfor.irpc.common.protocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import io.netty.buffer.ByteBuf;

import com.google.common.base.Optional;
import com.uulookingfor.icommon.queue.ByteBufferBlockingQueue;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;
import com.uulookingfor.irpc.common.domain.IrpcCommonContext;
import com.uulookingfor.irpc.common.exception.IrpcCommonException;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;
import com.uulookingfor.irpc.common.util.UidGenerator;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


/**
 * @author suxiong.sx
 *  
 * Request Protocol
 * [0,3] 	protocal 
 * [4,4] 	version
 * [5,5] 	type
 * [6,6] 	result code
 * [7,7] 	option
  *[8,11]	meta length
 * [12,?]   meta  
 */

public class IrpcProtocol implements IrpcCommonConstants, IrpcCommonContext{
	
	public static int PROTOCOL_LEN_WITHOUT_META = 0 
			+ PROTOCOL_LEN_IRP_PROTOCOL 
			+ PROTOCOL_LEN_VERSION
			+ PROTOCOL_LEN_TYPE
			+ PROTOCOL_LEN_RC
			+ PROTOCOL_LEN_OPTION
			+ PROTOCOL_LEN_META_LEN;
	
	
	@Getter @Setter private byte[] 	protocal;
	
	@Getter @Setter private byte 	version;
	
	@Getter @Setter private byte 	type;
	
	@Getter @Setter private byte 	rc;
	
	@Getter @Setter private byte 	option;
	
	@Getter @Setter private int		metaLen;
	
	@Getter @Setter private byte[] 	meta;
	
	public static void encodeIrpcConsumerTransPack(
			@NonNull IrpcConsumerTransPack pack, 
			@NonNull ByteBuf out){
			
		byte[] packBytes = serializer.serialize(pack);
			
		out
		.writeBytes(IPRC_PROTOCOL)
		.writeByte(USE_VERSION)
		.writeByte(TYPE_REQUEST)
		.writeByte(RC_UNUSE)
		.writeByte(DEF_OPTION)
		.writeInt(packBytes.length)
		.writeBytes(packBytes);
		
	}
	
	public static void encodeIrpcProviderTransPack(
			@NonNull IrpcProviderTransPack pack, 
			@NonNull ByteBuf out){
			
		byte[] packBytes = serializer.serialize(pack);
			
		out
		.writeBytes(IPRC_PROTOCOL)
		.writeByte(USE_VERSION)
		.writeByte(TYPE_RESPONSE)
		.writeByte(RC_SUC)
		.writeByte(DEF_OPTION)
		.writeInt(packBytes.length)
		.writeBytes(packBytes);
		
	}
	
	
	public static Optional<IrpcConsumerTransPack> decodeIrpcConsumerTransPack(
			@NonNull ByteBuf in) throws IrpcCommonException{
		
		int origReadIndex = in.readerIndex();
		
		if(in.readableBytes() < PROTOCOL_LEN_WITHOUT_META){
			return Optional.absent();
		}
		
		Optional<IrpcProtocol> protocol = protocolFromByteBuf(in);
		
		if(!protocol.isPresent()){
			
			in.setIndex(origReadIndex, in.writerIndex());
			
			return Optional.absent();
		}
		
		validIrpcConsumerTransPack(
				protocol.get().getProtocal(), 
				protocol.get().getVersion(), 
				protocol.get().getType(), 
				protocol.get().getRc(), 
				protocol.get().getOption());
		
		IrpcConsumerTransPack pack = serializer.deSerialize(protocol.get().getMeta(), IrpcConsumerTransPack.class);
		
		return Optional.of(pack);
		
	}
	
	public static Optional<IrpcProviderTransPack> decodeIrpcProviderTransPack(
			@NonNull ByteBuf in) throws IrpcCommonException{
		
		int origReadIndex = in.readerIndex();
		
		if(in.readableBytes() < PROTOCOL_LEN_WITHOUT_META){
			return Optional.absent();
		}
		
		Optional<IrpcProtocol> protocol = protocolFromByteBuf(in);
		
		if(!protocol.isPresent()){
			
			in.setIndex(origReadIndex, in.writerIndex());
			
			return Optional.absent();
		}
		
		validIrpcProviderTransPack(
				protocol.get().getProtocal(), 
				protocol.get().getVersion(), 
				protocol.get().getType(), 
				protocol.get().getRc(), 
				protocol.get().getOption());
		
		IrpcProviderTransPack pack = serializer.deSerialize(protocol.get().getMeta(), IrpcProviderTransPack.class);
		
		return Optional.of(pack);
		
	}
	
	private static Optional<IrpcProtocol> protocolFromByteBuf(ByteBuf in){
		
		IrpcProtocol ret = new IrpcProtocol();
		
		ret.setProtocal(new byte[PROTOCOL_LEN_IRP_PROTOCOL]);

		in.readBytes(ret.getProtocal());
		
		ret.setVersion(in.readByte());
		ret.setType(in.readByte());
		ret.setRc(in.readByte());
		ret.setOption(in.readByte());
		ret.setMetaLen(in.readInt());
		
		if(in.readableBytes() < ret.getMetaLen()){
			return Optional.absent();
		}
		
		ret.setMeta(new byte[ret.getMetaLen()]);
		
		in.readBytes(ret.getMeta());
		
		return Optional.of(ret);
		
	}
	public static void validIrpcConsumerTransPack(
			byte[] irpcProtocolBuf,
			byte version,
			byte type,
			byte rc,
			byte option) throws IrpcCommonException{
		
		if(!validProtocol(irpcProtocolBuf)){
			throw new IrpcCommonException("protocal not match");
		}
		
		if(!validVersion(version)){
			throw new IrpcCommonException("version not match");
		}
		
		if(!requestType(type)){
			throw new IrpcCommonException("not request type:" + TYPE_REQUEST + " but:" + type);
		}
		
		if(!requesRc(rc)){
			throw new IrpcCommonException("not request rc:" + rc);
		}
		
		if(!validOption(option)){
			throw new IrpcCommonException("not valid option:" + option);
		}

	} 
	
	public static void validIrpcProviderTransPack(
			byte[] irpcProtocolBuf,
			byte version,
			byte type,
			byte rc,
			byte option) throws IrpcCommonException{
		
		if(!validProtocol(irpcProtocolBuf)){
			throw new IrpcCommonException("protocal not match");
		}
		
		if(!validVersion(version)){
			throw new IrpcCommonException("version not match");
		}
		
		if(!reponseType(type)){
			throw new IrpcCommonException("not response type:" + TYPE_RESPONSE + " but:" + type);
		}
		
		if(!reponseRc(rc)){
			throw new IrpcCommonException("not request rc:" + rc);
		}
		
		if(!validOption(option)){
			throw new IrpcCommonException("not valid option:" + option);
		}

	} 
	
	private static boolean validProtocol(byte[] irpcProtocolBuf){
		
		if(irpcProtocolBuf == null){
			return false;
		}
		
		if(irpcProtocolBuf.length != PROTOCOL_LEN_IRP_PROTOCOL){
			return false;
		}
		
		boolean match = true;
		for(int i=0; i < irpcProtocolBuf.length ; i++){
			
			match = irpcProtocolBuf[i] == IPRC_PROTOCOL[i];
			
			if(!match){
				break;
			}
		}
		
		return match;
	}
	
	private static boolean validVersion(byte version){
		return version == USE_VERSION;
	}
	
	private static boolean requestType(byte type){
		return type == TYPE_REQUEST;
	}
	
	private static boolean reponseType(byte type){
		return type == TYPE_RESPONSE;
	}
	
	private static boolean requesRc(byte rc){
		return rc == RC_UNUSE;
	}
	
	private static boolean reponseRc(byte rc){
		return rc == RC_SUC;
	}
	
	private static boolean validOption(byte option){
		return option == DEF_OPTION;
	}
}
