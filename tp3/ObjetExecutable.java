package fr.unice.master1.sd.tp3;

public class ObjetExecutable implements Runnable {
	 
	private volatile boolean done = false;
	 public void seterminer() {done = true;}

	@Override
	public synchronized void run() {
		int i=0;
		System.out.print("je commence avec : "+i);
		while (!done)
	     {
			i++;
			System.out.println("après incrément j'obtient :"+i);
	     }
	}

	public static void main(String[] args) throws InterruptedException {
		ObjetExecutable container = new ObjetExecutable();
		Thread myThread = new Thread(container);
		myThread.start();
		Thread.sleep(10000);
		container.seterminer();
	}

}
