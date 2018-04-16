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
        Socket sc = null;
        PrintWriter pw = null;
        BufferedReader br = null;
        String firstname = "";
        String line = "";
        try {
            ss=new ServerSocket(port);
            System.out.println("Server listen port "+ port);
            sc = ss.accept();
            pw = new PrintWriter(sc.getOutputStream(),true);
            br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
            pw.println("Your name, please: ");
            boolean f = true;
            int n = 0;
            do{
                System.out.println("Loop: "+n++);
                line = br.readLine();
                if(f){
                   pw.println("Hello, "+line);
                   firstname = line;
                   f=false;
                }else{
                   pw.println(firstname+" write: "+line);
                }
                if(line.equals("stop")){working=false;}
                //Thread.sleep(2000);
            }while(working);
        } finally{
            if(br != null) br.close();
            if(pw != null) pw.close();
            if(sc != null) sc.close();
        }
    }
    
}
