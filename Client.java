import java.net.*;
import java.util.*;
import java.io.*;
import Msg.*;
import Protocol.*;

public class Client {

	public static void main(String[] args) {
		
		Socket socket = null;
		
		try {
			socket = new Socket("127.0.0.1", 9522);
			
			Thread thread1 = new ClientSenderThread(socket);
			//Thread thread2 = new ReceiverThread(socket);
			thread1.start();
			//thread2.start();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}