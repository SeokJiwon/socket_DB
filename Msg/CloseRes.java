package Msg;

import java.io.Serializable;
import Protocol.MsgProtocol;

public class CloseRes extends ServerMsg implements Serializable{
	public CloseRes(){
		
	}
	public CloseRes(String s){
		super(s);
		this.msg_type = MsgProtocol.DGT_CLOSE_RES_MSG;
	}
	
}
