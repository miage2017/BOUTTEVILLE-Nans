package fr.unice.master1.sd.tp3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Serveur_chat {

	private ServerSocket sSocket ;
	private boolean isRunning = true;
	private ArrayList<Thread> allClients;
	private ArrayList<ServiceClient_chat> clientsConnected;
	
	public Serveur_chat() {
		try {
			 this.allClients = new ArrayList();
			 this.sSocket = new ServerSocket(12000);
			 this.clientsConnected = new ArrayList();
			 System.out.println("Serveur en cours d'execution");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ServiceClient_chat> getClientsConnected(){
		return this.clientsConnected;
	}
	
	public void disconnectClient(ServiceClient_chat client,Thread t) {
		this.clientsConnected.remove(client);
		this.allClients.remove(t);
	}
	
	public void open() {
		Thread exit = new Thread(new Runnable() {

			@Override
			public void run() {
				Scanner sc = new Scanner(System.in);
				String src="";
				do {
					src = sc.nextLine();
				}while(!src.equals("exit"));
				if(isRunning==true) {
					try {
						System.out.println("stop all clients");
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
			
		});
		exit.start();
		while(isRunning == true){
            try {
               Socket client = sSocket.accept();
               ServiceClient_chat serv = new ServiceClient_chat(client,this);
               clientsConnected.add(serv);
               
               Thread t1 = new Thread(serv);
               allClients.add(t1);
               t1.start();
               
            } catch (IOException e) {
            }
         }
		
	}

	public static void main(String[] args) {
		Serveur_chat serv = new Serveur_chat();
		serv.open();
	}

}
