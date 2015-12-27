package com.uulookingfor.irpc.common.util;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import com.uulookingfor.icommon.asserts.Asserts;
import com.uulookingfor.icommon.queue.ByteBufferBlockingQueue;
import com.uulookingfor.irpc.common.domain.IrpcCommonConstants;

/**
 * @author suxiong.sx 
 */
public class UidGenerator implements IrpcCommonConstants{
	
	
	public static final int LENGTH_OF_LONG = 8;
		
	public static final AtomicLong uid = new AtomicLong();
	
	public static final long uptime = System.currentTimeMillis();
	
	private static ByteBufferBlockingQueue byteBufferQueue = 
			new ByteBufferBlockingQueue(SIZE_OF_UID_BUFFER,	LENGTH_OF_LONG);
	
	public static class Uid{
		@Getter @Setter private long uid;
		@Getter @Setter private long uptime;
		
		public Uid(){}
		
		public Uid(boolean increment){
			this.uid = UidGenerator.uid.getAndIncrement();
			this.uptime = UidGenerator.uptime;
		}
		
		public Uid(long uid, long uptime){
			this.uid = uid;
			this.uptime = uptime;
		}
		
		public String toString(){
			
			StringBuffer sb = new StringBuffer();
			
			sb.append(uid).append("-").append(uptime);
			
			return sb.toString();
		}
		
		public boolean equals(Object obj){
			
			if (obj instanceof Uid) {
				
				Uid uid = (Uid) obj;
				
				return uid.getUid() == this.uid && uid.getUptime() == this.uptime;
				
			}
			
			return false;
		}
		
		public int hashCode(){
			
			int prime = 63689 ;  
			
			return (int) (uid*prime + uptime) ;
			
		}
		public static Uid parseUid(@NonNull byte[] byteUid){
		
			if(byteUid.length != UID_LEN){
				throw new RuntimeException("byteUid len " + byteUid.length + " not " + UID_LEN
						+ " byteUid:" + byteUid.toString());
			}
			
			ByteBuffer bufferUid = outBlockingQueue();
			
			ByteBuffer bufferTimeStamp = outBlockingQueue();
			
			Asserts.notNull(bufferUid, "bufferUid cant be null");
			
			Asserts.notNull(bufferTimeStamp, "bufferTimeStamp cant be null"); 
			
			bufferUid.put(byteUid, 0, LENGTH_OF_LONG);
			
			bufferTimeStamp.put(byteUid, LENGTH_OF_LONG, LENGTH_OF_LONG);
			
			bufferUid.flip();
			
			bufferTimeStamp.flip();
			
			long uid = bufferUid.getLong();
			
			long timeStamp = bufferTimeStamp.getLong();
			
			inBlockingQueue(bufferUid);
			
			inBlockingQueue(bufferTimeStamp);
			
			return new Uid(uid, timeStamp);
			
		}
		
	}
	
	public static byte[] getNext(){
		
		ByteBuffer bufferUid = outBlockingQueue();
		
		ByteBuffer bufferTimeStamp = outBlockingQueue();
		
		Asserts.notNull(bufferUid, "bufferUid cant be null");
		
		Asserts.notNull(bufferTimeStamp, "bufferTimeStamp cant be null");
		
		bufferUid.putLong(uid.getAndIncrement());
		
		bufferTimeStamp.putLong(uptime);

		byte[] ret = new byte[UID_LEN];
		
		System.arraycopy(bufferUid.array(), 0, ret, 0, LENGTH_OF_LONG);
		
		System.arraycopy(bufferTimeStamp.array(), 0, ret, LENGTH_OF_LONG, LENGTH_OF_LONG);
		
		inBlockingQueue(bufferUid);
		
		inBlockingQueue(bufferTimeStamp);
		
		return ret;
	}
	
	public static byte[] from(@NonNull Uid uid){
		
		ByteBuffer bufferUid = outBlockingQueue();
		
		ByteBuffer bufferTimeStamp = outBlockingQueue();
		
		Asserts.notNull(bufferUid, "bufferUid cant be null");
		
		Asserts.notNull(bufferTimeStamp, "bufferTimeStamp cant be null");
		
		bufferUid.putLong(uid.getUid());
		
		bufferTimeStamp.putLong(uid.getUptime());

		byte[] ret = new byte[UID_LEN];
		
		System.arraycopy(bufferUid.array(), 0, ret, 0, LENGTH_OF_LONG);
		
		System.arraycopy(bufferTimeStamp.array(), 0, ret, LENGTH_OF_LONG, LENGTH_OF_LONG);
		
		inBlockingQueue(bufferUid);
		
		inBlockingQueue(bufferTimeStamp);
		
		return ret;
	}
	
	private static void inBlockingQueue(ByteBuffer item){
		byteBufferQueue.inQueue(item);
	}
	
	private static ByteBuffer outBlockingQueue(){
		return byteBufferQueue.deQueue();
	}
	
	public static void main(String[] args){
		long start = System.currentTimeMillis();
		for(int i=0; i<10; i++){
			
			byte[] b = getNext();
			
			Uid uid = Uid.parseUid(b);
			
			System.out.println(uid);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("spend " + (end -start) + " ms");
		
	}
	
}
