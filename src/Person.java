import java.util.*;

public class Person extends Thread {

    private int id;
    private int floor = 1;
    private int floorDestination;

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

    public void enterElevator(){
        System.out.println("Person " + id + " enters the elevator to go to floor " + floorDestination );
    }

    public void exitElevator(){
        System.out.println("Person " + id + " leaves the elevator");
    }

    public void run(){
        enterElevator();

    }


}
