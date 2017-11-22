package fr.unice.master1.sd.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ServiceClient implements Runnable {
	
	private Socket client;
	
	private PrintStream out;
	private InputStreamReader isr;
	private BufferedReader flux_entrant;
	
	public ServiceClient(Socket client) {
		this.client=client;
		try {
			this.isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			this.out = new PrintStream(client.getOutputStream()); 
			this.flux_entrant= new BufferedReader(isr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getNameClient() {
		String read;
		String returnValue=null;
		read = read();
		returnValue=  read.split("bonjour c'est le client ")[1];
		return returnValue;
	}
	
	public String read() {
		String read=null;
		try {
			read = this. flux_entrant.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return read;
	}
	
	public void ClientClose() {
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void run() {	
			String nameClient = getNameClient();
			
			out.println("Bonjour "+nameClient+"!");
			String read = read();
			Thread.currentThread();
			while(read!=null && !Thread.interrupted()) {
				System.out.println(nameClient+" écrit : "+read);
				flux_entrant = new BufferedReader(this.isr) ;
				if(read.equals("FINISH")) {
					break;
				}
				read = read();
				
			}
			
			System.out.println("le client s'est déconnecter");
			ClientClose();
	}

}
