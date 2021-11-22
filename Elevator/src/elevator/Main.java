/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import static java.lang.constant.ConstantDescs.NULL;

/**
 *
 * @author abdus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private int ExitCount;
    public static void PrintOutput(elevator[] e,floor[] f){
        Main main=new Main();
        for(int i=0;i<5;i++){
            f[i].printQueData();
        }
       System.out.println("exit count : "+main.getExitCount());//Bunun içinde toplam çıkış yaptırılan kişi sayısı tutulmalı.
        for(int i=0;i<5;i++){ 
            System.out.println("active :"+e[i].isActive());
            if(e[i].isActive()) System.out.println("            mode:working");
          
            else  System.out.println("            mode:idle");
            System.out.println("            floor :"+e[i].getCrntFloor());
            if(e[i].getQue().size()!=0){
                System.out.println("            destinaiton :"+e[i].getQue().get(0).getTargetFloor());//Elevator class na targetFloor konunca buraya gelcek. 
            }
            else System.out.println("            destination :"+0);
            System.out.println("            direction :"+e[i].getdirection());//Burası sonradan elevator class'ındaki direction ile alınacak.Direction sürekli olarak değişmeli.
            System.out.println("            capacity :10");
            System.out.println("            count_inside :"+e[i].getcountInside());
            System.out.print("            inside :");
            for(int j=0;j<e[i].getQue().size();j++){
              System.out.print("("+e[i].getQue().get(j).getCustomerNum()+","+e[i].getQue().get(j).getTargetFloor()+") ");//get'in içinin j olduğuna dikkat et.
            }
            System.out.println();
        }
       for(int i=0;i<5;i++){
           System.out.print(i+".floor : ");
           if(i==0)
           {
             for(int j=0;j<f[i].getLoginQue().size();j++){
                   System.out.print("["+f[i].getLoginQue().get(j).getCustomerNum()+","+f[i].getLoginQue().get(j).getTargetFloor()+"]");  
             }
               
           
           }
           else{
               for(int k=0;k<f[i].getExitQue().size();k++){
                   System.out.print("["+f[i].getExitQue().get(k).getCustomerNum()+","+f[i].getExitQue().get(k).getTargetFloor()+"]");
               //Burası eskiden getLogin di sen getExit yaptın orayı sonradan düzeltebilirsin.
               }
           }
           
            System.out.println();
       }
        
    }
    public static void main(String[] args) throws InterruptedException {
        /*
        1.500ms aralıklarla Login class'ından run metodu çağırılır.0.katın loginque'sine eleman ekliyor.
        
        
        
        */
        
        floor f0 = new floor();
        floor f1 = new floor();
        floor f2 = new floor();
        floor f3 = new floor();
        floor f4 = new floor();
        floor f[] = {f0,f1,f2,f3,f4};

        elevator e0 = new elevator(true); //Sürekli aktif olarak çalışacak asansör  
        elevator e1 = new elevator(false);
        elevator e2 = new elevator(false);
        elevator e3 = new elevator(false);
        elevator e4 = new elevator(false);
        elevator e[] = {e0,e1,e2,e3,e4};

        

        //Thread'lerin basladığı yer.
        Login login = new Login(f[0]);
        //Thread work = new Thread(login); //login zaten thread, extends ediyor
        login.start();
        for(int i=0;i<3;i++) login.run();
            
        //customer c = new customer();
        //for(int i=0;i<2;i++) f[1].LoginAdd(c);
        
        Thread.sleep(500);
        System.out.println("Main check");
        PrintOutput(e,f);
        //f[0].printQueData(); // güncellendiğini görmek için yazılmıştır, delay farkı olduğu için güncellendiği görülmüyor
        System.out.println("**************");
        ElevatorMove Move0=new ElevatorMove(e,f);
        Move0.start();
   
        while(Move0.MScount<60000){ 
            
            Move0.join();//İş parçacığı bitene kadar bekle demek oluyor.
            
            Move0.run();
           
            
        }
    }
    
    
    
    
    
    public void setExitCount(int ExitCount){
        this.ExitCount=ExitCount;
    }
    public int getExitCount(){
        return this.ExitCount;
    }
   
}
