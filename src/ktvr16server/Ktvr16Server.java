/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ktvr16server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Melnikov
 */
public class Ktvr16Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException{
        ServerSocket ss=null;
        int port=3001;
        Boolean working=true;
            ss=new ServerSocket(port);
            System.out.println("Server listen port "+ port);
            Work work = new Work();
            new Thread(work).start();
            while(working){
                new User(ss.accept(),work);
            }
       
    }
    
}
