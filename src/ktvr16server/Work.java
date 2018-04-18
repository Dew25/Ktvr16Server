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
    void sentToAll(String text){
        
            for (User user : users) {
                user.pw.println(text);
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
                                        user.name = user.line.trim();
                                        user.line = "";
                                        sentToAll("Hello, "+user.name);
                                    }else{
                                       if("stop".equals(user.line)){
                                           user.pw.println(user.name+" by-by!");
                                           this.remove(user);
                                           break;
                                       }else{
                                           sentToAll(user.name+" send: "+user.line.trim());
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
