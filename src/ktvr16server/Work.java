/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktvr16server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melnikov
 */
public class Work implements Runnable{
    List<User> users = new ArrayList<>();
    boolean running = true;
    void add(User user){
        users.add(user);
    }

    @Override
    public void run(){
        try {
            Thread.sleep(500);
            do{
                synchronized(users){
                    for (int i = 0; i < users.size(); i++) {
                        User user = users.get(i);
                        if(user.br.ready()){
                            user.line = user.br.readLine();
                            if(user.line.equals("stop")){running=false;}
                            user.pw.println(user.name+" write: "+user.line);
                        }
                    }
                }
            }while(running);
           
        } catch (IOException ex) {
            Logger.getLogger(Work.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Work.class.getName()).log(Level.SEVERE, null, ex);
        }    
        
    }
}
