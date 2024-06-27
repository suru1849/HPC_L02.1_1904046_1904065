import java.util.concurrent.locks.ReentrantLock;

import suru65.CustomerQueue.CustomerQueue;

public class Main {

    public static volatile boolean running = true;

    public static void main(String[] args) {
      // time for server
      int a = 3, b=15;
      // Queue size
      int n = 10;
      // Time for adder
      int c1 = 5, c2 = 10;
      // Lock
      ReentrantLock rel =  new ReentrantLock();
      // Customer Queue
      CustomerQueue obj = new CustomerQueue(rel);
    
      // Task for server;
      Runnable task1 = () -> {
        while (running) {
         obj.getNext();
         
          try {
             long x =(long)Math.random()*(b-a) + a;
             Thread.sleep(x*100);
          } catch (InterruptedException e) {
             e.printStackTrace();
          }
       }
      };

      // task for adder
      Runnable task2 = () -> {
        while ((obj.queue.size() <= n) && running) {
           boolean ans = rel.tryLock();
    
           if(ans){
             // Adding new customer to the queue
             System.out.println("Adding a new customer to the \'Queue\'.");
             try {
                obj.queue.add(1);
                
             } catch (Exception e) {
                e.printStackTrace();
             } finally {
                rel.unlock();
                // wait for a while
                try {
                   long x =(long)Math.random()*(c2-c1) + c1;
                   Thread.sleep(x*100);
                } catch (InterruptedException e) {
                   e.printStackTrace();
                }
             }
           } else {
             System.out.println("Waiting for adding new customer to the \'Queue\'.");
           }
        }
      };


      Thread[] server = new Thread[5];
      for(int i = 0; i < 5; i++){
        server[i] = new Thread(task1);
      }
      Thread adder = new Thread(task2);
      
      adder.start();
      for(int i = 0; i < 5; i++){
        server[i].start();
      }

      // runiing threads for t seconds
      try {
        Thread.sleep(60*1000);
      } catch (Exception e) {
        e.printStackTrace();
      }

      running = false;

        
    }
}