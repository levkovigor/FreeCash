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
public class FreeCash {

    /**
     * @param args the command line arguments
     */
    public static final int QUEUES = 1;
    public static final int CLIENT_SPEED = 2;
    public static final int CASH_SPEED = 3;
    public static LinkedBlockingDeque<Thread>[] queue = new LinkedBlockingDeque[QUEUES];

    public static void main(String[] args) {

        for (int i = 0; i < QUEUES; i++) {
            queue[i] = new LinkedBlockingDeque<>();
            if (i == 1) {
                new Cash(queue[i], 2).start();
            } else {
                new Cash(queue[i], CASH_SPEED).start();
            }
        }

        while (true) {

            try {
                Random generator = new Random();
                Thread.sleep((generator.nextInt(10) + 1) * 100 * 1);
            } catch (InterruptedException ex) {
                Logger.getLogger(FreeCash.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Client(queue).start();

            for (int i = 0; i < QUEUES; i++) {
                System.out.println(queue[i]);
            }
            System.out.println("-----");
        }

    }

}
