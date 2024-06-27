import java.util.concurrent.locks.ReentrantLock;

import suru65.CustomerQueue.CustomerQueue;
import suru65.QueueSimulator.QueueSimulator;

public class Main {

    public static volatile boolean running = true;

    public static void main(String[] args) {
      // QueueSimulator
      QueueSimulator qs = new QueueSimulator();
    
      // time for server
      int a = 60, b=300;
      // Queue size
      int n = 5;
      // Time for adder
      int c1 = 20, c2 = 60;
      // Lock
      ReentrantLock rel =  new ReentrantLock();
      // Customer Queue
      CustomerQueue obj = new CustomerQueue(rel);
    
      // Task for server;
      Runnable task1 = () -> {
        while (running) {
         int value = obj.getNext();

         if(value == 1){qs.addTotalSerrvedCustomer();}
         
          try {
             long x =(long)Math.random()*(b-a) + a;
             if(value == 1){qs.addTotalTimeToSerrveCustomers(x*1000);}
             Thread.sleep(x*1000);
          } catch (InterruptedException e) {
             e.printStackTrace();
          }
       }
      };

      // task for adder
      Runnable task2 = () -> {
        while (running) {
           
           boolean ans = rel.tryLock();
    
           if(ans){

             qs.addTotalArrivedCustomer();

             // Adding new customer to the queue
             System.out.println("Adding a new customer to the \'Queue\'.");
             try {
                // Cheking if the queue is full or empty.
                if(obj.queue.size() == n){

                  qs.addTotalDepartsCustomer();

                   try {
                       long x =(long)Math.random()*(c2-c1) + c1;
                       Thread.sleep(x*1000);
                   } catch (InterruptedException e) {
                        e.printStackTrace();
                   }      
                }else{
                  obj.queue.add(1);
                }
                
             } catch (Exception e) {
                e.printStackTrace();
             } finally {
                rel.unlock();
                // wait for a while
                try {
                   long x =(long)Math.random()*(c2-c1) + c1;
                   Thread.sleep(x*1000);
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
        Thread.sleep(60*5*1000);
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Killing the running threads.
      running = false;

      qs.conclussion(0.5);

        
    }
}