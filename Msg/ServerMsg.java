package Msg;
import java.io.Serializable;

public class ServerMsg extends Msg implements Serializable{
	private byte rtn_len;
	private char[] rtn_data;
	
	public ServerMsg(){
	}
	public ServerMsg(String s){
		this.rtn_data = s.toCharArray();
		this.rtn_len = (byte)(this.rtn_data.length & 0xff);
		this.msg_len = (byte)((this.msg_type + this.rtn_len + this.rtn_data.length) & 0xff);
	}

	public byte getRtnLen() {
		return this.rtn_len;
	}
	
	public char[] getRtnData() {
		return this.rtn_data;
	}
}
