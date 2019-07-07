/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import korisnikServer.KorisnikClass;

/**
 *
 * @author Luka
 */
public class ServerClass {
    static ServerNitClass klijenti[] = new ServerNitClass[20];
    public static LinkedList<KorisnikClass> korisnici = new LinkedList<KorisnikClass>();
    
    public static void napraviKorisnike(){
        KorisnikClass k = new KorisnikClass(0,"djole", "djole", "djole");
        korisnici.add(k);
        KorisnikClass k1 = new KorisnikClass(1, "djole", "djole","djole");
        korisnici.add(k1);
    }
    
    public static void main(String[] args) {
           int port = 22272;
           Socket klijentSoket = null;
           napraviKorisnike();
        try {
            ServerSocket serverSoket = new ServerSocket(port);
            while(true){
                System.out.println("Cekanje klijenta!");
                klijentSoket = serverSoket.accept();
                System.out.println("Klijent konektovan");
                for(int i=0;i<klijenti.length;i++){
                    if(klijenti[i] == null){
                        klijenti[i] = new ServerNitClass(klijentSoket,korisnici);
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
