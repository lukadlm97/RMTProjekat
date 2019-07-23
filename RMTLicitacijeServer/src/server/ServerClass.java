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
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
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
import proizvodServer.KnjigaClass;
import proizvodServer.KozmetikaClass;
import proizvodServer.KucniAparatiClass;
import proizvodServer.MuzickaOpremaClass;
import proizvodServer.ProizvodClass;
import proizvodServer.SportskaOpremaClass;
import proizvodServer.StavkaProizvodaClass;

/**
 *
 * @author Luka
 */
public class ServerClass {
    static ServerNitClass klijenti[] = new ServerNitClass[20];
    public static LinkedList<KorisnikClass> korisnici = new LinkedList<KorisnikClass>();
    public static LinkedList<StavkaProizvodaClass> proizvodiZaLicitaciju = new LinkedList<StavkaProizvodaClass>();
    
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
    
    public static void ucitajProizvode(){
        java.lang.reflect.Type listType = new TypeToken<LinkedList<StavkaProizvodaClass>>(){}.getType();
        
        RuntimeTypeAdapterFactory<ProizvodClass> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(ProizvodClass.class,"type")
                .registerSubtype(KnjigaClass.class, "knjigaClass")
                .registerSubtype(SportskaOpremaClass.class, "sportskaOpremaClass")
                .registerSubtype(KozmetikaClass.class, "kozmetikaClass")
                .registerSubtype(MuzickaOpremaClass.class, "muzickaOpremaClass")
                .registerSubtype(KucniAparatiClass.class, "kucniAparatiClass");
        
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
       
        
        try {
            FileReader citac = new FileReader("files/proizvodi.json");
            java.lang.reflect.Type type = new TypeToken<LinkedList<StavkaProizvodaClass>>(){}.getType();
            
            LinkedList<StavkaProizvodaClass> prozivodiTemp = gson.fromJson(citac, listType);
            
            for (StavkaProizvodaClass stavkaProizvodaClass : prozivodiTemp) {
                if(stavkaProizvodaClass.getProizvod() instanceof KnjigaClass){
                    KnjigaClass knjiga =(KnjigaClass) stavkaProizvodaClass.getProizvod();
                    stavkaProizvodaClass.setProizvod(knjiga);
                    System.out.println(knjiga.toString());
                }else if(stavkaProizvodaClass.getProizvod() instanceof KozmetikaClass){
                    KozmetikaClass kozmetika = (KozmetikaClass) stavkaProizvodaClass.getProizvod();
                    stavkaProizvodaClass.setProizvod(kozmetika);
                   System.out.println(kozmetika.toString());
                }else if(stavkaProizvodaClass.getProizvod() instanceof KucniAparatiClass){
                    KucniAparatiClass aparati = (KucniAparatiClass) stavkaProizvodaClass.getProizvod();
                    stavkaProizvodaClass.setProizvod(aparati);
                }else if(stavkaProizvodaClass.getProizvod() instanceof MuzickaOpremaClass){
                    MuzickaOpremaClass oprema = (MuzickaOpremaClass) stavkaProizvodaClass.getProizvod();
                    stavkaProizvodaClass.setProizvod(oprema);
                }else if(stavkaProizvodaClass.getProizvod() instanceof SportskaOpremaClass){
                    SportskaOpremaClass sport = (SportskaOpremaClass) stavkaProizvodaClass.getProizvod();
                    stavkaProizvodaClass.setProizvod(sport);
                }else{
                    continue;
                }
            }
            proizvodiZaLicitaciju = prozivodiTemp;
            
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
           ucitajProizvode();
        try {
            ServerSocket serverSoket = new ServerSocket(port);
            while(true){
                System.out.println("Cekanje klijenta!");
                klijentSoket = serverSoket.accept();
                System.out.println("Klijent konektovan");
                for(int i=0;i<klijenti.length;i++){
                    if(klijenti[i] == null){
                        klijenti[i] = new ServerNitClass(klijentSoket,korisnici,proizvodiZaLicitaciju);
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
