import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SyncLoopTest implements Runnable{

	public static void main(String[] args) {
		Runnable task = new SyncLoopTest();
		
		Thread t=new Thread(task);
        Thread t1=new Thread(task);

        t.start();
        t1.start();
	}
	@Override
	   public void run() {
		//System.out.println("Thread " + Thread.currentThread().getName() + " is waiting for execution");
		forTest(1, 2);
	    //System.out.println("Thread " + Thread.currentThread().getName() + " has executed synced method");
		
	   }
	
	public synchronized void forTest(int start, int end){
		List<Integer> list = new ArrayList<Integer>();
		
		List<Integer> list_1 = new ArrayList<Integer>();
		for(int i=start;i<end;i++){
			list_1.add(i);
		}
		list.addAll(list_1);
		
		List<Integer> list_2 = new ArrayList<Integer>();
		list_2.add(1001);
		list_2.add(1002);
		list.addAll(list_2);
	
		//for(int i=0;i<list.size();i++){
		Iterator<Integer> iter = list.iterator();
		while(iter.hasNext()){
			//int no = list.get(i);
			int no = iter.next();
			if(10 == Thread.currentThread().getId()){
				System.out.println("##"+Thread.currentThread().getName()+" start!");
			}else{
				System.err.println("##"+Thread.currentThread().getName()+" start!");
			}
			try {
				if(10 == Thread.currentThread().getId()){
					System.out.println("Thread:"+Thread.currentThread().getName()+"==>"+no);
				}else{
					System.err.println("Thread:"+Thread.currentThread().getName()+"==>"+no);
				}
				
                Thread.sleep((int)(Math.random() * 800));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			if(10 == Thread.currentThread().getId()){
				System.out.println("##"+Thread.currentThread().getName()+" end!");
			}else{
				System.err.println("##"+Thread.currentThread().getName()+" end!");
			}
			System.out.println();
		}
	}
}
