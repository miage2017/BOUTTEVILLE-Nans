package fr.unice.master1.sd.tp3;

public class Consommateur extends Thread{
	Producteur producer;
	 
	Consommateur(Producteur p) {
        producer = p;
    }
 
    public void run() {
        try {
            while (true) {
                String message = producer.getJobs();
                System.out.println("Got message: " + message);
                //sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
