package com.uulookingfor.irpc.server;

import com.uulookingfor.irpc.server.domain.TestParam;
import com.uulookingfor.irpc.server.domain.TestResult;

/**
 * @author suxiong.sx 
 */
public class DefaultTest implements TestService{

	private static final String SERVER_HELLO = "hello world from irpc servier side";
	
	private static final String APPENDIX = ", we append some thing here";
	
	@Override
	public void print() {
		System.out.println(SERVER_HELLO);
	}

	@Override
	public TestResult<String> withResult() {
		
		TestResult<String> ret = new TestResult<String>();
		
		ret.setModel(SERVER_HELLO);
		ret.setSuccess(true);
		
		return ret;
	}

	@Override
	public TestResult<TestParam> withParamChange(TestParam param) {
		
		TestResult<TestParam> ret = new TestResult<TestParam>();
		
		TestParam model = param;
		model.setSParam(param.getSParam() + APPENDIX);
		model.setLParam(param.getLParam() + 101L);
		model.setIParam(param.getIParam() + 202);
		
		ret.setModel(model);
		
		return ret;
	}

	@Override
	public void withException() throws Exception {
		
		throw new RuntimeException(SERVER_HELLO + ", this is a exception test");
	}

}
