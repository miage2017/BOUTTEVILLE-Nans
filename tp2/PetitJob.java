package fr.unice.master1.sd.tp2;


public class PetitJob implements Runnable
{
	private Objet_Entier notre_entier ;
    private int increment;
    PetitJob(Objet_Entier notre_entier , int increment){
	this.increment=increment; 
	this.notre_entier = notre_entier;
    }
    public void run() {
	for (int i = 0; i < 1e7; i++)
	    {
		notre_entier.add(increment);
	    }  
	System.out.format("Thread faisant %d - termine, compteur= %s\n" , increment,notre_entier);

    }
    public static void main(String[] args) {
	Objet_Entier Compteur = new Objet_Entier(); 
	PetitJob objex1= new PetitJob(Compteur, 1);
	PetitJob objex2= new PetitJob(Compteur, -1);
	Thread tache1 = new Thread(objex1 ,"incrementeur");
	Thread tache2 = new Thread(objex2, "decrementeur");

	tache1.start();
	tache2.start();
	
	System.out.format("Le compteur vaut %s\n" , Compteur);
	
	//for (int i = 0; i < 1e9; i++) {}
	try {
		tache1.join();
		tache2.join();
		System.out.format("Le compteur vaut %s\n" , Compteur);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
    
    }
}
