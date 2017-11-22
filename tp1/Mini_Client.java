package fr.unice.master1.sd.tp1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Mini_Client {
	private String name;
	
	public Mini_Client(String name) {
		this.name=name;
	}
	
	public void connexionServer() {
		Socket socket; 
		DataInputStream userInput; 
		PrintStream theOutputStream; 
		try { 
		InetAddress serveur = InetAddress.getByName("127.0.0.1"); 
		socket = new Socket(serveur, 12000); 
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		PrintStream out = new PrintStream(socket.getOutputStream()); 
		out.println("bonjour c'est le client " + name); 
		System.out.println(in.readLine());
		Scanner sc = new Scanner(System.in);
		String str;
		do {
			System.out.println("Veuillez saisir une phrase :");
			str = sc.nextLine();
	
			out.println(str);
		}while(!(str.equals("FINISH")));
		} catch (Exception e) { 
		e.printStackTrace(); 
		}
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Mini_Client cl = new Mini_Client("lysan");
		cl.connexionServer();
	}

}
