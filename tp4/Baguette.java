package fr.unice.master1.sd.tp4;

import java.util.concurrent.locks.ReentrantLock;

public class Baguette extends ReentrantLock{
	private Philosophe philosophe;
	private PhilosopheMalin philosophemalin;
	public synchronized boolean prendre(Philosophe philo) {
		if(this.philosophe!=null || this.philosophemalin!=null) {
			return false;
		}else {
			this.philosophe=philo;
			return true;
			
		}
	}
	
	public synchronized boolean prendre(PhilosopheMalin philo) {
		if(this.philosophe!=null || this.philosophemalin!=null) {
			return false;
		}else {
			this.philosophemalin=philo;
			return true;
			
		}
	}
	
	public synchronized boolean poser() {
		if(this.philosophe!=null || this.philosophemalin!=null) {
			this.philosophe=null;
			this.philosophemalin=null;
			return true;
		}else {
			return false;
		}
	}
	
	public synchronized Philosophe getPhilosophe() {
		return this.philosophe;
	}
	
	public synchronized PhilosopheMalin getPhilosopheMalin() {
		return this.philosophemalin;
	}
}
