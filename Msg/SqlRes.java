package Msg;

import java.io.Serializable;
import Protocol.*;

public class SqlRes extends ServerMsg implements Serializable{
	public SqlRes(){
		
	}
	
	public SqlRes(String s){
		super(s);
		this.msg_type = MsgProtocol.DGT_SQL_RES_MSG;
	}
}
