package fr.unice.master1.sd.tp3;

import java.util.Vector;

public class Producteur extends Thread{
	static final int MAXQUEUE = 5;
    private Vector nosJobs = new Vector();
	
	
	public Producteur(int identifiant) {
	}
	
	public void prod() throws InterruptedException {
		while (nosJobs.size() == MAXQUEUE) {
            wait();
        }
		nosJobs.addElement(new java.util.Date().toString());
        System.out.println("put message");
        notify();
	}
	
	@Override
	public void run() {
		try {
            while (true) {
            	prod();
                //sleep(5000);
            }
        } catch (InterruptedException e) {
        }
	}
	
	public synchronized String getJobs() throws InterruptedException {
        notify();
        while (nosJobs.size() == 0) {
            wait();//By executing wait() from a synchronized block, a thread gives up its hold on the lock and goes to sleep.
        }
        String message = (String) nosJobs.firstElement();
        nosJobs.removeElement(message);
        return message;
    }
	
	public static void main(String[] args) {
		Producteur producer = new Producteur(1);
        producer.start();
        new Consommateur(producer).start();

	}

}
