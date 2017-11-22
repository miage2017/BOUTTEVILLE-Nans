package fr.unice.master1.sd.tp3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Repartiteur {

	private ServerSocket sSocket ;
	private boolean isRunning = true;
	private Thread[] allClients;
	private int lengthClients;
	private static int maxClientsAccepted=5;
	
	public Repartiteur() {
		try {
			 this.allClients = new Thread[Repartiteur.maxClientsAccepted];
			 this.sSocket = new ServerSocket(12000);
			 this.lengthClients = 0;
			 System.out.println("Serveur en cours d'execution");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void open() {
		while(isRunning == true && this.lengthClients<Repartiteur.maxClientsAccepted){
            
            try {
         	   System.out.println("Attente d'un client");
               Socket client = sSocket.accept();
               
               System.out.println("Connexion cliente reçue.");                  
               Thread t1 = new Thread(new ServiceClient(client));
               this.allClients[this.lengthClients]=t1;
               this.lengthClients++;
               System.out.println("nb de client connecter : "+this.lengthClients);
               t1.start();
               
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
		if(isRunning==true) {
			try {
				Thread.sleep(10000);
				System.out.println("j'arete tout les clients");
				for(int i=0;i<this.allClients.length;i++) {
					allClients[i].interrupt();
				}
				System.out.println("j'arete le serveur");
				isRunning=false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Repartiteur serv = new Repartiteur();
		serv.open();
	}

}
