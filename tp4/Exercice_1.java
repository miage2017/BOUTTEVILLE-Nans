package fr.unice.master1.sd.tp4;

import java.util.ArrayList;

public class Exercice_1 {
	public static int nbPhilosophe = 3;
	
	
	public static void main(String[] args) {
		ArrayList<Philosophe> philosophes = new ArrayList();
		ArrayList<Baguette> baguettes = new ArrayList();
		ArrayList<Thread> alLThread = new ArrayList();
		
		for(int i=0;i<Exercice_1.nbPhilosophe;i++) {
			baguettes.add(new Baguette());
		}
		int j=0;
		for(int i=0;i<Exercice_1.nbPhilosophe;i++) {
			
			if(j+1<baguettes.toArray().length) {
				philosophes.add(new Philosophe("philo"+i,(Baguette) baguettes.toArray()[j],(Baguette) baguettes.toArray()[j+1]));
				if(j+2!=baguettes.toArray().length) {
					j+=2;
				}else {
					j++;
				}
			}else {
				philosophes.add(new Philosophe("philo"+i,(Baguette) baguettes.toArray()[j],(Baguette) baguettes.toArray()[0]));
			}
			
		}
		Thread t;
		for(int i=0;i<philosophes.toArray().length;i++) {
			t= new Thread((Philosophe) philosophes.toArray()[i]);
			alLThread.add(t);
			t.start();
		}
		/*Thread observeur = new Thread(new Observateur(baguettes));
		observeur.start();*/
		
		t=new Thread(new Runnable() {

			@Override
			public void run() {
				int a =  10* (int)  (Math.random()  * 1000 ); 
				System.out.println("j'attend :"+a+"ms");
				try {
					Thread.currentThread().sleep(a);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		t.start();
		try {
			t.join();
			//observeur.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<alLThread.size();i++) {
			((Thread) alLThread.toArray()[i]).interrupt();
		}
		for(int i=0;i<philosophes.toArray().length;i++) {
			Philosophe p = (Philosophe) philosophes.toArray()[i];
			System.out.println("je suis "+p.getName()+" j'ai mangé "+p.getNbManger()+" j'ai "+p.NbBaguetteDetenu()+" baguettes en ma possession");
		}
		
	}

}
