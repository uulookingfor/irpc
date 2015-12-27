package com.uulookingfor.irpc.server;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author suxiong.sx 
 */
public class ServerMain {
	
	public static void main(String[] args) throws IOException{
		
		String config = ServerMain.class.getPackage().getName().replace('.', '/') + "/appContext.xml";
		
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        
        context.start();
        
        System.out.println("Server start , wait client to connect, press enter to exit server...");
        
        System.in.read();
        
	}
}
