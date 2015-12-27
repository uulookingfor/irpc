package com.uulookingfor.irpc.client.transport.netty;

import com.uulookingfor.irpc.client.domain.IrpcClientConstants;
import com.uulookingfor.irpc.client.domain.IrpcClientContext;
import com.uulookingfor.irpc.client.exception.IrpcClientException;
import com.uulookingfor.irpc.client.exception.IrpcClientExceptionCode;
import com.uulookingfor.irpc.client.transport.netty.future.IrpcRequestFuture;
import com.uulookingfor.irpc.client.transport.netty.future.IrpcRequestFuturePool;
import com.uulookingfor.irpc.client.transport.netty.handler.ConsumerAdapter;
import com.uulookingfor.irpc.client.transport.netty.handler.ConsumerProtocolDecoder;
import com.uulookingfor.irpc.client.transport.netty.handler.ConsumerProtocolEncoder;
import com.uulookingfor.irpc.common.client.IrpcConsumerTransPack;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author suxiong.sx 
 */
public class IrpcNettyClient implements IrpcClientConstants, IrpcClientContext{
	
	@Getter @Setter private Channel channel;
	
	public static final EventLoopGroup workerGroup = new NioEventLoopGroup();
	
	public static IrpcNettyClient createIrpcNettyClient(String host, int port, int timeOut){
		
		IrpcNettyClient ret = new IrpcNettyClient();
		
		Bootstrap bootStrap = new Bootstrap();
		
		bootStrap.group(workerGroup);
		bootStrap.channel(NioSocketChannel.class);
		bootStrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootStrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		bootStrap.option(ChannelOption.TCP_NODELAY, true);
		bootStrap.option(ChannelOption.SO_REUSEADDR, true);
		
		((NioEventLoopGroup)workerGroup).setIoRatio(100);;
		
		bootStrap.handler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(
						new ConsumerProtocolEncoder(),
						new ConsumerProtocolDecoder(),
						new ConsumerAdapter());
			}
			
		});
		
		ChannelFuture future = bootStrap.connect(host, port);
			
		if(future.awaitUninterruptibly(timeOut) && future.isSuccess() && future.channel().isActive()){
			
			ret.setChannel(future.channel());
			
			return ret;
			
		}else{
			
			future.cancel(true);
            future.channel().close();
            
            throw new RuntimeException("IrpcNettyClient connect host " + host
            		+ " port " + port + " timeout " + timeOut + " exception");
            
		}	
	}
	
	public IrpcRequestFuture<Object> invoke(@NonNull final IrpcConsumerTransPack pack) throws IrpcClientException{
		
		final IrpcRequestFuture<Object> requestFuture =  new IrpcRequestFuture<Object>();
		
		IrpcRequestFuturePool.futurePool.put(pack.getUid(), requestFuture);
		
		ChannelFuture channelFuture = this.channel.writeAndFlush(pack);
		
		channelFuture.addListener(new ChannelFutureListener() {
			
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if(future.isSuccess()){
					//may be do some thing here
				}else{
					
					IrpcRequestFuturePool.futurePool.remove(pack.getUid());
					
					throw new IrpcClientException(
							IrpcClientExceptionCode.IRPC_CLIENT_EXCEPTION_ON_SEND, "" 
									+ "uid:" + pack.getUid() 
									+ " send exception"
									+ " future:" + requestFuture);
				}
			}
			
		});
		
		return requestFuture;
			
	}
}