package fr.unice.master1.sd.tp2;

public class Objet_Entier {
	private int ma_valeur;
    public Objet_Entier()    {
	ma_valeur=0; 
	System.out.format("Valeur partagee initialisee a %d\n", ma_valeur); 
    }
    public synchronized String  toString(){
	return Integer.toString (ma_valeur);    }
    public int val(){ return ma_valeur;}
    public  synchronized   void add(int i) { ma_valeur+=i;} 

} 
