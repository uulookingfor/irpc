package com.uulookingfor.irpc.client;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author suxiong.sx 
 */
public class IrpcSpringConsumerBean implements FactoryBean{

	@Getter @Setter private String group ;
	
	@Getter @Setter private String version ;
	
	@Getter @Setter private String interfaceName;
	
	@Getter @Setter private String serviceAddress;

	private IrpcConsumer consumer;
	
	public synchronized void init() throws Exception{
		
		IrpcConsumer consumer = new IrpcConsumer();
		
		consumer.setGroup(group);
		consumer.setVersion(version);
		consumer.setInterfaceName(interfaceName);
		consumer.setServiceAddress(serviceAddress);
		
		consumer.init();
		
		this.consumer = consumer;
		
	}
	@Override
	public Object getObject() throws Exception {
		return consumer.getConsumerProxy();
	}

	@Override
	public Class<?> getObjectType() {
		
		if(consumer.getConsumerProxy()== null){
			return null;
		}

		return consumer.getConsumerProxy().getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
