package Utility;

import java.util.Observable;

public class MasterThread extends Observable implements Runnable {

	private static MasterThread instance;
	private static Thread t;

	public static MasterThread getInstance(){
		if(instance!=null)
			return instance;
		else{
			instance = new MasterThread();
			return instance;
		}
	}
	
	protected MasterThread() {
		 t = new Thread(this);
	}

	@SuppressWarnings("deprecation")
	public static void stopThread() {
		t.stop();
		System.out.println("[MasterThread] MasterThread has stopped");
	}

	public static void startThread() {
		t.start();
		System.out.println("[MasterThread] MasterThread has started");
	}

	private void ichanged() {
		System.out.println("[MasterThread] ichanged called");
		setChanged();
		notifyObservers();
	}

	@Override
	public void run() {
		boolean thread = true;
		while(thread){
			try {
				ichanged();
				System.out.println("[MasterThread] thread run called");
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				thread = false;
			}
		}
	}
}
