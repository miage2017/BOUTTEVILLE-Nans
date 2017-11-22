package fr.unice.master1.sd.tp2;

public class Exercice_1 {


	public static void main(String[] args) {
		Exercice_1 ex = new Exercice_1();
		ex.executionTHreadCalculator();
	}
	
	
	public void executionTHreadCalculator() {
		 Thread TA = new Thread(new Runnable(){

			@Override
			public void run() {
				for(int i=0;i<1000;i++) {
					System.out.println("TA count : "+i);
				}
				
			}
			 
		 });
		 
		 Thread TB = new Thread(new Runnable(){

			 public void run() {
					for(int i=1000;i>0;i--) {
						System.out.println("TB decount : "+i);
					}
				}
		});
		 TA.start();
		 TB.start();
		 
	}

}
