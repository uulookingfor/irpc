package com.uulookingfor.irpc.server;

import java.net.UnknownHostException;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.common.client.IrpcConsumerMetaData;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.server.IrpcProviderMetaData;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;
import com.uulookingfor.irpc.common.util.AddressBuilder;
import com.uulookingfor.irpc.common.util.UidGenerator.Uid;
import com.uulookingfor.irpc.server.exception.IrpcServerException;
import com.uulookingfor.irpc.server.exception.IrpcServerExceptionCode;
import com.uulookingfor.irpc.server.reflect.CglibFastClass;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author suxiong.sx 
 */
public class IrpcProvider {
	
	@Getter @Setter private IrpcProviderMetaData irpcProviderMetaData = new IrpcProviderMetaData();
	
	@Getter @Setter private Object instance;
	
	public IrpcProviderTransPack invoke(@NonNull IrpcConsumerTransPack consumerTransPack){
		
		Asserts.notNull(instance, "instance can't be null for " + irpcProviderMetaData);
		
		Asserts.notFalse(irpcProviderMetaData.isValid(), "irpcProviderMetaData illegal " + irpcProviderMetaData);
		
		Asserts.notNull(consumerTransPack.getIrpcConsumerMetaDate(), "consumer meta null");
		
		IrpcConsumerMetaData irpcConsumerMetaData = consumerTransPack.getIrpcConsumerMetaDate();
		
		Object result = null;
		
		if(!equals(irpcProviderMetaData.getInterfaceName(), irpcConsumerMetaData.getInterfaceName())){
			result = new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_INTERFACE_NOT_EQUAL,
					exceptionMsg("interface not match", irpcProviderMetaData.getInterfaceName(), irpcConsumerMetaData.getInterfaceName())
					);
			return newPack(consumerTransPack.getUid(), consumerTransPack.getParameters(), result);
		}
		
		if(!equals(irpcProviderMetaData.getServiceAddress(), irpcConsumerMetaData.getServiceAddress())){
			result = new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_ADDRESS_NOT_EQUAL,
					exceptionMsg("service not match", irpcProviderMetaData.getServiceAddress(), irpcConsumerMetaData.getServiceAddress())
					);
			
			return newPack(consumerTransPack.getUid(), consumerTransPack.getParameters(), result);
		}
		
		if(!equals(irpcProviderMetaData.getVersion(), irpcConsumerMetaData.getVersion())){
			result = new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_VERSION_NOT_EQUAL,
					exceptionMsg("version not match", irpcProviderMetaData.getVersion(), irpcConsumerMetaData.getVersion())
					);
			return newPack(consumerTransPack.getUid(), consumerTransPack.getParameters(), result);
		}
		
		if(!equals(irpcProviderMetaData.getGroup(), irpcConsumerMetaData.getGroup())){
			result = new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_GROUP_NOT_EQUAL,
					exceptionMsg("group not match", irpcProviderMetaData.getGroup(), irpcConsumerMetaData.getGroup())
					);
			return newPack(consumerTransPack.getUid(), consumerTransPack.getParameters(), result);
		}
		
		try {
			
			result = CglibFastClass.invoke(
					consumerTransPack.getMethodName(),
					consumerTransPack.getParameterTypes(), 
					this.instance, 
					consumerTransPack.getParameters());
			
		} catch (Throwable e) {
			result = e;
		}
		
		return newPack(consumerTransPack.getUid(), consumerTransPack.getParameters(), result);	
	} 
	
	public static IrpcProvider createIrpcProvider(
			String group,
			String version,
			String interfaceName,
			long timeoutMills,
			Object obj) throws IrpcServerException{
		
		IrpcProvider irpcProvider = new IrpcProvider();
		
		irpcProvider.getIrpcProviderMetaData().setGroup(group);
		irpcProvider.getIrpcProviderMetaData().setVersion(version);
		irpcProvider.getIrpcProviderMetaData().setInterfaceName(interfaceName);
		irpcProvider.getIrpcProviderMetaData().setTimeoutMills(timeoutMills);
		irpcProvider.setInstance(obj);
		
		try {
			irpcProvider.getIrpcProviderMetaData().setServiceAddress(AddressBuilder.localAddress());
		} catch (UnknownHostException e) {
			throw new IrpcServerException(
					IrpcServerExceptionCode.IRPC_SERVER_BUILD_ADDRESS_EXCEPTION,
					"can't get address when create IrpcProvider " + irpcProvider 
					);
		}
		
		return irpcProvider;
	}
	
	public String toString(){
		return irpcProviderMetaData + "-" + instance;
	}
	private boolean equals(@NonNull Object a, Object b){
		
		return a.equals(b);
		
	}
	
	private String exceptionMsg(String prefix , Object provider, Object consumer){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(prefix).append("==>");
		sb.append("provider:").append(provider).append(",");
		sb.append("consumer:").append(consumer);
		
		return sb.toString();
	}
	
	private static IrpcProviderTransPack newPack(Uid uid , Object[] params, Object result){
		
		IrpcProviderTransPack pack = new IrpcProviderTransPack();
		
		pack.setUid(uid);
		pack.setParameters(params);
		pack.setResult(result);
		
		return pack;
	}
}
