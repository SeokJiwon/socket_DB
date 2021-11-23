package Msg;

import java.io.Serializable;
import Protocol.MsgProtocol;

public class CloseReq extends Msg implements Serializable{
	public CloseReq(){
		this.msg_type = MsgProtocol.DGT_CLOSE_REQ_MSG;
		this.msg_len = (byte)(this.msg_type & 0xff);
	}
	public CloseReq(String s){
		this.msg_len = (byte)((s.length()+this.msg_type) & 0xff);
	}
}
