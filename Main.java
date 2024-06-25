import java.util.concurrent.locks.ReentrantLock;

import suru65.CustomerQueue.CustomerQueue;

public class Main {

    public static void main(String[] args) {
        ReentrantLock rel = new ReentrantLock();

        CustomerQueue obj = new CustomerQueue(rel);
        obj.pushInQueue();
        
        Thread[] server = new Thread[5];
        // initalizing Servers
        for(int i = 0; i < 5; i++){
            server[i] = new Thread(()->{
                obj.popFromQueue();
            });
        }

        // Start the servers
        for(int i = 0; i < 5; i++){
            server[i].start();
        }

        
    }
}