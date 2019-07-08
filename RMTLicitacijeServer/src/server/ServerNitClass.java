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
import korisnikServer.KarticaClass;
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
    LinkedList<KorisnikClass> onlineKorisnici = new LinkedList<KorisnikClass>();
    String username = null;

    ServerNitClass(Socket klijentSoket, LinkedList<KorisnikClass> korisnici) {
        soketZaKomunikaciju = klijentSoket;
        registrovaniKorisnici = korisnici;
    }

    




    
    public enum izborLogMeni{
        Prijava,
        Registracija,
        Izlaz   
    }
    public enum izborPrijavljenMeni{
        Razgledanje,
        Dopuna,
        Stanje,
        Istorija,
        Odjava
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
           izborPrijavljenMeni izborPrijavljen = izborPrijavljenMeni.Odajava;
           boolean odjava = true;
           do{
           prijavljenKorisnikMeni();
           String izborTemp = ulazniTokOdKlijenta.readLine();
           izborPrijavljen = izborPrijavljenMeni.valueOf(izborTemp);
           if(izborPrijavljen == izborPrijavljenMeni.Stanje){
               proveraStanja();
           }
           if(izborPrijavljen == izborPrijavljenMeni.Odajava){
               izlazniTokKaKlijentu.println("Dovidjenja!");
               System.out.println(username+" se diskonektovao!");
               return;
           }
           if(izborPrijavljen == izborPrijavljenMeni.Dopuna){
               dopunaRacuna();
           }
           }while(odjava);
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
    
    public void prijavljenKorisnikMeni(){
        izlazniTokKaKlijentu.println("Unesite:"
                + "\n1.Razgledanje"
                + "\n2.Dopuna"
                + "\n3.Stanje"
                + "\n4.Istorija"
                + "\n5.Odjava"
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
            String brojKartice = null;
             boolean temp = false;
            do{
            izlazniTokKaKlijentu.println("Unesite broj kartice koju zelite da koristite: ");
                try {
                    brojKartice = ulazniTokOdKlijenta.readLine();
                } catch (IOException ex) {
                    Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                int IDKartice = Integer.parseInt(brojKartice);
                KarticaClass novaKartica = new KarticaClass(IDKartice,KarticaClass.TipKartice.Debitna,2500);
                for(KorisnikClass k:registrovaniKorisnici){
                    if(noviKorisnik.equals(k)){
                        k.dodajKarticu(novaKartica);
                    }
                }
            izlazniTokKaKlijentu.println("Jos kartica(DA/NE): ");
           
                try {
                    String izbor = ulazniTokOdKlijenta.readLine();
                    if(izbor.equals("da") || izbor.equals("Da") || izbor.equals("DA") || izbor.equals("dA")){
                        temp = true;
                    }else{
                        break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }while(temp);
            System.out.println(noviKorisnik.getUsername()+" se registrovao!");
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
         KorisnikClass k = null;
         for(KorisnikClass korisnik : registrovaniKorisnici){
             if(korisnik.getUsername().equals(usernameTemp)){
                 k = korisnik;
                 break;
             }
         }
        onlineKorisnici.add(k);
         System.out.println(k.getImePrezimeKorisnika()+" se konektovao!");
         username = k.getUsername();
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
     private void proveraStanja() {
         izlazniTokKaKlijentu.println("IDKartice"+"\t"+"Iznos");
         for(KorisnikClass k : registrovaniKorisnici){
             if(k.username.equals(username)){
                 for(KarticaClass kartica:k.getKarticeKorisnika()){
                     izlazniTokKaKlijentu.println(kartica.getIDKartice()+"\t"+kartica.getIznos());
                 }
             }
         }
     }
     
     private void dopunaRacuna() {
         String tempBrojKartice = null;
         String tempIznos = null;
         int IDKartice;
         double iznos;
         izlazniTokKaKlijentu.println("Unesite broj kartice: ");
        try {
            tempBrojKartice = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        IDKartice = Integer.parseInt(tempBrojKartice);
        izlazniTokKaKlijentu.println("Unesite iznos za dopunu: ");
        try {
            tempIznos = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        iznos = Double.parseDouble(tempIznos);
        for(KorisnikClass korisnik:registrovaniKorisnici){
            if(korisnik.getUsername().equals(username)){
                for(KarticaClass kartice:korisnik.getKarticeKorisnika()){
                    if(kartice.getIDKartice() == IDKartice){
                        kartice.setIznos(kartice.getIznos()+iznos);
                        return;
                    }
                }
            }
        }
        izlazniTokKaKlijentu.println("Neuspesna dopuna!");
     }
}
