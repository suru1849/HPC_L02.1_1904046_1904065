package suru65.QueueSimulator;

public class QueueSimulator {
    private int totalArrivedCustomer = 0;
    private int totalDepartsCustomer = 0;
    private int totalSerrvedCustomer = 0;
    private double totalTimeToSerrveCustomers = 0;
    private double averageTimeToServeEachCustomer = 0;


    public void addTotalArrivedCustomer(){
        totalArrivedCustomer++;
    }

    public void addTotalDepartsCustomer(){
        totalDepartsCustomer++;
    }

    public void addTotalSerrvedCustomer(){
        totalSerrvedCustomer++;
    }

    public void addTotalTimeToSerrveCustomers(double t ){
        totalTimeToSerrveCustomers += t;
    }

    public void conclussion(double simulationTime){
        averageTimeToServeEachCustomer = (totalTimeToSerrveCustomers / (double)totalSerrvedCustomer);

        System.out.println("....Conclussion....");
        System.out.println("SimulationTime : " + simulationTime + " seconds.");
        System.out.println("Total arrived customer : " + totalArrivedCustomer);
        System.out.println("Total departs customer : " + totalDepartsCustomer);
        System.out.println("Total serrved customer : " + totalSerrvedCustomer);
        System.out.println("Total time taken to serrve the customers : " + totalTimeToSerrveCustomers + " seconds.");
        System.out.println("Average time to serrve the customers : " + averageTimeToServeEachCustomer +" seconds.");
    }
}
