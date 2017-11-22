package fr.unice.master1.sd.tp3.p2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import fr.unice.master1.sd.tp2.Dormeur;

public class Client {
	private Socket socketLocal;
	private Socket socketDistant;
	private Client_server myserver;
	private String name;
	
	private BufferedReader in ;
	private PrintStream out;
	private Scanner sc;
	
	private boolean  isConnected;
	
	//Constructor
	public Client(String name) {
		this.name = name;
		this.isConnected=false;
		this.myserver = new Client_server(12000);
		this.socketLocal=this.connexionServer("127.0.0.1");
	}
	
	//connexion à un serveur
	private Socket connexionServer(String ip) {
		Socket retour = null;
		try { 
		InetAddress serveur = InetAddress.getByName(ip); 
		retour =  new Socket(serveur, 12000); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		}
		return retour;
	}
	
	//récupéré les message du server si on est connecté à un.
	private String getMessageServer(Socket socket) {
		String read="";
		try {
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(this.isConnected) {
				read = this. in.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return read;
	}
	
	//Thread des messsages reçus du server si on est connecté à un. ou de notre server actuel
	private void read() {
		Thread readServerDistant = new Thread(new Runnable() {

			@Override
			public void run() {
				String read1="";
				do {
					if(isConnected) {
						read1 = getMessageServer(socketDistant);
						if(!read1.equals("FINISH") && !read1.equals("EXIT")) {
							System.out.println(read1);
						}else {
							System.out.println("vous avez été déconnecté du serveur");
						}
					}
				}while(!read1.equals(" ") && !read1.equals("FINISH") && !read1.equals(null) && !read1.equals("EXIT") &&equals(isConnected));
				if(read1.equals("EXIT")) {
					try {
						out = new PrintStream(socketDistant.getOutputStream());
						out.println("exit");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				isConnected=false;
				try {
					socketDistant.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
			
		});
		readServerDistant.start();
		
		Thread readServerLocal= new Thread(new Runnable() {

			@Override
			public void run() {
				String read1="";
				do {
						read1 = getMessageServer(socketLocal);
						if(!read1.equals("FINISH") && !read1.equals("EXIT")) {
							System.out.println(read1);
						}else {
							System.out.println("vous avez été déconnecté du serveur");
						}
				}while(!read1.equals(" ") && !read1.equals("FINISH") && !read1.equals(null) && !read1.equals("EXIT"));
				if(read1.equals("EXIT")) {
					try {
						out = new PrintStream(socketLocal.getOutputStream());
						out.println("exit");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				try {
					socketLocal.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
			
		});
		readServerLocal.start();
	}
	

	public void run() {	
		
	}
	
	public static void main(String[] args) {
		if (args.length != 1)
		{
			System.out.format("Ouch ! utilisation: java %s   name \nCe programme Utilise un argument que vous devez fournir\n",
				 Client.class.getCanonicalName());
			System.exit( - 1);
		}
		
		Client t = new Client(args[0]);
		t.run();
	}

}

