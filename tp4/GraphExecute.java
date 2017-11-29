/**
 * 
 */
package fr.unice.master1.sd.tp4;

import java.util.ArrayList;

/**
 * @author nansboutteville
 *
 */
public class GraphExecute {

	private Sommet graphe;
	
	
	public GraphExecute(Sommet graphe) {
		this.graphe=graphe;
	}
	
	public void execute() {
		this.addTache(this.graphe);
	}
	
	private void execute(Thread t, Sommet s) {
		ArrayList<Sommet> getFils = s.getFils();
		for(int i =0;i<getFils.toArray().length;i++) {
			this.addTache(t, (Sommet)getFils.toArray()[i]);
		}
	}
	
	private void addTache(Sommet s) {
		Thread t= new Thread(new Runnable() {

			@Override
			public void run() {
				int a =  10* (int)  (Math.random()  * 1000 ); 
				System.out.println(s.getName()+" attend : "+a+"ms");
				try {
					Thread.currentThread().sleep(a);
					System.out.println(s.getName()+" a fini sa tache");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		t.start();
		this.execute(t,s);
	}
	
	private void addTache(Thread parent, Sommet s) {
		Thread t= new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("j'attend la fin de la tache parente");
					parent.join();
					int a =  10* (int)  (Math.random()  * 1000 ); 
					Thread.currentThread().sleep(a);
					System.out.println(s.getName()+" a fini sa tache");
					System.out.println(s.getName()+" attend : "+a+"ms");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		t.start();
		this.execute(t,s);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//création du graphe
		Sommet s1 = new Sommet("1");
		s1.addSommet(new Sommet("2"));
		s1.addSommet(new Sommet("3"));
		Sommet s4 = new Sommet("4");
		s1.addSommet(s4);
		s4.addSommet(new Sommet("4_1"));
		s4.addSommet(new Sommet("4_2"));
		
		//affichage du graphe
		//System.out.println(s1);
		
		//Création du graphExecute
		GraphExecute graph = new GraphExecute(s1);
		
		//execution du graph (Threads)
		graph.execute();
		
		
	}

}
