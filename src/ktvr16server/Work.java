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
        synchronized(users){
            users.add(user);
        }
    }
    void remove(User user){
        synchronized(users){
            users.remove(user);
        }
    }
    @Override
    public void run(){
        try {
            do{
                Thread.sleep(500);
                synchronized(users){
                    for (User user : users) {
                        if(user.br.ready()){
                            while(user.br.ready()){
                               char c=(char)user.br.read();
                                if(c =='\n'){
                                   if("".equals(user.name)){
                                        user.name = user.line;
                                        user.line = "";
                                        user.pw.println("Hello, "+user.name);
                                    }else{
                                       if(user.line.equals("stop")){
                                           running=false;
                                           user.pw.println(user.name+" by-by!");
                                           this.remove(user);
                                           break;
                                       }else{
                                           user.pw.println(user.name+" send: "+user.line);
                                           user.line = "";
                                       }
                                    } 
                                }else{
                                    user.line += c;
                                }
                            }
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
