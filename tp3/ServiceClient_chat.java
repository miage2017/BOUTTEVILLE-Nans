package fr.unice.master1.sd.tp3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;

public class ServiceClient_chat implements Runnable {
	
	private Socket client;
	private Serveur_chat serveur;
	private String name;
	
	
	private PrintStream out;
	private InputStreamReader isr;
	private BufferedReader flux_entrant;
	
	public ServiceClient_chat(Socket client,Serveur_chat serv) {
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
	
	private void SendMessageToOtherClient(String message) {
		String messageFinal=name+" : "+message;
		ArrayList<ServiceClient_chat> allClients = this.serveur.getClientsConnected();
		for(int i=0;i<allClients.size();i++) {
			if(!((ServiceClient_chat) allClients.toArray()[i]).getName().equals(this.name)) {
				((ServiceClient_chat) allClients.toArray()[i]).sendMessageToClient(messageFinal);
			}
		}
		System.out.println(messageFinal);
	}
	
	public void disconnectThisClient() {
		out.println("le serveur va s'areter, vous aller être déconnecter");
		out.println("EXIT");
		this.serveur.disconnectClient(this, Thread.currentThread());
	}
	
	public void sendMessageToClient(String message) {
		out.println(message);
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