import java.util.*;
import java.util.concurrent.Semaphore;

public class Driver {

    static int numPeople = 49;
    public static void main(String[] args){

        int count = 0;


        //declare elevator operating semaphore.


        //declaring threads for all 49 people.
        Thread [] peopleThreads = new Thread[numPeople];

        //a associative array for the person + destination



        //create people threads
        for(int i = 0; i < 10; i++){
            peopleThreads[i] = new Thread(new Person(i,1,randomNumber()));

        }
        //initialize elevator Thread
        Thread elevator = new Thread(new Elevator());

        elevator.start();

        //initialize person threads

        for(int i = 0; i < 10; i++){
            peopleThreads[i].start();
        }



    }


    static int randomNumber(){

        Random randInteger = new Random();

        return randInteger.nextInt(8) + 2;
    }
}
