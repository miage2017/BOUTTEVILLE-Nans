package fr.unice.master1.sd.tp3.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;



public class Client_serviceClient implements Runnable{
	
	private Socket client;
	private Client_server serveur;
	private String name;
	
	
	private PrintStream out;
	private InputStreamReader isr;
	private BufferedReader flux_entrant;
	
	public Client_serviceClient(Socket client,Client_server serv) {
		this.client=client;
		this.serveur=serv;
		try {
			this.isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			this.out = new PrintStream(client.getOutputStream()); 
			this.flux_entrant= new BufferedReader(isr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public void getNameClient() {
		String read;
		String returnValue=null;
		read = read();
		returnValue=  read.split("bonjour c'est le client ")[1];
		this.name=returnValue;
	}
	
	public String read() {
		String read=null;
		try {
			read = this. flux_entrant.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}
	
	public void ClientClose() {
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessageToClient(String message) {
		out.println(message);
	}
	
	public void SendMessageToOtherClient(String message) {
		this.serveur.sendMessageToClient(this.name+": "+message);
	}
	
	@Override
	public void run() {
		getNameClient();
		SendMessageToOtherClient("Je viens de me connecter");
		out.println("Bonjour "+this.name+"!");
		String read = read();
		Thread.currentThread();
		flux_entrant = new BufferedReader(this.isr) ;
		while(read!=null && !Thread.interrupted()) {
			this.SendMessageToOtherClient(read);
			if(read.equals("FINISH")) {
				out.println("FINISH");
				break;
			}
			if(read.equals("exit")) {
				break;
			}
			read = read();
			
		}
		if(Thread.interrupted()) {
			out.println("FINISH");
		}else {
			this.serveur.disconnectClient(this, Thread.currentThread());
		}
		SendMessageToOtherClient("je me suis deconnecter");
		ClientClose();
		
	}

}
