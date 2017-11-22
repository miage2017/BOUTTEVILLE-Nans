package fr.unice.master1.sd.tp3.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import fr.unice.master1.sd.tp3.ServiceClient_chat;

public class Client_server implements Runnable{
	private ServerSocket sSocket ;
	private boolean isRunning = true;
	private ArrayList<Thread> allClients;
	private ArrayList<Client_serviceClient> clientsConnected;
	private Client_serviceClient clientlocal;
	
	public Client_server(int port) {
		try {
			 this.allClients = new ArrayList();
			 this.sSocket = new ServerSocket(port);
			 this.clientsConnected = new ArrayList();
			 System.out.println("Votre server tourne. Il attend des nouveaux clients sur le port: "+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Client_serviceClient> getClientsConnected(){
		return this.clientsConnected;
	}
	
	public void sendMessageToClient(String message) {
		for(int i=0;i<this.clientsConnected.toArray().length;i++) {
			((Client_serviceClient)this.clientsConnected.toArray()[i]).toString();
		}
	}
	
	public void disconnectClient(Client_serviceClient client,Thread t) {
		this.clientsConnected.remove(client);
		this.allClients.remove(t);
	}
	
	public void disconnectServer() {
		if(isRunning==true) {
			try {
				System.out.println("stop all clients connected on me");
				do {
					for(int i=0;i<clientsConnected.toArray().length;i++) {
						((Thread) allClients.toArray()[i]).interrupt();
						((ServiceClient_chat) clientsConnected.toArray()[i]).disconnectThisClient();
					}
					Thread.currentThread().sleep(3000);
				}while(clientsConnected.size()>0);
				System.out.println("stop server");
				isRunning=false;
				try {
					sSocket.close();
				} catch (IOException e) {
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		while(isRunning == true){
            try {
               Socket client = sSocket.accept();
               Client_serviceClient serv = new Client_serviceClient(client,this);
               if(this.clientlocal!=null) {
	               clientsConnected.add(serv);
               }else {
            	   		this.clientlocal =serv;
               }
               Thread t1 = new Thread(serv);
               allClients.add(t1);
               t1.start();
               
            } catch (IOException e) {
            }
         }
	}
	
	
}
