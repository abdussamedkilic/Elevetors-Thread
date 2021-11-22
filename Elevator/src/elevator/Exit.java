/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdus
 */
public class Exit extends Thread { // will be exit thread

    private floor f[];
    Random rand = new Random();

    public Exit(floor f[]) {
        this.f = f;
    }

    /*public int exitThread(int fn, int n) {//fn targetFloor kat indexsi,n ise exitQue'ye alınacak olan customerNum
        List<customer> l = f[fn].getLoginQue();
        int tempN = n;
        int i;
        customer c;
        for (i = 0; i < l.size(); i++) {
            if (tempN <= 0) {
                return 1;
            }
            c = l.get(i);
            if (c.getCustomerNum() == tempN) {
                tempN -= c.getCustomerNum();
                c.setWantExit(true);
                c.setTargetFloor(0);
                c.setCurrentFloor(fn); // bunu ElevatorMove
                f[fn].ExitAdd(c);
                for (int j = 0; j <= i; j++) {
                    this.f[fn].getLoginQue().remove(j);
                }
            } else if (c.getCustomerNum() > tempN) {
                tempN -= c.getCustomerNum();
                int newCustomerNum = c.getCustomerNum() - n;
                this.f[fn].getLoginQue().get(i).setCustomerNum(newCustomerNum);
                customer tempC = new customer(n, fn);
                this.f[fn].ExitAdd(tempC);
                for (int j = 0; j < i ; j++) {
                    this.f[fn].getLoginQue().remove(j);
                }
            } else if (c.getCustomerNum() < tempN) {
                tempN -= c.getCustomerNum();
                this.f[fn].ExitAdd(c);
            }
        }
        return -1;
    }*/
    public int exitThread(int fn,int n){
        customer c=new customer(n,fn);
        for(int i=0;i<f[fn].getLoginQue().size();){
            if(n==f[fn].getLoginQue().get(i).getCustomerNum()){
                f[fn].getLoginQue().remove(i);
                f[fn].ExitAdd(c);
                break;
            }
            else if(n<f[fn].getLoginQue().get(i).getCustomerNum()){
                f[fn].getLoginQue().get(i).setCustomerNum(f[fn].getLoginQue().get(i).getCustomerNum()-n);
                c.setCustomerNum(n);
                f[fn].ExitAdd(c);
                break;
            }
            else{
                n-=f[fn].getLoginQue().get(i).getCustomerNum();
                f[fn].getLoginQue().remove(i);
                i++;
            }
               
            
        }
        
        
        
        return -1;
    }
    
    public void run() {
        try {
            Thread.sleep(1000);
            int targetFloor = 1 + rand.nextInt(4);
            int wantExitCustomer = 1 + rand.nextInt(5);
            if (f[targetFloor].getLoginQue().size() != 0) {
                exitThread(targetFloor, wantExitCustomer);
                
            } else {
                this.run();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
