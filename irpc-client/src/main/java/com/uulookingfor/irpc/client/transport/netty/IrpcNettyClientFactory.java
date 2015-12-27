package com.uulookingfor.irpc.client.transport.netty;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import lombok.NonNull;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.client.domain.IrpcClientConstants;
import com.uulookingfor.irpc.common.client.IrpcConsumerMetaData;
import com.uulookingfor.irpc.common.util.AddressParser;

public class IrpcNettyClientFactory implements IrpcClientConstants{
	
	public static Cache<String , IrpcNettyClient> cache = CacheBuilder.newBuilder()
			.maximumSize(DEFAULT_FACTORY_CACHE_MAXIUMSIZE)
			.expireAfterWrite(DEFAULT_FACTORY_CACHE_EXPIRETIEM, TimeUnit.MINUTES)
			.removalListener(new RemovalListener<String , IrpcNettyClient>(){

				@Override
				public void onRemoval(RemovalNotification<String, IrpcNettyClient> paramRemovalNotification) {
					
					if(paramRemovalNotification.getValue().getChannel().isActive()){
						paramRemovalNotification.getValue().getChannel().close();
					}
					
				}
				
			})
			.build();
	
	public static IrpcNettyClient getNettyClient(
			@NonNull final IrpcConsumerMetaData irpcConsumerMetaData) throws ExecutionException{
		
		Asserts.notNull(irpcConsumerMetaData.getGroup(), irpcConsumerMetaData + ", group can't be null");
		Asserts.notNull(irpcConsumerMetaData.getVersion(), irpcConsumerMetaData + ", version can't be null");
		Asserts.notNull(irpcConsumerMetaData.getServiceAddress(), irpcConsumerMetaData + ", serviceaddress can't be null");
		Asserts.notNull(irpcConsumerMetaData.getInterfaceName(), irpcConsumerMetaData + ", interface can't be null");
		
		String cacheKey = uniqueNettyClientCacheKey(irpcConsumerMetaData);

		return cache.get(cacheKey, new Callable<IrpcNettyClient>(){
			
			@Override
			public IrpcNettyClient call() throws Exception {
				
				return IrpcNettyClient.createIrpcNettyClient(
						host(irpcConsumerMetaData), 
						port(irpcConsumerMetaData), 
						DEFAULT_CONNECT_TIMEOUT
						);
			}
			
		});

	}
	
	private static String uniqueNettyClientCacheKey(IrpcConsumerMetaData irpcConsumerMetaData){
		
		StringBuffer sb = new StringBuffer();
		//key = group-version-interface
		sb.append(irpcConsumerMetaData.getGroup() + "-");
		sb.append(irpcConsumerMetaData.getVersion()+ "-");
		sb.append(irpcConsumerMetaData.getInterfaceName() + "-");
				
		return sb.toString();
	}
	
	private static String host(IrpcConsumerMetaData irpcConsumerMetaData){
		return AddressParser.host(irpcConsumerMetaData.getServiceAddress());
	}
	
	private static int port(IrpcConsumerMetaData irpcConsumerMetaData){
		return AddressParser.port(irpcConsumerMetaData.getServiceAddress());
	}
}
