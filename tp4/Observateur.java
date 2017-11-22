/**
 * 
 */
package fr.unice.master1.sd.tp4;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nansboutteville
 *
 */
public class Observateur implements Runnable {
	private ArrayList<Baguette> baguettes;
	
	public Observateur(ArrayList<Baguette> baguettes) {
		this.baguettes=baguettes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			for(int i=0;i<this.baguettes.toArray().length;i++) {
				((Baguette) this.baguettes.toArray()[i]).lock();
				Philosophe p = ((Baguette) this.baguettes.toArray()[i]).getPhilosophe();
				if(p != null) {
					System.out.println("Je suis la baguette "+i+" et je suis pris par le Philosophe "+p.getName());
				}else {
					System.out.println("Je suis la baguette "+i+" et je suis par personne");
				}
			}
			try {
				Thread.currentThread().sleep(1000);
				for(int i=0;i<this.baguettes.toArray().length;i++) {
					((Baguette)this.baguettes.toArray()[i]).unlock();
				}
				Thread.currentThread().sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}
