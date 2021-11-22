/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elevator;

/**
 *
 * @author abdus
 */
public class Control extends Thread { // will be control thread
    // check-up floor exitQue size if that size

    floor[] f;
    elevator[] e;

    public Control(floor[] f, elevator[] e) {
        this.f = f;
        this.e = e;
    }

    public int calculateCnum() {
        int exitCustomerNum = 0;
        for (int i = 1; i < f.length; i++) {
            for (int j = 0; j < f[i].getExitQue().size(); j++) {
                customer c = f[i].getExitQue().get(j);
                exitCustomerNum += c.getCustomerNum();
            }
        }
        return exitCustomerNum;
    }

    public void setActive(int exitCustomerNum) {
        if (exitCustomerNum >= 20) {
            e[1].setActive(true);
        } else {
            e[1].setActive(false);
        }

        if (exitCustomerNum >= 40) {
            e[2].setActive(true);
        } else {
            e[2].setActive(false);
        }
        if (exitCustomerNum >= 60) {
            e[3].setActive(true);
        } else {
            e[3].setActive(false);
        }
        if (exitCustomerNum >= 80) {
            e[4].setActive(true);
        } else {
            e[4].setActive(false);
        }
    }

    @Override
    public void run() {
        setActive(calculateCnum());
    }

}
