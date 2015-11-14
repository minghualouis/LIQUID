package prymm.test;

public class MultiThreadTest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new MultiThreadTest2());
		thread.start();
	}

}
