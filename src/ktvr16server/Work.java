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
    volatile List<User> users = new ArrayList<>();
    boolean running = true;
    void add(User user){
        synchronized(users){
            users.add(user);
        }
    }
    void remove(User user){
        synchronized(users){
            users.remove(user);
            user.pw.println("by-by");
        }
    }
    void sendToAll(String text){
        
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
                        System.out.println(Thread.currentThread().getName()+"  = "+Thread.currentThread().isAlive());
                        if(user.br.ready()){
                            while(user.br.ready()){
                                char c=(char)user.br.read();
                                if(c =='\n'){
                                   if("".equals(user.name)){
                                        user.name = user.line.trim();
                                        user.line = "";
                                        sendToAll("Hello, "+user.name);
                                        user.pw.println("Для выхода набери \"stop\"");
                                    }else{
                                       if("stop".equals(user.line)){
                                           System.out.println(Thread.currentThread().getName()+" line = "+user.line);
                                           user.pw.println("by-by!"); 
                                           this.remove(user);
                                           sendToAll(user.name + " покинул нас!");
                                       }else{
                                           sendToAll(user.name+" send: "+user.line.trim());
                                       }
                                       user.line = "";
                                    } 
                                }else{
                                    user.line += c;
                                }
                            }
                        }
                        if(users.size()==0){break;}
                    }
                }
            }while(running);
        } catch (IOException ex) {
            Logger.getLogger(Work.class.getName()).log(Level.SEVERE, "Не могу писать или читать в сокет, завершаем работу", ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Work.class.getName()).log(Level.SEVERE, " Поток прерван", ex);
        }    
        
    }
}
