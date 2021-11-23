package Msg;

import java.io.Serializable;
import Protocol.MsgProtocol;

public class AuthRes extends ServerMsg implements Serializable{
	public AuthRes(){
	}
	
	public AuthRes(String s){
		super(s);
		this.msg_type = MsgProtocol.DGT_AUTH_RES_MSG;
	}
}
