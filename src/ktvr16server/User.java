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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melnikov
 */
public class User implements Runnable{
    Socket sc;
    PrintWriter pw;
    BufferedReader br;
    String line="";
    Work work;
    String name="";
    boolean f=true;

    public User(Socket socket, Work work) throws IOException {
        this.sc = socket;
        this.work = work;
        this.pw = new PrintWriter(this.sc.getOutputStream(),true);
        this.br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        Thread thUser = new Thread(this);
        thUser.start();
        try {
            thUser.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "User дал исключение", ex);
        }
    }

    @Override
    public void run() {
        pw.println("Your name, please: ");
        // name = br.readLine();
        work.add(this);
    }
    
}
