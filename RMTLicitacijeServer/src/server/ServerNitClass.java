/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import korisnikServer.KorisnikClass;

/**
 *
 * @author Luka
 */
public class ServerNitClass extends Thread{
    BufferedReader ulazniTokOdKlijenta = null;
    PrintStream izlazniTokKaKlijentu = null;
    Socket soketZaKomunikaciju = null;
    LinkedList<KorisnikClass> registrovaniKorisnici = new LinkedList<KorisnikClass>();
    

    ServerNitClass(Socket klijentSoket, LinkedList<KorisnikClass> korisnici) {
        soketZaKomunikaciju = klijentSoket;
        registrovaniKorisnici = korisnici;
    }


    
    public enum izborLogMeni{
        Prijava,
        Registracija,
        Izlaz   
    }

    @Override
    public void run() {
        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());
            izborLogMeni izbor = izborLogMeni.Izlaz;
            boolean prijavljen = false;
            do{
                prijavljivanjeKorisnikaMeni();
                String izborTemp = ulazniTokOdKlijenta.readLine();
                izbor=izborLogMeni.valueOf(izborTemp);
                if(izbor == izborLogMeni.Izlaz){
                    izlazniTokKaKlijentu.println("Dovidjenja!");
                    return;
                    }
                if(izbor == izborLogMeni.Registracija){
                    registracijaKorisnika();
                    }
                if(izbor == izborLogMeni.Prijava){
                    prijavljen = prijavaKorisnika();
                }
            }while(!prijavljen);
           // izlazniTokKaKlijentu.println(izbor);    
            }   catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void prijavljivanjeKorisnikaMeni(){
        izlazniTokKaKlijentu.println("Unesite:"
                + "\n1.Prijava"
                + "\n2.Registracija"
                + "\n3.Izlaz"
                + "\nUnesite Vas izbor:");
    }
    public void registracijaKorisnika(){
        String imePrezime = "prazan";
        String username = "prazan";
        String password = "prazan";
        izlazniTokKaKlijentu.println("Unesite ime i prezime: ");
        try {
             imePrezime = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        izlazniTokKaKlijentu.println("Unesite korisnicko ime koje zelite da koristite: ");
        try {
            username = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        izlazniTokKaKlijentu.println("Unesite lozinku koju zelite da koristite: ");
        try {
            password = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!validacija(imePrezime, username, password)){
            KorisnikClass noviKorisnik = new KorisnikClass(2, imePrezime, username, password);
            registrovaniKorisnici.add(noviKorisnik);
            izlazniTokKaKlijentu.println("Uspesno ste se registrovali!");
        }else{
            izlazniTokKaKlijentu.println("Neuspesna registracija!");
        }
    }
    public boolean validacija(String str1,String str2,String str3){
        return str1.equals("prazan") || str2.equals("prazan") || str3.equals("prazan");
    }
     private boolean prijavaKorisnika() {
         String usernameTemp = null;
         String password = null;
         String passwordTemp = null;
         boolean kontrolaPrijave = true;
        izlazniTokKaKlijentu.println("Unesite korisnicko ime koje koristite: ");
        try {
            usernameTemp = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        kontrolaPrijave = kontrolaPrijave && proveraUsername(usernameTemp);
        if(!kontrolaPrijave)
            return false;
        password = nadjiPassword(usernameTemp);
        izlazniTokKaKlijentu.println("Unesite lozniku koju koristite: ");
        try {
            passwordTemp = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        kontrolaPrijave = kontrolaPrijave && password.equals(passwordTemp);
        if(!kontrolaPrijave){
            izlazniTokKaKlijentu.println("Uneli ste pogresu lozinku. Pokusajte ponovo:  ");
            passwordTemp = null;
            kontrolaPrijave = true;
             try {
                 passwordTemp = ulazniTokOdKlijenta.readLine();
             } catch (IOException ex) {
                 Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        kontrolaPrijave = kontrolaPrijave && password.equals(passwordTemp);
        if(!kontrolaPrijave){
             izlazniTokKaKlijentu.println("Neuspesna prijava!");
             return false;
        }
        izlazniTokKaKlijentu.println("Uspesna prijava!");
        return true;
     }
      private String nadjiPassword(String username) {
          for(KorisnikClass k : registrovaniKorisnici){
              if(k.getUsername().equals(username))
                  return k.getPassword();
          }
          return null;
      }
     private boolean proveraUsername(String username) {
         for(KorisnikClass k : registrovaniKorisnici){
             if(k.getUsername().equals(username))
                 return true;
         }
         return false;
     }
}
