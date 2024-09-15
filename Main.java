import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

import suru65.CustomerQueue.CustomerQueue;
import suru65.QueueSimulator.QueueSimulator;

public class Main {

    public static volatile boolean running = true;

    public static void main(String[] args) {
      // Take input from the users
      @SuppressWarnings("resource")
      Scanner inObj = new Scanner(System.in);

      System.out.print("X servers, X : ");
      int numberOfServers = inObj.nextInt();

      System.out.print("sizeOfQueue, N : ");
      int sizeOfQueue = inObj.nextInt();

      System.out.print("T seconds for simulation, T : ");
      int simulationTime = inObj.nextInt();

      System.out.print("Time range (a,b)->seconds for serving one customer, a : ");
      int a = inObj.nextInt();
      System.out.print("b : ");
      int b = inObj.nextInt();

      System.out.print("Time range (c1,c2)->seconds for arriving a customer, c1 : ");
      int c1 = inObj.nextInt();
      System.out.print("c2 : ");
      int c2 = inObj.nextInt();
      

      // QueueSimulator
      QueueSimulator qs = new QueueSimulator();
    
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
                if(obj.queue.size() == sizeOfQueue){
                  qs.addTotalDepartsCustomer();
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


      Thread[] server = new Thread[numberOfServers];
      for(int i = 0; i < numberOfServers; i++){
        server[i] = new Thread(task1);
      }
      Thread adder = new Thread(task2);
      
      adder.start();
      for(int i = 0; i < numberOfServers; i++){
        server[i].start();
      }

      // runiing threads for T seconds
      try {
        Thread.sleep(simulationTime*1000);
      } catch (Exception e) {
        e.printStackTrace();
      }

      // Killing the running threads.
      running = false;

      qs.conclussion(simulationTime);

        
    }
}