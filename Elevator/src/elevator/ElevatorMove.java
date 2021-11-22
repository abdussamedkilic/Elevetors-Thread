/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdus
 */
public class ElevatorMove extends Thread {

    private elevator[] e;
    private floor[] f;
    public int MScount;
    public int ExitCount;
    public ElevatorMove(elevator[] e, floor[] f) {
        this.e = e;
        this.f = f;
    }

    public int findActiveNum() { // çalışan asansör sayısını bulur
        int i, sum = 1;
        for (i = 1; i < e.length; i++) { // 1. asansör daima aktif
            if (e[i].isActive() == true) {
                sum++;
            }
        }
        return sum;
    }
    
    public void loginAdd(elevator e){
        //0.kat boş mu değil mi diye kontrol yapılmalı ve ona göre koşula girmeli.Ona dikkat et.!!!Belki birden fazla asansör yoğunluğu azaltır.
        customer c;
        int control = -1;
        int i=0;
       
        while(control!=0){
            if(f[0].getLoginQue().size()!=0){
            c=f[0].getLoginQue().get(i);
            if(e.getCrntFloor()==0){//Move'da hata yapılmışsa diye burda kontrol ediyoruz.
                control=e.add(c);
                if(control==1){
                    e.getQue().add(c);
                    e.setcountInside(e.getcountInside() + c.getCustomerNum());
                    f[0].getLoginQue().remove(i);
                }
                else if(control==2){
                    int addNum = 10 - e.getcountInside();
                    customer cTemp = new customer();
                    cTemp.setTargetFloor(c.getTargetFloor());
                    cTemp.setCustomerNum(addNum);
                    cTemp.setCurrentFloor(c.getCurrentFloor());
                    f[0].getLoginQue().get(0).setCustomerNum(c.getCustomerNum() - addNum);
                    e.getQue().add(cTemp);
                    e.setcountInside(e.getcountInside() + addNum);
                    i++;
                    break;
                }
            }
            else break;
        
        }
            else break;
        }
       
    }

    public boolean dropCustomer(elevator e, customer c) { //verilen müşteri, verilen asansörün anlık katı ile uyuşuyorsa ekler, uyuşmazsa ekleme yapmaz
        if (c.getTargetFloor() == e.getCrntFloor()) {
            f[c.getTargetFloor()].LoginAdd(c);
            e.getQue().remove(0); // First in first out mantığı Move methodunuda da bu şekilde kullanılmalı 
            e.setcountInside(e.getcountInside()-c.getCustomerNum());
            return true;
        }
        System.out.println("that customer not reach him target floor");
        return false;
    }
    
