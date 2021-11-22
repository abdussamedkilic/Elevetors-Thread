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
public class floor {
    private int floorNum;
    private List<customer> loginQue; // asansörden inme yani asansörMove threadinin sonucunda atılacak
    private List<customer> exitQue; // exit threadine göre atılacak
    private static  int i = -1 ;
    public floor(){
        this.floorNum=++this.i;
        loginQue = new ArrayList<>();
        exitQue = new ArrayList<>();
    }

    public  int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public List<customer> getLoginQue() {
        return loginQue;
    }

    public void setLoginQue(List<customer> loginQue) {
        this.loginQue = loginQue;
    }

    public List<customer> getExitQue() {
        return exitQue;
    }

    public void setExitQue(List<customer> exitQue) {
        this.exitQue = exitQue;
    }
    
    public void printQueData(){
        int customerNum = 0,exitcustomerNum = 0,wantExit = 0;
        customer c;
        for(int i =0; i< loginQue.size();i++){
             c = loginQue.get(i);
            customerNum += c.getCustomerNum();
            if(c.isWantExit() == true){
                wantExit++;
            }
        }
        for(int j=0;j<exitQue.size();j++){
          c = exitQue.get(j);
          exitcustomerNum += c.getCustomerNum();
           if(c.isWantExit() == true){
                wantExit++;
           }
        }
        if(this.floorNum == 0)
        System.out.println(this.floorNum+". floor : queue : "+customerNum);
        else
        {
            System.out.println(this.floorNum+". floor : all : "+customerNum+" queue : "+exitcustomerNum+" wantExit :"+wantExit);
        }
    }
    public void LoginAdd(customer c) {
            this.loginQue.add(c);
    }
    public void ExitAdd(customer c){
        this.exitQue.add(c);
    }
}
