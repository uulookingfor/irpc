package com.uulookingfor.irpc.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.uulookingfor.icommon.collection.StringUtil;
import com.uulookingfor.irpc.client.domain.IrpcClientConstants;
import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.client.exception.IrpcClientException;
import com.uulookingfor.irpc.client.exception.IrpcClientExceptionCode;
import com.uulookingfor.irpc.client.proxy.IrpcProxy;
import com.uulookingfor.irpc.common.client.IrpcConsumerMetaData;

public class IrpcConsumer implements IrpcClientConstants, IrpcClientContext{
	
	private IrpcConsumerMetaData irpcConsumerMetaData = new IrpcConsumerMetaData();
	
	private AtomicBoolean inited = new AtomicBoolean(false);
	
	private Object consumerProxy;
	
	public IrpcConsumer(){
		setGroup(DEFAULT_GROUP);
		setVersion(DEFAULT_VERSION);
	}

	public void setGroup(String group){
		irpcConsumerMetaData.setGroup(group);
	}
	
	public void setVersion(String version){
		irpcConsumerMetaData.setVersion(version);
	}
	
	public void setInterfaceName(String interfaceName){
		irpcConsumerMetaData.setInterfaceName(interfaceName);;
	}
	public void setServiceAddress(String serviceAddress){
		irpcConsumerMetaData.setServiceAddress(serviceAddress);
	}	
	public Object getConsumerProxy(){
		return consumerProxy;
	}
	
	public void init() throws IrpcClientException{
		if(inited.get()){
			return;
		}
		
		interfaceValidCheck(irpcConsumerMetaData.getInterfaceName());
		
		try {
			
			consumerProxy = IrpcProxy.newProxyInstance(
					Class.forName(irpcConsumerMetaData.getInterfaceName()),
					irpcConsumerMetaData);
			
		} catch (ClassNotFoundException e) {
			throw new IrpcClientException(IrpcClientExceptionCode.IRPC_CLIENT_CLASS_NOT_FIND, e);
		} catch (ExecutionException e){
			throw new IrpcClientException(IrpcClientExceptionCode.IRPC_CLIENT_PROXY_CREATE_EXCEPTION, e);
		}
		
		inited.set(true);
	}
	
	private void interfaceValidCheck(String interfaceName) throws IrpcClientException{
		
		if(StringUtil.emptyString(interfaceName)){
			throw new IrpcClientException(IrpcClientExceptionCode.IRPC_CLIENT_INTERFACENAME_EMPTY, 
					"interfaceName can't be empty");
		}
		
		//and so on...
	}
}