    public void takeCustomerForAllElevator(){ // bu sayede loginQue'den alımda herkes kendi sırasını bekliyor
        if(f[0].getLoginQue().size() != 0){
             for(int i=0; i< findActiveNum();i++){ 
                 if(e[i].getCrntFloor() == 0){
                     loginAdd(e[i]);
                     //f[0].getLoginQue().remove(0);//Burası sonradan farklı olabilir yine onlara iyi bak.
                             
                 }
             }
        }
    }
    public int ArriveFloorforExit(elevator e,int floorIndex) throws InterruptedException{
       int updateMScount=0;
       customer c;
       //Burası döngü içerisinde olmalı ve asansörün kapasitesi doldurulmalı.O yüzden o döngüye dikkat et.
       //Belki boş değilse falan diye koşul gelmesi gerekebilir.Onlarıda iyice kontrol et.
       while(true){
           if(e.getCrntFloor()==floorIndex){
               for(int i=0;i<f[floorIndex].getExitQue().size();){
               int sum=e.getcountInside()+f[floorIndex].getExitQue().get(i).getCustomerNum();//Burası belki i de olabilir.
               if(sum<=10){
                   c=f[floorIndex].getExitQue().get(i);
                   f[floorIndex].getExitQue().remove(i);
                   e.getQue().add(c);
                   e.setcountInside(sum);
                   e.setdirection("down");
                   
               }
               else if(sum>10){
                   int fark=10-e.getcountInside();
                   c=f[floorIndex].getExitQue().get(i);
                   f[floorIndex].getExitQue().get(i).setCustomerNum(f[floorIndex].getExitQue().get(i).getCustomerNum()-fark);
                   c.setCustomerNum(e.getcountInside()+fark);
                   e.getQue().add(c);
                   e.setcountInside(e.getcountInside()+fark);
                   e.setdirection("down");
                   break;
               }
           }
               
               break;
           }
           else if(e.getCrntFloor()<floorIndex){
               e.setCrntFloor(e.getCrntFloor()+1);
               Thread.sleep(200);
               updateMScount+=200;
               
           }
           else{
               e.setCrntFloor(e.getCrntFloor()-1);
               Thread.sleep(200);
               updateMScount+=200;
           }
           
       }
       
       
       
       
        return updateMScount;
    }
    public void move() throws InterruptedException{ 
        // 5 asansör nasıl birlikte katlara ilerleme yapacaklar, nasıl birlikte müşterileri indirecekler
        // nasıl exit que'leri kontrol edip çıkmak isteyenleri gönderecek ??? 
        
        int msCount=500;
        this.MScount+=msCount;
        boolean isDrop;
        Main main=new Main();
        takeCustomerForAllElevator();//Bu başka yerde tanımlanabilir.
        System.out.println("****************");
        main.PrintOutput(e,f);
        
       for(int i=0;i<findActiveNum();i++){
           //e[i].settargetFloor(e[i].getQue().get(0).getTargetFloor());Güncelleme işlemlerinden sonra yukarıdaki metot değişmeli.
           e[i].setCrntFloor(e[i].getCrntFloor()+1);
           Thread.sleep(200);
           while(true){
          
               if(e[i].getQue().size()!=0){
               
               isDrop=dropCustomer(e[i],e[i].getQue().get(0));
               //Burası wait olabilir ve mainden birden fazla start olabilir.
               if(isDrop){
                   //Boşaltma başarılı ise bunun içine girecek.
                   System.out.println("Eleman Bırakılmıştır."); 
               }
               else{
                  //Başarılı olamadığı durum için bu yapılır.
                  //Buralarda direction da eklenip update edilebilir.
                  if(e[i].getCrntFloor()<e[i].getQue().get(0).getTargetFloor())
                      e[i].setCrntFloor(e[i].getCrntFloor()+1);
                  else
                      e[i].setCrntFloor(e[i].getCrntFloor()-1);
                  
                  Thread.sleep(200);
                  msCount+=200;
                     
               }
           }
              else break;
               
           
        }
          
       }
       this.MScount+=msCount;
       main.PrintOutput(e,f);
       
       /*
        Burdaki koşulu güncellemeyi unutma.
       */
        Login login=new Login(f[0]);
        Exit exit=new Exit(f);
        System.out.println("****msCount****"+msCount);
        //Burda ya da main'de Exit Threadinin içerisindeki run metotdu çağırılmalı.Koşul altında çağırılmalı.
        
        if(MScount%500==0 || MScount%500==100){
           System.out.println("\nTEKRARDAN GİRİŞ YAPILMAKTADIR...\n");
           login.run();
            
        }
        if(MScount%1000==0 || MScount%1000==100 || MScount%1000==200){
             System.out.println("\nTEKRARDAN ÇIKIŞ YAPILMAKTADIR...\n");
             exit.run();
        }
        
       
        
        //for(int i=0;i<4;i++) exit.run();//Deneme amaçlı yapılmıştır.
        Thread.sleep(2000);
        main.PrintOutput(e,f);
       
        /*
         Exit Kuyruğundan çıkış işlemleri burada gerçekleşiyor.direction değişkenini burada güncellemeyi unutma.
         Burda çıkıştaki yerlere ulaşıyor.
        */
        //Bunun birden fazla asansörle çalışmış halini göster.Dene.
        for(int i=0;i<findActiveNum();i++){
            for(int j=4;j>=1;j--){
                if(f[j].getExitQue().size()!=0){
                  System.out.println("\nÇIKMAK İÇİN BEKLENEN KAT BULUNMUŞTUR...KAT NO:"+j);
                  msCount+=ArriveFloorforExit(e[i],j);  
                }
            }
        }
        this.MScount+=msCount;
        System.out.println("\n\nÇIKIŞA GÖTÜRME İŞLEMLERİ BAŞLANGIÇ\n\n");
        System.out.println("****msCount****"+msCount);
        
        if(MScount%500==0 || MScount%500==100){
           System.out.println("\nTEKRARDAN GİRİŞ YAPILMAKTADIR...\n");
           login.run();
            
        }
        if(MScount%1000==0 || MScount%1000==100 || MScount%1000==200){
             System.out.println("\nTEKRARDAN ÇIKIŞ YAPILMAKTADIR...\n");
             exit.run();
        }
        
        Thread.sleep(500);//Bunların güncelliği sağladığına emin ol.
        main.PrintOutput(e,f);
        
        /*
        Çıkışa götürme işlemleri bunlardan önce yine exit ve login Thread'leri için msCount kontrolleri yapılmalıdır.
        Koşullar ile birlikte.Main de ise sadece start diyip sonra join yield veya wait gibi işlemler koyacağız.
        */
        
        
        int ControlVariable=0;
        for(int i=0;i<findActiveNum();i++){
           this.ExitCount+=e[i].getcountInside();
           main.setExitCount(this.ExitCount);
           e[i].setcountInside(0);
           e[i].setdirection("up");
           while(e[i].getQue().size()!=0){
               ControlVariable=1;
               if(e[i].getQue().get(0).getTargetFloor()==e[i].getCrntFloor()){//TargetFloor olarak bunu kullanman gerektiğini unutma.Hedef katı 0 yani.
                  
                   e[i].getQue().remove(0);
               }
               else{
                   e[i].setCrntFloor(e[i].getCrntFloor()-1);
                   Thread.sleep(200);
                   msCount+=200;
               }
           }
           if(ControlVariable==0){
            //0.kata boş olarak götür.Ordan işlem yapmaya başlasın.Bu çalışma mantığına dikkat et.
               while(e[i].getCrntFloor()!=0){
                   e[i].setCrntFloor(e[i].getCrntFloor()-1);
                   Thread.sleep(200);
                   msCount+=200;
               }
               
           }
        }
        this.MScount+=msCount;
        main.setExitCount(ExitCount);
        //this.MScount+=msCount;
        System.out.println("\nASANSÖRLERİN İÇİNDEKİ MÜŞTERİLER ÇIKIŞA GÖTÜRÜLMEKTEDİR...\n");
        System.out.println("****msCount****"+msCount);
        
        if(MScount%500==0 || MScount%500==100){
           System.out.println("\nTEKRARDAN GİRİŞ YAPILMAKTADIR...\n");
           login.run();
            
        }
        if(MScount%1000==0 || MScount%1000==100 || MScount%1000==200){
             System.out.println("\nTEKRARDAN ÇIKIŞ YAPILMAKTADIR...\n");
             exit.run();
        }
        
        Thread.sleep(500);
        main.PrintOutput(e,f);
    
        Control control=new Control(f,e);
        System.out.println("\nKONTROL İŞLEMİ YAPILMAKTADIR...\n");
        control.run();
        
        Thread.sleep(500);
        main.PrintOutput(e,f);
    
    }

    
    public void run() {
        try {
            //Burada move metodunu çağırdık.
           move();
        } catch (InterruptedException ex) {
            Logger.getLogger(ElevatorMove.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
