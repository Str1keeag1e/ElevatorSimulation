import java.util.*;
import java.util.concurrent.Semaphore;


public class Elevator extends Thread {


    static int MAX_CAPACITY = 7;
    static int topFloor = 10;
    static Semaphore maxCapacity = new Semaphore(7,true);

    private static int currentPeople;
    private static int currentFloor;
    private static boolean doorClosed;

    List<Person> peopleInElevator;

    public Elevator(){
        currentPeople = 0;
        currentFloor = 1;
        this.peopleInElevator = new ArrayList<>();
    }

    public int getPeopleInElevator(){
        return currentPeople;
    }

    public int getFloor(){
        return currentFloor;
    }


    public void leaveElevator(){
        if(currentPeople > 0){
            currentPeople--;
        }else{
            System.out.println("The amount of people in the elevator is already 0");
        }
    }

    public void enterElevator(){
        if(currentPeople < 7){
            currentPeople++;
        }else{
            System.out.println("Elevator is at max capacity");
        }
    }
    public void openDoor(){
        if(doorClosed) {
            System.out.println("Elevator door opening at floor " + currentFloor);
            doorClosed = false;
        }else{
            System.out.println("Elevator door is already open");
        }
    }

    public void closeDoor(){
        if(!doorClosed){
            System.out.println("Elevator door closing");
            doorClosed = true;
        }
    }

    public void run(){
        while(true){

        }
    }





}
