package com.uulookingfor.irpc.server.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author suxiong.sx 
 */
public class TestParam {
	
	@Getter @Setter private String sParam;
	
	@Getter @Setter private Long lParam;
	
	@Getter @Setter private int iParam;
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("[");
		sb.append("sParam:" + sParam).append(",");
		sb.append("lParam:" + lParam).append(",");
		sb.append("iParam:" + iParam);
		sb.append("]");
		
		return sb.toString();
	}
	
}
