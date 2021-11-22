/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author abdus
 */
public class elevator { // will be elevator thread

    private int crntFloor;
    private int elevatorNum;
    private int countInside;
    private int targetFloor;
    private String direction;
    private List<customer> que;
    private boolean active;
    private static int i = -1;

    public elevator(boolean active) {
        this.elevatorNum = ++this.i;
        que = new ArrayList<>();
        this.crntFloor = 0;
        this.countInside=0;
        this.targetFloor=0;//Burası sonradan ekleme yapılırken asansörün ilk quesine göre değişmeli.
        this.direction="up";
        this.active = active;
    }

    public int add(customer c) {
        int sum=countInside+c.getCustomerNum();
        if (countInside < 10) {
           if(sum <= 10){
              return 1;
           }
           else{ // grupları bölme yapıcak 
               return 2;
           }             
        }
        return 0;
    }
    public int getCrntFloor() {
        return crntFloor;
    }

    public void setCrntFloor(int crntFloor) {
        this.crntFloor = crntFloor;
    }

    public int getElevatorNum() {
        return elevatorNum;
    }
   
    public void setElevatorNum(int elevatorNum) {
        this.elevatorNum = elevatorNum;
    }
    public int getcountInside(){
       return countInside; 
    }
    public void setcountInside(int countInside){
        this.countInside=countInside;
    }
    public List<customer> getQue() {
        return que;
    }

    public void setQue(List<customer> que) {
        this.que = que;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    public String getdirection(){
        return direction;
    }
    public void setdirection(String direction){
        this.direction=direction;
    }
    public int gettargetFloor(){
        return targetFloor;
    }
    public void settargetFloor(int targetFloor){
        this.targetFloor=targetFloor;
    }
}
