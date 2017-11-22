package fr.unice.master1.sd.tp4;

public class Philosophe implements Runnable{
	private String name;
	private Baguette droite;
	private Baguette gauche;
	private int nbManger;
	private boolean baguetteDroite;
	private boolean baguetteGauche;
	
	public Philosophe(String name, Baguette droite, Baguette gauche) {
		this.name=name;
		this.droite=droite;
		this.gauche=gauche;
		this.nbManger=0;
		this.baguetteDroite=false;
		this.baguetteGauche=false;
	}
	
	public int getNbManger() { return this.nbManger;}
	public int NbBaguetteDetenu() {

		int returnFinal=0;
		if(this.baguetteDroite) {
			returnFinal++;
		}
		if(this.baguetteGauche) {
			returnFinal++;
		}
		return returnFinal;
	}
	public String getName() {return this.name;}
	public boolean DetienBaguetteDroite() {return this.baguetteDroite;}
	public boolean DetienBaguetteGauche() {return this.baguetteGauche;}
	
	private void manger() {
		if(baguetteDroite && baguetteGauche) {
			nbManger++;
			this.droite.poser();
			this.baguetteDroite=false;
			this.gauche.poser();
			this.baguetteGauche=false;
			//this.attendre();
		}
	}
	
	private void attendre() {
		Thread.currentThread();
		try {
			int a =  1 * (int)  (Math.random()  * 100 ); 
			Thread.sleep(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void prendreBaguettes() {
		int a =  1 * (int)  (Math.random()  * 2 ); 
		if(a==0) {
			if(!this.baguetteDroite) {
				this.baguetteDroite = this.droite.prendre(this);
			}
		}else {
			if(!this.baguetteGauche) {
				this.baguetteGauche= this.gauche.prendre(this);
			}
		}
		
		//this.attendre();
		
	}
	
	@Override
	public void run() {
		Thread.currentThread();
		while(!Thread.interrupted()) {
			//this.attendre();
			this.prendreBaguettes();
			//this.attendre();
			this.manger();
		}
	}

	
}
