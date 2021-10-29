import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;


public class Elevator implements Runnable {


    static int MAX_CAPACITY = 7;
    static int topFloor = 10;

    //declaring semaphores

    public static Semaphore elevatorPeopleIntakeOuttake = new Semaphore(0,false);
    public static Semaphore elevatorAtFloor = new Semaphore(0,false);




    private static int currentPeople;
    private static int currentFloor;
    private static boolean doorClosed = true;
    static Map<Integer,List<Integer>> floorPeopleDestinationMap = new ConcurrentHashMap<>();

    public Elevator(){
        currentPeople = 0;
        currentFloor = 1;

    }

    public static int getPeopleInElevator(){
        return currentPeople;
    }

    public static int getFloor(){
        return currentFloor;
    }



    public static void elevatorGoUp(){
        if(currentFloor < 10){

            currentFloor++;
            System.out.println("Elevator is going up, now at floor: " + currentFloor);
            System.out.flush();
        }else{
            System.out.println("Elevator is already at floor 10");
            System.out.flush();
        }
    }

    public static void elevatorGoDown(){
        if(currentFloor >= 1 ){
            currentFloor--;
        }else{
            System.out.println("Elevator is already at floor 1");
            System.out.flush();
        }
    }


    public static void leaveElevator(){
        if(currentPeople > 0){
            currentPeople--;
            Driver.numPeople--;
        }else{
            System.out.println("The amount of people in the elevator is already 0");
            System.out.flush();
        }
    }

    public static void enterElevator(){
        if(currentPeople < 10){
            currentPeople++;
        }else{
            System.out.println("Elevator is at max capacity");
            System.out.flush();
        }
    }
    synchronized public static void openDoor(){
        if(doorClosed) {
            System.out.println("Elevator door opening at floor " + currentFloor);
            System.out.flush();
            doorClosed = false;
        }else{
            System.out.println("Elevator door is already open");
            System.out.flush();
        }
    }

    public static void closeDoor(){
        if(!doorClosed){
            System.out.println("Elevator door closing");
            System.out.flush();
            doorClosed = true;
        }
    }

    public void run(){

        //intialize global hashmap

        for(int i = 1; i < 11; i++){
            floorPeopleDestinationMap.put(i, new ArrayList<Integer>(10));
        }

        while(Driver.numPeople > 0) {
            try {
                //at floor 1, open elevator doors for people to come in
                openDoor();
                //since people are entering--elevator operation should be blocked.
                //(1.1)
                elevatorPeopleIntakeOuttake.acquire();

                //once people have entered, close the door (elevatorPeopleIntake released in Person.java)
                closeDoor();


            //double checking hashmap populated correctly
                System.out.println("DEBUG*********************************************************");
            for(List<Integer> people : floorPeopleDestinationMap.values()){
                System.out.println(Arrays.toString(people.toArray()));
            }
                System.out.println("END DEBUG*********************************************************");
            //System.out.println("key values");







                //now, go up to each floor, if list of people size > 0; then release people
                for (int i = 2; i < 10; i++) {

                    //System.out.println("Elevator is now at floor " + i);

                    elevatorGoUp();

                    //if people need to get off on that floor--stop elevator operation and open door
                   //System.out.println("floor " + i + " has people in it " + (floorPeopleDestinationMap.get(i).size() > 0));

                    if(floorPeopleDestinationMap.get(i).size() > 0){
                        openDoor();
                        //elevator is opened and not operating, allowing people to get off (unblocking people threads);
                        //(3.1)
                        //System.out.println(Person.elevatorOperating.availablePermits());
                        Person.elevatorOperating.release();
                        //blocking elevator from operating while people are moving.
                        elevatorPeopleIntakeOuttake.acquire();

                        //System.out.println(elevatorPeopleIntakeOuttake.availablePermits());

                    }



                    }



            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



}
