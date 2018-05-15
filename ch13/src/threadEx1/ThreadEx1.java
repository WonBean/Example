package threadEx1;

public class ThreadEx1 {

	public static void main(String[] args) {
		ThreadEx1_1 t1 = new ThreadEx1_1();
		
		Runnable r = new ThreadEx1_2();
		Thread t2 = new Thread(r);
		
		t1.start();
		t2.start();

	}
	
	

}


class ThreadEx1_1 extends Thread{
	public void run() {
		for(int i =0; i < 100; i++) {
			System.out.println(getName());
		}
	}
}

class ThreadEx1_2 implements Runnable{
	public void run() {
		for(int i =0; i < 100; i++) {
			//Thread.crrentThread() - 현재 실행중인 쓰레드를 반환한다.
			System.out.println(Thread.currentThread().getName());
		}
	}
}
