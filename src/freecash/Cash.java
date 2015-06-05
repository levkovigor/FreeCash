/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freecash;

import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author igor
 */
public class Cash extends Thread {

    LinkedBlockingDeque<Thread> queue;
    int speed;

    public Cash(LinkedBlockingDeque<Thread> queue, int speed) {

        this.queue = queue;
        this.speed = speed;

    }

    public void run() {

        while (true) {

            if (!queue.isEmpty()) {
                Client client = (Client) queue.poll();
                client.setQueue(-1);

                try {
                    Random generator = new Random();
                    Thread.sleep((generator.nextInt(10) + 1) * 100 * this.speed);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Cash.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }

}
