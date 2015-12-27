package com.uulookingfor.irpc.server.transport.netty.handler;

import java.util.concurrent.ExecutorService;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;
import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;
import com.uulookingfor.irpc.common.server.IrpcProviderTransPack;
import com.uulookingfor.irpc.server.IrpcProvider;
import com.uulookingfor.irpc.server.IrpcProviderHolder;
import com.uulookingfor.irpc.server.exception.IrpcServerException;
import com.uulookingfor.irpc.server.exception.IrpcServerExceptionCode;
import com.uulookingfor.irpc.server.executor.IrpcMultiThreadExcutor;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author suxiong.sx 
 */
public class ProviderAdapter extends ChannelHandlerAdapter {
	
	private static final ExecutorService providerExcutor = IrpcMultiThreadExcutor.providerExcutor;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		
		if(msg instanceof IrpcConsumerTransPack){
			
			handleIrpcConsumerTransPack(ctx, (IrpcConsumerTransPack) msg);
			
			return;
		}

	}
	
	private static IrpcProviderTransPack withoutProviderPack(String providerKey){
		
		IrpcProviderTransPack pack = new IrpcProviderTransPack();
		
		Object result = new IrpcServerException(
				IrpcServerExceptionCode.IRPC_SERVER_CANT_FIND_PROVIDER,
				"can't find provider for :" + providerKey
				);
		
		pack.setResult(result);
		
		return pack;
	}
	
	private static void handleIrpcConsumerTransPack(
			final ChannelHandlerContext ctx, 
			final IrpcConsumerTransPack msg){
		
		providerExcutor.execute(new Runnable(){

			@Override
			public void run() {
				
				IrpcConsumerTransPack consumerPack = (IrpcConsumerTransPack) msg;
				
				Asserts.notNull(consumerPack.getIrpcConsumerMetaDate(), "consumer meta null");
				
				String providerKey = consumerPack.getIrpcConsumerMetaDate().getInterfaceName();
				
				IrpcProvider provider = IrpcProviderHolder.get(providerKey);
				
				IrpcProviderTransPack providerPack = null;
				
				boolean isTimeOut = false;
				
				if(provider == null){
					providerPack = withoutProviderPack(providerKey);	
				}else{
					
					long startInvoke = System.currentTimeMillis();
			
					providerPack = provider.invoke(consumerPack);
					
					long endInvoke = System.currentTimeMillis();
			
					long timeOutMills = provider.getIrpcProviderMetaData().getTimeoutMills();
					
					if(timeOutMills == IrpcCommonConstants.IGNORE_TIME_OUT){
						//ignore timeout situation
					}else if((endInvoke - startInvoke) > timeOutMills){
						//time out , won't send any response;
						isTimeOut = true;
					}
				}
				
				if(isTimeOut == false){
					ctx.channel().writeAndFlush(providerPack);	
				}else{
					return;
				}
				
			}
			
		});
	}
}
