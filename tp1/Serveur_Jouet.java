package fr.unice.master1.sd.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.unice.master1.xml.tp5.org.inria.ns.tp.jaxb.zoo.Tp5;

public class Serveur_Jouet {
	Socket client ;
	
	public Serveur_Jouet() {
		try {
			ServerSocket sSocket = new ServerSocket(12000);
			System.out.println("Serveur en cours d'execution...");
			System.out.println("Attente du client....");
			client = sSocket.accept();
			System.out.println("Client accepté.");
			
			InputStreamReader isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader flux_entrant = new BufferedReader(isr) ;
			int i=1;
			String read = flux_entrant.readLine();
			System.out.println("le client écrit :");
			while(read!=null) {
				System.out.println("ligne "+i+": "+read);
				flux_entrant = new BufferedReader(isr) ;
				if(read.equals("FINISH")) {
					break;
				}
				read = flux_entrant.readLine();
				i++;
			}
			client.close();
			System.out.println("le client s'est déconnecter");
			System.out.println("déconnexion du serveur");
			sSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Serveur_Jouet serv = new Serveur_Jouet();
	}
	
	
}
