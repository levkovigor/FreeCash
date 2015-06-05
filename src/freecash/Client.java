/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package freecash;

import static freecash.FreeCash.QUEUES;
import java.util.concurrent.LinkedBlockingDeque;

/**
 *
 * @author igor
 */
public class Client extends Thread{
    
   LinkedBlockingDeque<Thread>[] queue;
    int q;


    public Client(LinkedBlockingDeque<Thread>[] queue) {
        this.queue = queue;
    }
    
    public void setQueue(int q){
                this.q = q;
    }
    
    public int minQueue(){
       
        int minQ = 0;
        int size = queue[0].size();
        
        for (int i = 1; i < QUEUES; i++){
            if (size > queue[i].size()){
                size = queue[i].size();
                minQ = i;
            }
        }
        
        return minQ;   
    }
    
    
    public void run(){
        
        int minQ = minQueue();
        queue[minQ].addLast(this);
        this.q = minQ;
        
        
        while (this.q != -1){
          
          try{
            if (queue[this.q].peekLast() == this){
                
               minQ = minQueue();
               
               if (minQ != this.q){
                   if ((queue[minQ].size()+1) < queue[this.q].size()){
                   queue[this.q].remove(this);
                   queue[minQ].addLast(this);
                   this.q = minQ;
               }
               }
            
            }
           
        }catch (ArrayIndexOutOfBoundsException e){}
        }
       // System.out.println(this.getName() + " ushel");
    }
    
    public String toString(){
        return String.valueOf(this.getId());
    }
}
