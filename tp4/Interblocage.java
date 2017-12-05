/**
 * 
 */
package fr.unice.master1.sd.tp4;

import java.util.ArrayList;

/**
 * @author nansboutteville
 *
 */
public class Interblocage {
	private ArrayList<Philosophe_InterBlocage> philosophes;
	private boolean possedeBoucle;
	
	public Interblocage(Philosophe_InterBlocage phi) {
		 this.philosophes = new ArrayList<Philosophe_InterBlocage>();
		 this.possedeBoucle=this.detecBoucle(phi);
		 this.philosophes = new ArrayList<Philosophe_InterBlocage>();
	}
	
	public void remettreAZero() {
		this.philosophes = new ArrayList<Philosophe_InterBlocage>();
	}
	
	public boolean getpossedeBoucle() {return this.possedeBoucle;}
	
	private boolean detecBoucle(Philosophe_InterBlocage phi) {
		philosophes.add(phi);
		boolean boucle=false;
		if(phi.getPhilosopheDroit()!=null) {
			if(this.philosophes.contains(phi.getPhilosopheDroit())) {
				boucle=true;
			}else {
				boucle = this.detecBoucle(phi.getPhilosopheDroit());
			}
		}
		if(phi.getPhilosopheGauche()!=null) {
			if(this.philosophes.contains(phi.getPhilosopheGauche())) {
				boucle=true;
			}else {
				boucle = this.detecBoucle(phi.getPhilosopheGauche());
			}
		}
		return boucle;
	}
	
	public boolean detectinterBlocage(Philosophe_InterBlocage phi) {
		boolean interBloc=false;
		boolean add =false;
		if(phi.getPhilosopheDroit()!=null) {
			if(this.philosophes.contains(phi.getPhilosopheDroit())) {
				interBloc=true;
			}else {
				if(phi.PhilopheDroitBolquer() && !add) {
					this.philosophes.add(phi);
					add=true;
				}
				interBloc = this.detecBoucle(phi.getPhilosopheDroit());
			}
		}
		if(phi.getPhilosopheGauche()!=null) {
			if(this.philosophes.contains(phi.getPhilosopheGauche())) {
				interBloc=true;
			}else {
				if(phi.PhilopheGaucheBolquer() && !add) {
					this.philosophes.add(phi);
					add=true;
				}
				interBloc = this.detecBoucle(phi.getPhilosopheGauche());
			}
		}
		return interBloc;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Philosophe_InterBlocage phi1 = new Philosophe_InterBlocage("1");
		Philosophe_InterBlocage phi2 = new Philosophe_InterBlocage("2");
		Philosophe_InterBlocage phi3 = new Philosophe_InterBlocage("3");
		Thread t1 = new Thread(phi1);
		Thread t2 = new Thread(phi2);
		Thread t3 = new Thread(phi3);
		
		phi2.setPhilosopheDroit(phi1,t1);
		phi2.setPhilosopheGauche(phi3,t2);
		phi1.setPhilosopheDroit(phi3,t3);
		phi1.setPhilosopheGauche(phi2,t2);
		phi3.setPhilosopheDroit(phi2,t2);
		phi3.setPhilosopheGauche(phi1,t1);
		
		Interblocage inter = new Interblocage(phi1);
		if(inter.getpossedeBoucle()) {
			System.out.println("Attention il y a une boucle, donc possibilité d'interBlocage");
		}
		t1.start();
		t2.start();
		t3.start();
		boolean blocage = false;
		System.out.println(blocage);
		while(!blocage) {
			inter.remettreAZero();
			blocage =inter.detectinterBlocage(phi1);
			inter.remettreAZero();
		}
		t1.interrupt();
		t2.interrupt();
		t3.interrupt();
		System.out.println("interblocage détecter");
		System.out.println("phi1 a manger "+phi1.getNbManger()+" phi2 a manger "+phi2.getNbManger()+" phi3 a manger "+phi3.getNbManger());
		
	}

}
