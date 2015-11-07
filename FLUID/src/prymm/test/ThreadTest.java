package prymm.test;

public class ThreadTest {

	public static void main(String[] args)
	{
		
			MyThread mythread = new MyThread();
			Thread mythread1 = new Thread(mythread);

			mythread1.start();
		
			try {
				Thread.sleep(5000);
				if (mythread1.isAlive()) {
					mythread1.suspend();
				}
				System.out.println("now waiting for 5 seconds....then continue");
				Thread.sleep(5000);
				mythread1.resume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
}
