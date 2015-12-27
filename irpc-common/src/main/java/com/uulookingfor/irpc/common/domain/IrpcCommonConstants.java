package com.uulookingfor.irpc.common.domain;

public interface IrpcCommonConstants {
	
	public static final byte[] 	IPRC_PROTOCOL = {'I','r','p','c'};
	
	public static final byte 	USE_VERSION = 0x01;
	
	public static final byte 	TYPE_REQUEST = 0x01;
	
	public static final byte 	TYPE_RESPONSE = 0x02;
	
	public static final byte 	TYPE_HEART_BEAT = 0x03;
	
	public static final byte 	RC_SUC = 's';
	
	public static final byte 	RC_UNUSE = 0;
	
	public static final byte 	DEF_OPTION = 0;
	
	public static final byte[] 	UNUSE_UID = {0,0,0,0,0,0,0,0};
	
	public static final int 	IPRC_PROTOCOL_LEN = 4;
	
	public static final int 	UID_LEN = 16;
	
	public static final int 	PROTOCOL_LEN_IRP_PROTOCOL = IPRC_PROTOCOL.length;
	
	public static final int 	PROTOCOL_LEN_VERSION = 1;
	
	public static final int 	PROTOCOL_LEN_TYPE = 1;
	
	public static final int 	PROTOCOL_LEN_RC = 1;
	
	public static final int 	PROTOCOL_LEN_OPTION = 1;
	
	public static final int 	PROTOCOL_LEN_UID = UNUSE_UID.length;
	
	public static final int 	PROTOCOL_LEN_META_LEN = 4;
	
	public static final int 	SIZE_OF_UID_BUFFER = 1024 * 30;
	
	public static final int 	SIZE_OF_IRPC_PROTOCOL_BUFFER = 1024 * 30;
	
	public static final long 	IGNORE_TIME_OUT = -1;
	
	public static final int 	DEF_SERVER_PORT = 9112;
}
