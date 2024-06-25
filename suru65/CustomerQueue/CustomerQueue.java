package suru65.CustomerQueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerQueue {
   // Customers queue
   public final Queue<Integer> queue = new LinkedList<>();

   // Lock
   private ReentrantLock rel;

   // Constructor
   public CustomerQueue(ReentrantLock rel){
      this.rel = rel;
   }
   
   // push in queue
   public void pushInQueue(){

    for(int i = 0; i<10; i++){
        queue.add(i+1);
    }
     
   }

   public void getNext(){
      System.out.println("Start : " + Thread.currentThread().getId());
      
      boolean ans = rel.tryLock();


      if(ans)
      {
          try {
             int x = queue.poll();
             System.out.println("From Thread : " + Thread.currentThread().getId() +" the item is : " + x);
            Thread.sleep(800);
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


   // pop from queue
   public void popFromQueue(){
       while (!queue.isEmpty()) {
           getNext();

           try {
            Thread.sleep(500);
           } catch (Exception e) {
            e.printStackTrace();
           }

       }
   }
}

