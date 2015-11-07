package prymm.test;

public class MyThread implements Runnable {

	public static int i = 0;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(;;){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("running" + i);
		i++;
		}
	}

}
