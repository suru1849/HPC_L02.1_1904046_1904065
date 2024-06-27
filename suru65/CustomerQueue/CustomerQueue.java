package suru65.CustomerQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerQueue {
   // Customers queue
   public  Queue<Integer> queue = new LinkedList<>();

   // Lock
   private ReentrantLock rel;

   // Constructor
   public CustomerQueue(ReentrantLock rel){
      this.rel = rel;
   }
   
   // Get the next Customer
   public void getNext(){ 
      boolean ans;
      
      if(queue.isEmpty()){
         ans = false;
      }else{
         ans = rel.tryLock();
      }
      if(ans)
      {
          try {
            System.out.println("Start : " + Thread.currentThread().getId());
            int x = queue.poll();
            System.out.println("From Thread : " + Thread.currentThread().getId() +" the item is : " + x);
            // Thread.sleep(800);
          } catch (Exception e) {
            e.printStackTrace();
          } finally{
            rel.unlock();
          }
      }
      else
      {
         System.out.println("Waiting : " + Thread.currentThread().getId());
      }
   }

}

