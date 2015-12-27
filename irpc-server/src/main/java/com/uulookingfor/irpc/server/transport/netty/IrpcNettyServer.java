package com.uulookingfor.irpc.server.transport.netty;

import java.util.concurrent.atomic.AtomicBoolean;

import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;
import com.uulookingfor.irpc.server.domain.IrpcServerConstants;
import com.uulookingfor.irpc.server.transport.netty.handler.ProviderAdapter;
import com.uulookingfor.irpc.server.transport.netty.handler.ProviderProtocolDecoder;
import com.uulookingfor.irpc.server.transport.netty.handler.ProviderProtocolEncoder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author suxiong.sx 
 */
public class IrpcNettyServer implements IrpcServerConstants{

	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(); 
	
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
        
    private int port;
    
    private AtomicBoolean inited = new AtomicBoolean(false);

    public IrpcNettyServer() {
        this.port = IrpcCommonConstants.DEF_SERVER_PORT;
    }
    
    public IrpcNettyServer(int port) {
        this.port = port;
    }

    public synchronized void start() throws Exception {
        
    	if(inited.get()){
    		return;
    	}
    	
    	ServerBootstrap b = new ServerBootstrap();
    	
    	b.group(bossGroup, workerGroup)
    	.channel(NioServerSocketChannel.class)
    	.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
    	.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
    	.childOption(ChannelOption.TCP_NODELAY, true)
    	.childOption(ChannelOption.SO_REUSEADDR, true)
    	.childHandler(new ChannelInitializer<SocketChannel>() { 
    		@Override
    		public void initChannel(SocketChannel ch) throws Exception {
    			ch.pipeline()
    			.addLast(new ProviderProtocolEncoder())
    			.addLast(new ProviderProtocolDecoder())
    			.addLast(new ProviderAdapter());
    		}
    	});
    	
    	ChannelFuture bindFuture = b.bind(port).sync(); 
    	
    	bindFuture.await();
    	
    	inited.compareAndSet(false, true);

    }

    public void stop(){
    	workerGroup.shutdownGracefully();
    	bossGroup.shutdownGracefully();
    	
    }
    public static void main(String[] args) throws Exception {

        IrpcNettyServer server = new IrpcNettyServer(9112);
        
        server.start();
        
        System.out.println("server start ...");
        
        System.in.read();
        
        server.stop();
        
    	System.out.println("server stop");
    	
    	System.in.read();
        
    }
}