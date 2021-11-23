package Msg;

import java.io.Serializable;
import Protocol.MsgProtocol;

public class AuthReq extends Msg implements Serializable{

	private static final long serialVersionUID = 1L;
	private char[] user_id = new char[33];
	private char[] password = new char[33];
	
	public AuthReq() {
		this.msg_type = MsgProtocol.DGT_AUTH_REQ_MSG;
	}
	
	public AuthReq(String id, String pwd){
		this.msg_type = MsgProtocol.DGT_AUTH_REQ_MSG;
		this.user_id = id.toCharArray();
		this.password = pwd.toCharArray();
		this.msg_len = (byte)((this.user_id.length + this.password.length + this.msg_type) & 0xff);		
	}	
	public char[] getId() {
		return this.user_id;
	}
	public char[] getPwd() {
		return this.password;
	}
}
