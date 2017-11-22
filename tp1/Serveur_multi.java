package fr.unice.master1.sd.tp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.unice.master1.xml.tp5.org.inria.ns.tp.jaxb.zoo.Tp5;

public class Serveur_multi {
	private ServerSocket sSocket ;
	private boolean isRunning = true;
	
	public Serveur_multi() {
		try {
			 this.sSocket = new ServerSocket(12000);
			 System.out.println("Serveur en cours d'execution");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void open() {
		 Thread t = new Thread(new Runnable(){
	         public void run(){
	            while(isRunning == true){
	               
	               try {
	                  //On attend une connexion d'un client
	            	   	System.out.println("Attente d'un client");
	                  Socket client = sSocket.accept();
	                  
	                  //Une fois reçue, on la traite dans un thread séparé
	                  System.out.println("Connexion cliente reçue.");                  
	                  Thread t1 = new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Service_Client(client);
						}
	                	  
	                  });
	                  t1.start();
	                  
	               } catch (IOException e) {
	                  e.printStackTrace();
	               }
	            }
	            
	          
	         }
	      });
	      
	      t.start();

	}
	
	public void Service_Client(Socket client) {
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(client.getInputStream(), "UTF-8");
			BufferedReader flux_entrant = new BufferedReader(isr) ;
			PrintStream out = new PrintStream(client.getOutputStream()); 
			int i=1;
			String read = flux_entrant.readLine();
			String nameClient = read.split("bonjour c'est le client ")[1];
			out.println("Bonjour "+nameClient+"!");
			while(read!=null) {
				System.out.println("le client numéro "+nameClient+" écrit : "+read);
				flux_entrant = new BufferedReader(isr) ;
				if(read.equals("FINISH")) {
					break;
				}
				read = flux_entrant.readLine();
				i++;
				
			}
			System.out.println("le client s'est déconnecter");
			client.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Serveur_multi serv = new Serveur_multi();
		serv.open();
	}
	
	
	
	
}
