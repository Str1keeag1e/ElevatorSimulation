import java.util.*;
import java.util.concurrent.Semaphore;

public class Driver {

    public static void main(String[] args){
        final int numPeople = 49;
        int count = 0;


        //declare elevator operating semaphore.
        Semaphore elevatorOperator = new Semaphore(0,false);

        //declaring threads for all 49 people.
        Thread [] peopleThreads = new Thread[numPeople];

        //a associative array for the person + destination
        List<List<Person>> personDestinationPerFloor = new ArrayList<>(10);

        //initialize elevator Thread
        Thread elevator = new Thread(new Elevator());

        elevator.start();
        
        //initialize person threads
        for(int i = 0; i < numPeople; i++){
            peopleThreads[i] = new Thread(new Person(i,1,randomNumber()));
        }

    }


    static int randomNumber(){

        Random randInteger = new Random();

        return randInteger.nextInt(8) + 2;
    }
}
