import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Msg.AuthReq;
import Msg.AuthRes;
import Msg.CloseRes;
import Msg.ServerMsg;
import Msg.SqlReq;
import Msg.SqlRes;
import Protocol.MsgProtocol;
import Protocol.SqlProtocol;

class ServerReceiverThread extends Thread{
	Socket socket = null;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	
	ServerReceiverThread(Socket socket){
		this.socket = socket;
	}
	
	public void run() {
		try {
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

			try {
				AuthReq c_msg1;

				do {
					c_msg1 = (AuthReq)objectInputStream.readObject();
					
					ServerMsg s_msg1;
					String userId = String.valueOf(c_msg1.getId());
					String userPwd = String.valueOf(c_msg1.getPwd());
					if(!(userId.equals("scott"))) {
						s_msg1 = new AuthRes("user_id invalid.");
						objectOutputStream.writeObject(s_msg1);
						objectOutputStream.flush();
						System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+c_msg1.msg_type + ":user_id invalid.");
						continue;
					}
					if(!(userPwd.equals("tiger"))) {
						s_msg1 = new AuthRes("password incorrect.");
						objectOutputStream.writeObject(s_msg1);
						objectOutputStream.flush();
						System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+c_msg1.msg_type + ":password incorrect.");
						continue;
					}
					if(userId.equals("scott") && userPwd.equals("tiger")) {
						s_msg1 = new AuthRes("===========================\nConnection is Successful.\n Server IP: "+MultiServer.ip+"\nServer Port: "+Integer.toString(MultiServer.port)+"\n===========================");
						objectOutputStream.writeObject(s_msg1);
						objectOutputStream.flush();
						System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"]"+c_msg1.msg_type + ":Valid User");
						break;
					}
				}while(c_msg1.msg_type==MsgProtocol.DGT_AUTH_REQ_MSG);
				
			}catch(ClassNotFoundException e) {
				e.getStackTrace();
			}
			
			try {
				SqlReq c_msg2;
				
				 do{
					c_msg2 = (SqlReq)objectInputStream.readObject();
					System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"] MsgType: "+c_msg2.msg_type+" SqlType: "+c_msg2.getSqlType());
					ServerMsg s_msg2;
					
					if(c_msg2.getSqlType()==SqlProtocol.SELECT) s_msg2 = new SqlRes("Result : select sql is successful.");
					else if(c_msg2.getSqlType()==SqlProtocol.UPDATE) s_msg2 = new SqlRes("Result : update sql is successful.");
					else if(c_msg2.getSqlType()==SqlProtocol.DELETE) s_msg2 = new SqlRes("Result : delete sql is successful.");
					else s_msg2 = new SqlRes("Result : unsupported sql is executed.");
						
					objectOutputStream.writeObject(s_msg2);
					objectOutputStream.flush();
				}while(c_msg2.msg_type==MsgProtocol.DGT_SQL_REQ_MSG);
				 
			}catch(ClassNotFoundException e) {
				e.getStackTrace();
			}catch(Exception e) {
				e.getStackTrace();
			}

			ServerMsg s_msg3 = new CloseRes("["+socket.getInetAddress()+":"+socket.getPort()+"]"+"Server connection is closed.");
			objectOutputStream.writeObject(s_msg3);
			objectOutputStream.flush();

		}catch(IOException e) {
			e.getStackTrace();
		}finally {
			System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"] connection end");
		}
	}
}