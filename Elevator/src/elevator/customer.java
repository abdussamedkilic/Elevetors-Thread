/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.Random;

/**
 *
 * @author abdus
 */
public class customer { // tüm atamaları kendi içerisinde yapıyor, bizim yapmamız gereken buradan türetmek

    private int customerNum;
    private int targetFloor;
    private int currentFloor;
    Random rand = new Random();
    private boolean wantExit;

    public customer() {//Burda avm'ye giriş yapan müşteri sayısı ve hedef katı bulunuyor.
        this.customerNum = 1 + rand.nextInt(10);
        this.currentFloor = 0;
        this.targetFloor = 1 + rand.nextInt(4);
        this.wantExit = false; // Exit threadinde kontrol et, targetFloor == currentFloor ? true : false
    }
    
    public customer(int cn, int fn){ // Exitteki bir koşul için gerekli
        this.customerNum = cn;
        this.currentFloor = fn;
        this.targetFloor = 0;
        this.wantExit = true;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public boolean isWantExit() {
        return wantExit;
    }

    public void setWantExit(boolean wantExit) {
        this.wantExit = wantExit;
    }
}
