package fr.unice.master1.sd.tp3;

public class GenClients {
	
	private int nbCLient;
	private Thread[] mesThreads;
	
	public GenClients(int nbClient) {
		this.nbCLient=nbClient;
	}
	
	private void createClient() {
		mesThreads = new Thread[nbCLient];
		for(int i=0;i<nbCLient;i++) {
			mesThreads[i]= new Thread(new Client(createName(5),true));
		}
	}
	
	private void launchClient() {
		for(int i=0;i<mesThreads.length;i++) {
			mesThreads[i].start();
		}
	}
	
	public void gotToConnectClients() {
		createClient();
		launchClient();
	}

	private String createName(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    String pass = "";
	    for(int x=0;x<length;x++)
	    {
	       int i = (int)Math.floor(Math.random() * 62);
	       pass += chars.charAt(i);
	    }
	    System.out.println(pass);
	    return pass;
	}
	
	
	public static void main(String[] args) {
		GenClients gen = new GenClients(5);
		gen.gotToConnectClients();
	}

}
