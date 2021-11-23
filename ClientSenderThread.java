import java.net.*;
import java.util.*;
import java.io.*;
import Msg.*;
import Protocol.SqlProtocol;

public class ClientSenderThread extends Thread{
	Socket socket = null;
	ObjectInputStream objectInputStream;
	ObjectOutputStream objectOutputStream;
	static boolean idCheck = false;
	
	ClientSenderThread(Socket socket){
		this.socket = socket;
	}
	public static void checkID(boolean check) {
		idCheck = check;
	}
	public void run() {
		String sql = "";
		String id = "";
		String password = "";
		try {
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());		
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			Scanner in = new Scanner(System.in);
			AuthReq authreq;
			while(!idCheck) {
				System.out.print("ID : ");
				id = in.next();
				System.out.print("Password : ");
				password = in.next();
				
				authreq = new AuthReq(id, password);
				
				objectOutputStream.writeObject(authreq);
				objectOutputStream.flush();
				
				ServerMsg m = (ServerMsg)objectInputStream.readObject();
				System.out.println(String.valueOf(m.getRtnData()));
				if (m.getRtnLen() > 20) idCheck=true;
			}
			
			in.nextLine();
			while(true) {
				System.out.print("SQL > ");
				sql = in.nextLine();
				
				String split[] = sql.split(" ");
				String type = split[0];
				if(sql.equals("exit;")) break;
				if(type.equalsIgnoreCase("select")) {
					SqlReq sqlreq = new SqlReq(SqlProtocol.SELECT, sql);
					objectOutputStream.writeObject(sqlreq);
					objectOutputStream.flush();
				}
				else if(type.equalsIgnoreCase("update")) {
					SqlReq sqlreq = new SqlReq(SqlProtocol.UPDATE, sql);
					objectOutputStream.writeObject(sqlreq);
					objectOutputStream.flush();
				}
				else if(type.equalsIgnoreCase("delete")) {
					SqlReq sqlreq = new SqlReq(SqlProtocol.DELETE, sql);
					objectOutputStream.writeObject(sqlreq);
					objectOutputStream.flush();
				}
				else {
					SqlReq sqlreq = new SqlReq(SqlProtocol.UNKNOWN, sql);
					objectOutputStream.writeObject(sqlreq);
					objectOutputStream.flush();
				}
				ServerMsg m = (ServerMsg)objectInputStream.readObject();
				m = (SqlRes)m;
				System.out.println(String.valueOf(m.getRtnData()));
			}
			
			CloseReq c_msg3 = new CloseReq();
			objectOutputStream.writeObject(c_msg3);
			objectOutputStream.flush();
			
			System.out.println("Server connection is closed.");
			
			socket.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}