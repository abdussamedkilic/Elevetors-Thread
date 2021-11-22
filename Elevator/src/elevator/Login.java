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
public class Login extends Thread { // will be login thread

    private floor f;
    private customer c;
    public Login(floor f) {//0.kattaki nesneyi buna atamak için yapıyoruz.
        this.f = f;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            c = new customer();
            f.LoginAdd(c);
            
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }

    public floor getfloor() {
        return f;
    }

    public void setfloor(floor f) {
        this.f = f;
    }
}
