/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luka
 */
public class ServerClass {
    static ServerNitClass klijenti[] = new ServerNitClass[20];
    
    public static void main(String[] args) {
           int port = 22272;
           Socket klijentSoket = null;
           
        try {
            ServerSocket serverSoket = new ServerSocket(port);
            while(true){
                System.out.println("Cekanje klijenta!");
                klijentSoket = serverSoket.accept();
                System.out.println("Klijent konektovan");
                for(int i=0;i<klijenti.length;i++){
                    if(klijenti[i] == null){
                        klijenti[i] = new ServerNitClass(klijentSoket);
                        klijenti[i].start();
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
           
           
          
    }
}
