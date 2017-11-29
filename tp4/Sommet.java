/**
 * 
 */
package fr.unice.master1.sd.tp4;

import java.util.ArrayList;

/**
 * @author nansboutteville
 *
 */
public class Sommet {
	private String name;
	private ArrayList<Sommet> sommetsFils;
	
	public Sommet(String name) {
		this.name = name;
		this.sommetsFils =new ArrayList<Sommet>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Sommet> getFils(){
		return this.sommetsFils;
	}
	
	public void addSommet(Sommet s) {
		this.sommetsFils.add(s);
	}
	
	public String parcoursGraphe() {
		String returnFinal = name+" a pour fils : [\n\t";
		for(int i=0;i<this.sommetsFils.size();i++) {
			returnFinal+= this.sommetsFils.get(i).toString()+"\n\t";
		}
		returnFinal+="]";
		return returnFinal;
	}
	
	@Override
	public String toString() {
		return this.parcoursGraphe();	
	}
	
}
