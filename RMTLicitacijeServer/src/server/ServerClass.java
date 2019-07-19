/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect.Type;
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
    
    public static void ucitajKorisnike(){
       Gson gson = new GsonBuilder().setPrettyPrinting().create();
       
        try {
            FileReader citac = new FileReader("files/korisnici.json");
            java.lang.reflect.Type type = new TypeToken<LinkedList<KorisnikClass>>(){}.getType();
            
            LinkedList<KorisnikClass> korisniciTemp = gson.fromJson(citac, type);
            
            korisnici = korisniciTemp;
            
            citac.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
           int port = 22272;
           Socket klijentSoket = null;
           ucitajKorisnike();
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
