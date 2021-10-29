import java.util.*;
import java.util.concurrent.Semaphore;

public class Person implements Runnable  {

    //private ids
    private final int id;
    private int floor = 1;
    private final int floorDestination;

    public static Semaphore elevatorOperating = new Semaphore(0, false);
    public static Semaphore maxCapacity = new Semaphore(7,true);



    public Person(int id, int floor, int floorDestination){
        this.id = id;
        this.floor = floor;
        this.floorDestination = floorDestination;
    }

    public int getID(){
        return id;
    }

    public int getFloor(){
        return floor;
    }

    public int getFloorDestination(){
        return floorDestination;
    }


    //dont need synchronize for the actual functionality--but hashmap isn't threadsafe,
    //need to avoid read/write issues.
    synchronized public void enterElevator(){
        System.out.println("Person " + id + " enters the elevator to go to floor " + floorDestination );
        System.out.flush();

        //using a hashmap to keep track of floors + people destinations
        Elevator.floorPeopleDestinationMap.get(floorDestination).add(id);

    }

    synchronized public void exitElevator(){

        if(Elevator.floorPeopleDestinationMap.containsKey(floorDestination)){
            if(Elevator.floorPeopleDestinationMap.get(floorDestination).contains(id)){
                System.out.println("Person " + id + " leaves the elevator");
                System.out.flush();

                Elevator.floorPeopleDestinationMap.get(floorDestination).remove(id);
            }
        }
    }


    public void run(){

        try {

            //People are entering elevator, so we must acquire the permits for maxCapacity
            //(2.1)
            maxCapacity.acquire();
            enterElevator();
            Elevator.enterElevator();

            //if elevator is at max capacity, then release elevator from blocked;
            if(Elevator.getPeopleInElevator() == 7) {
                //(1.2)
                Elevator.elevatorPeopleIntakeOuttake.release();
            }
            //elevator is now operating--thus blocking people from leaving/entering.
            elevatorOperating.acquire();


            System.out.println("Thread " + id + " executing");
            System.out.flush();

            while(true) {
                if (floorDestination == Elevator.getFloor()) {
                    exitElevator();
                    Elevator.leaveElevator();
                }
            }





        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
