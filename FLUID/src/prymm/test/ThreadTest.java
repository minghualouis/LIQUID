package prymm.test;

public class ThreadTest {

	public static void main(String[] args)
	{
		
			MyThread mythread = new MyThread();
			Thread mythread1 = new Thread(mythread);
			for (;;) {
				
				mythread1.start();
			
			}
		
		
		
	}
	
}
