package com.uulookingfor.irpc.server.domain;

import lombok.Getter;
import lombok.Setter;

public class TestResult<T> {
	
	@Getter @Setter private boolean success;
	
	@Getter @Setter private T model;
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("succ:" + success).append(",");
		sb.append(model);
		
		return sb.toString();
	}
}