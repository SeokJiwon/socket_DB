package Msg;

import java.io.Serializable;
import Protocol.*;

public class SqlReq extends Msg implements Serializable{
	private byte sql_type;
	private byte sql_len;
	private char[] sql_text;
	
	public SqlReq(){
		this.msg_type = MsgProtocol.DGT_SQL_REQ_MSG;
	}
	
	public SqlReq(int sql_type, String s){
		this.msg_type = MsgProtocol.DGT_SQL_REQ_MSG;
		this.sql_type = (byte)sql_type;
		this.sql_len = (byte)(s.length() & 0xff);
		this.sql_text = s.toCharArray();
		this.msg_len = (byte)((this.msg_type + this.sql_type + this.sql_len + this.sql_text.length) & 0xff);		
	}
	
	public byte getSqlType() {
		return this.sql_type;
	}
	public byte getSqlLen() {
		return this.sql_len;
	}
	public char[] getSqlText() {
		return this.sql_text;
	}
}
