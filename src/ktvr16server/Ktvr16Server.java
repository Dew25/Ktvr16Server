/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktvr16server;
import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 * @author Melnikov
 */
public class Ktvr16Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        int port=3001;
        Boolean working=true;
        ServerSocket ss=new ServerSocket(port);
        System.out.println("Server listen port "+ port);
        Work work = new Work();
        new Thread(work).start();
        while(working){
            new User(ss.accept(),work);
        }
       
    }
    
}
