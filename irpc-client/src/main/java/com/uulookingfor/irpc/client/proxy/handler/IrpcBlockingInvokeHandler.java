package com.uulookingfor.irpc.client.proxy.handler;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;

import net.sf.cglib.beans.BeanCopier;
import lombok.NonNull;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.client.exception.IrpcClientException;
import com.uulookingfor.irpc.client.exception.IrpcClientExceptionCode;
import com.uulookingfor.irpc.client.transport.netty.IrpcNettyClient;
import com.uulookingfor.irpc.client.transport.netty.IrpcNettyClientFactory;
import com.uulookingfor.irpc.client.transport.netty.future.IrpcRequestFuture;
import com.uulookingfor.irpc.common.client.IrpcConsumerMetaData;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;
import com.uulookingfor.irpc.common.util.UidGenerator.Uid;

/**
 * @author suxiong.sx 
 */
public class IrpcBlockingInvokeHandler implements IrpcInvokeHandler, IrpcClientContext{

	private IrpcConsumerMetaData irpcConsumerMetaData;
	
	private IrpcNettyClient irpcNettyCliet;
	
	public IrpcBlockingInvokeHandler(@NonNull IrpcConsumerMetaData irpcConsumerMetaData) throws ExecutionException{
		
		Asserts.notNull(irpcConsumerMetaData.getGroup(), irpcConsumerMetaData + ", group can't be null");
		Asserts.notNull(irpcConsumerMetaData.getVersion(), irpcConsumerMetaData + ", version can't be null");
		Asserts.notNull(irpcConsumerMetaData.getServiceAddress(), irpcConsumerMetaData + ", serviceaddress can't be null");
		Asserts.notNull(irpcConsumerMetaData.getInterfaceName(), irpcConsumerMetaData + ", interface can't be null");
		
		this.irpcNettyCliet = IrpcNettyClientFactory.getNettyClient(irpcConsumerMetaData);
		
		this.irpcConsumerMetaData = irpcConsumerMetaData;
		
	}
	
	public Object invoke(Object proxy, Method method, Object[] parameters)
			throws Throwable {
		
		IrpcConsumerTransPack consumerPack = new IrpcConsumerTransPack();
		
		consumerPack.setIrpcConsumerMetaDate(irpcConsumerMetaData);
		consumerPack.setMethodName(method.getName());
		consumerPack.setParameterTypes(method.getParameterTypes());
		consumerPack.setParameters(parameters);
		consumerPack.setUid(new Uid(true));
		
		IrpcRequestFuture<Object> future = null;
		
		future = irpcNettyCliet.invoke(consumerPack);
		
		IrpcProviderTransPack providerPack =  providerPack(future);
		
		Object providerResult = providerPack.getResult();
		
		Object[] providerParameters = providerPack.getParameters();
		
		handleParam(parameters, providerParameters);
		
		handleThrowable(providerResult);
		
		return handleResult(providerResult);
		
	}
	
	private IrpcProviderTransPack providerPack(IrpcRequestFuture<Object> future) throws IrpcClientException, InterruptedException, ExecutionException{
		
		Object futureRet = future.get();
		
		if(!(futureRet instanceof IrpcProviderTransPack)){
			throw new IrpcClientException(
						IrpcClientExceptionCode.IRPC_CLIENT_UNKNOW_PROVIDER_PACK,
						"unknow provider pack, class type:" + future.get());
		}
		
		IrpcProviderTransPack providerPack =  (IrpcProviderTransPack) futureRet;
		
		return providerPack;
		
	}
	private void handleParam(Object[] consumerParameters, Object[] providerParameters){
		
		if(providerParameters == null){
			consumerParameters = null;
			return;
		}
		
		
		for(int i=0; i<providerParameters.length; i++){
			copyParameters(providerParameters[i], consumerParameters[i]);
		}
		
	}
		
	private void handleThrowable(Object providerResult) throws Throwable{
		
		if(providerResult instanceof Throwable){
			throw (Throwable)providerResult;
		}
		
	}
		
	private Object handleResult(Object providerResult){
		
		return providerResult;
	}
	
	private void copyParameters(Object from, Object to){
		
		BeanCopier bc = BeanCopier.create(from.getClass(), to.getClass(), false);
		bc.copy(from, to, null);
		
	}

}
