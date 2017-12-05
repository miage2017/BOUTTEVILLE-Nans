/**
 * 
 */
package fr.unice.master1.sd.tp4;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nansboutteville
 *
 */
public class Philosophe_InterBlocage  extends ReentrantLock implements Runnable {
	private Philosophe_InterBlocage philosopheDroit;
	private Philosophe_InterBlocage philosopheGauche;
	private Thread droit;
	private Thread gauche;
	private String name;
	private int nbManger;
	private boolean PhilosopheDroitBloquer;
	private boolean PhilosopheGaucheBloquer;
	
	public Philosophe_InterBlocage(String name) {
		this.name=name;
		this.nbManger=0;
		this.philosopheDroit=null;
		this.philosopheGauche=null;
		this.PhilosopheDroitBloquer=false;
		this.PhilosopheGaucheBloquer=false;
	}
	
	public void setPhilosopheDroit(Philosophe_InterBlocage newPhilosophe,Thread droit) {
		this.philosopheDroit=newPhilosophe;
		this.droit=droit;
	}
	
	public void setPhilosopheGauche(Philosophe_InterBlocage newPhilosophe,Thread gauche) {
		this.philosopheGauche=newPhilosophe;
		this.gauche=gauche;
	}

	public Philosophe_InterBlocage getPhilosopheGauche() {return this.philosopheGauche;}
	
	public Philosophe_InterBlocage getPhilosopheDroit() {return this.philosopheDroit;}
	
	public boolean PhilopheDroitBolquer() {return this.PhilosopheDroitBloquer;}
	
	public boolean PhilopheGaucheBolquer() {return this.PhilosopheGaucheBloquer;}
	
	
	public int getNbManger() { return this.nbManger;}
	
	public String getName() {return this.name;}
	
	private void manger() {
		if(this.PhilosopheDroitBloquer && this.PhilosopheGaucheBloquer) {
			this.nbManger++;
			System.out.println("je suis "+this.name+" je mange et débloque mes voisins");
			this.PhilosopheDroitBloquer=false;
			this.PhilosopheGaucheBloquer=false;
			this.philosopheDroit.unlock();
			this.philosopheGauche.unlock();
		}
	}
	
	private void prendreBaguettes() throws InterruptedException {
		int a =  1 * (int)  (Math.random()  * 2 ); 
		if(a==0) {
			if(!this.PhilosopheDroitBloquer) {
				this.philosopheDroit.lock();
				this.PhilosopheDroitBloquer=true;
				System.out.println("je suis "+this.name+" bolque mon voisin de droite "+this.philosopheDroit.getName());
			}
		}else {
			if(!this.PhilosopheGaucheBloquer) {
				this.philosopheGauche.lock();
				this.PhilosopheGaucheBloquer=true;
				System.out.println("je suis "+this.name+" bolque mon voisin de gauche "+this.philosopheGauche.getName());
			}
		}
	}
	
	@Override
	public void run() {
		Thread.currentThread();
		while(!Thread.interrupted()) {
			try {
				this.prendreBaguettes();
				this.manger();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
}
