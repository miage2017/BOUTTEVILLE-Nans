package fr.unice.master1.sd.tp2;

public class Dormeur implements Runnable {
	
	private String nom = "";
	private Thread previous = null;
	
	public Dormeur(String name, Thread prec) {
		this.nom=name;
		this.previous=prec;
	}

	public Dormeur(String name) {
		this.nom=name;
	}
	public void set_pred(Thread t) {
		this.previous=t;
	}
	
	
	@Override
	public void run() {
		try
	    {
			if(previous!=null) {
				previous.join();
			}
			int a =  5 * (int)  (Math.random()  * 1000 ); 
			Thread.sleep(a);
			System.out.format("Je suis [%s] j'ai fini!\n", nom);
		    
	    }
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
			//Exercice 6
			/*Dormeur C = new Dormeur("C");
			Thread t3 = new Thread(C);
			
			Dormeur B = new Dormeur("B",t3);
			Thread t2 = new Thread(B);
			
			Dormeur A = new Dormeur("A",t2);
			Thread t1 = new Thread(A);
			t1.start();
			t2.start();
			t3.start();*/
			
			//Exercice 6 plus
			if (args.length != 1)
			{
				System.out.format("Ouch ! utilisation: java %s   nbthreads \nCe programme Utilise un argument que vous devez fournir\n",
					 Dormeur.class.getCanonicalName());
				System.exit( - 1);
			}
			
			int nbThreads = Integer.parseInt(args[0]);
			Thread[] mesDormeurs = new Thread[nbThreads];
			
			for (int t = nbThreads; t > 0; t--)
			{
				String name = "T"+t;
				Dormeur d;
				if(t==nbThreads) {
					 d = new Dormeur(name);
				}else {
					d = new Dormeur(name, mesDormeurs[t]);
				}
				mesDormeurs[t-1]=new Thread(d);
				mesDormeurs[t-1].start();
			}
			
			
			
	}

}
