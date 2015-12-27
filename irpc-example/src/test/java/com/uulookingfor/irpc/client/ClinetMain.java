package com.uulookingfor.irpc.client;

import io.netty.util.concurrent.DefaultExecutorServiceFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.uulookingfor.irpc.server.TestService;
import com.uulookingfor.irpc.server.domain.TestParam;
import com.uulookingfor.irpc.server.domain.TestResult;

public class ClinetMain {
	
	public static final int NUM_OF_THREADS = 20;
	
	public static final ExecutorService excutor	= 
			new DefaultExecutorServiceFactory("client-main").newExecutorService(NUM_OF_THREADS);
	
	public static void main(String[] args) throws IOException{
		
		String config = ClinetMain.class.getPackage().getName().replace('.', '/') + "/appContext.xml";
		
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(config);
        
        context.start();
        
        TestService testService = (TestService) context.getBean("testService");
       
        testPrint(testService);
        
        testResult(testService);
        
        testParamModified(testService);

        testException(testService);
      
        testLocalXTimesSpendTime(testService, 10000, 50);
        
        System.in.read();
	}
	
	private static void testPrint(TestService testService){
		testService.print();
	}
	
	private static void testResult(TestService testService){
		
		TestResult<String> ret = testService.withResult();
		
		System.out.println("testResult:" + ret);
	}
	
	private static void testParamModified(TestService testService){
		
		TestParam param = new TestParam();
		
        param.setSParam("client param");
        param.setLParam(90L);
        param.setIParam(990);
        
        System.out.println("before send to server, param:" + param);
        
        TestResult<TestParam> ret = testService.withParamChange(param);
        
        System.out.println("after send to server, param:" + param);
        System.out.println("after send to server, ret:" + ret);
	}
	
	private static void testException(TestService testService){
		
		try {
			testService.withException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testLocalXTimesSpendTime(final TestService testService, final int x, final int nThreads){
		
		System.out.println("test " + x + " times " + nThreads + " threads " + "start , just wait ...");
		
		for(int i=0; i< 20000; i++){
			testService.withResult();
		}
		
		final CountDownLatch latch = new CountDownLatch(nThreads);
		
		long start = System.nanoTime();
		
		for(int i=0; i<nThreads; i++){
			
			excutor.execute(new Runnable(){
				
				@Override
				public void run() {
					
					for(int i=0; i< x; i++){
						testService.withResult();
					}
					latch.countDown();
					
				}
				
			});
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.nanoTime();
		
		System.out.println("spend " + (end - start) + " ns "
				+ "for " + nThreads + " threads, "
				+ "and every thread run " + x + " times");
		

	}
	
}
