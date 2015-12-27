package com.uulookingfor.irpc.server;

import com.uulookingfor.irpc.server.domain.TestParam;
import com.uulookingfor.irpc.server.domain.TestResult;

public interface TestService {
	/**
	 * just print some thing on service side
	 */
	void print();
	
	/**
	 * a method with String result 
	 */
	TestResult<String> withResult();
	
	/**
	 * a method with parameter change 
	 */
	TestResult<TestParam> withParamChange(TestParam param);
	
	/**
	 * a method who throws exception 
	 */
	void withException() throws Exception;
}
