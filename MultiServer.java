import java.net.*;
import java.util.*;
import java.io.*;
import Protocol.*;
import Msg.*;

public class MultiServer {
	HashMap clients;
	static String ip="";
	static int port = 9522;
	
	MultiServer(){
		clients = new HashMap();
		Collections.synchronizedMap(clients);
	}
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(9522);
			System.out.println("server start");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println("["+socket.getInetAddress()+":"+socket.getPort()+"] connection start");
				ServerReceiverThread thread = new ServerReceiverThread(socket);
				thread.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try{
			InetAddress local = InetAddress.getLocalHost();
			ip = local.getHostAddress();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}finally {
			new MultiServer().start();
		}
	}
}