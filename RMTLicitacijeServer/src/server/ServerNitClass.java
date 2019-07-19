/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileWriter;
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
    int brojKorisnika = 0;

    ServerNitClass(Socket klijentSoket, LinkedList<KorisnikClass> korisnici) {
        soketZaKomunikaciju = klijentSoket;
        registrovaniKorisnici = korisnici;
        brojKorisnika = korisnici.size() + 1;
    }

    private boolean validacijaUsername(String usernameTemp) {
         if(usernameTemp.length() < 4) {
        	return false;
        }
        return true;
    
    }

    private boolean validacijaPassword(String passwordTemp) {
        if(passwordTemp.length() < 8 || passwordTemp.toLowerCase().equals(passwordTemp)) {
    		return false;
    	}
    	
    	for(int i = 0; i < passwordTemp.length(); i++) {
    		if(passwordTemp.charAt(i) >= 48 && passwordTemp.charAt(i) <= 57)
    			return true;
    	}
    	
    	return false;
    }

    private void dodavanjeNovogKorisnikaUFile() {
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try {
            FileWriter upisivac = new FileWriter("files/korisnici.json");
            
            String korisnikUString = gson.toJson(registrovaniKorisnici);
             
            upisivac.write(korisnikUString);
           
            
            upisivac.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    




    
    public enum izborLogMeni{
        Prijava,
        Registracija,
        Izlaz   
    }
    public enum izborPrijavljenMeni{
        Razgledanje,
        Licitacija,
        Dopuna,
        Stanje,
        Istorija,
        Odjava,
        Nedefinisano
    }
    

    @Override
    public void run() {
        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());
            //obezbedi da moze meni sa brojem
            izborLogMeni izbor;
            boolean prijavljen = false;
            do{
                izbor = izborLogMeni.Izlaz;
                prijavljivanjeKorisnikaMeni();
                String izborTemp = ulazniTokOdKlijenta.readLine();
                try{
                    izbor=izborLogMeni.valueOf(izborTemp);
                }catch(Exception e){
                    System.out.println("Unet broj kao izbor");
                }
                if(izbor == izborLogMeni.Registracija || izborTemp.equals("2")){
                    registracijaKorisnika();
                    izbor = izborLogMeni.Registracija;
                    }
                if(izbor == izborLogMeni.Prijava || izborTemp.equals("1")){
                    prijavljen = prijavaKorisnika();
                    izbor = izborLogMeni.Prijava;
                }
                if(izbor == izborLogMeni.Izlaz || izborTemp.equals("3")){
                    izlazniTokKaKlijentu.println("Dovidjenja!");
                    return;
                    }
            }while(!prijavljen);
           // izlazniTokKaKlijentu.println(izbor);
           izborPrijavljenMeni izborPrijavljen;
           boolean odjava = true;
           do{
           izborPrijavljen = izborPrijavljenMeni.Nedefinisano;
           prijavljenKorisnikMeni();
           String izborTemp = ulazniTokOdKlijenta.readLine();
            //try?catch,treba srediti da moze i sa brojem da se bira
            //u sustini, parsirati u int i onda ubaciti dodatni uslov u ifu da moze i broj
           try{
            izborPrijavljen = izborPrijavljenMeni.valueOf(izborTemp);
           }catch(Exception e){
               System.out.println("Unet je broj kao izbor");
           }
           
           if(izborPrijavljen == izborPrijavljenMeni.Stanje || izborTemp.equals("4")){
               proveraStanja();
               izborPrijavljen = izborPrijavljenMeni.Stanje;
           }else if(izborPrijavljen == izborPrijavljenMeni.Dopuna || izborTemp.equals("3")){
               dopunaRacuna();
                izborPrijavljen = izborPrijavljenMeni.Dopuna;
           }else if(izborPrijavljen == izborPrijavljenMeni.Razgledanje || izborTemp.equals("1")){
               izlazniTokKaKlijentu.println("RAZGLEDANJE");
                izborPrijavljen = izborPrijavljenMeni.Razgledanje;
           }else if(izborPrijavljen == izborPrijavljenMeni.Licitacija || izborTemp.equals("2")){
               izlazniTokKaKlijentu.println("LICITACIJA");
                izborPrijavljen = izborPrijavljenMeni.Licitacija;
           }else if(izborPrijavljen == izborPrijavljenMeni.Istorija || izborTemp.equals("5")){
               izlazniTokKaKlijentu.println("ISTORIJA");
                izborPrijavljen = izborPrijavljenMeni.Istorija;
           }else if(izborPrijavljen == izborPrijavljenMeni.Odjava || izborTemp.equals("6")){
               izlazniTokKaKlijentu.println("Dovidjenja!");
               System.out.println(username+" se diskonektovao!");
               return;
           }else{
               izlazniTokKaKlijentu.println("Greska u unosu");
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
                +"\n2.Licitacija"
                + "\n3.Dopuna"
                + "\n4.Stanje"
                + "\n5.Istorija"
                + "\n6.Odjava"
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
          //omoguceno da unosi username, sve dok ne unese od 4+ karaktera
        	username = ulazniTokOdKlijenta.readLine();
        	boolean validanUsername = validacijaUsername(username);
        	while(!validanUsername) {
        		izlazniTokKaKlijentu.println("Username mora imati minimum 4 karaktera. Pokusajte ponovo!");
        		username = ulazniTokOdKlijenta.readLine();
        		validanUsername = validacijaUsername(username);
        	}   
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        izlazniTokKaKlijentu.println("Unesite lozinku koju zelite da koristite: ");
        try {
            //omoguceno da unosi sifru sve dok ne ispuni ogranicenja
            password = ulazniTokOdKlijenta.readLine();
            boolean validanPassword = validacijaPassword(password);
            while(!validanPassword) {
        		izlazniTokKaKlijentu.println("Password mora imati minimum 8 karaktera, barem jedno veliko slovo i barem jednu cifru. Pokusajte ponovo!");
        		password = ulazniTokOdKlijenta.readLine();
        		validanPassword = validacijaPassword(password);
        	} 
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!validacija(imePrezime, username, password)){
            KorisnikClass noviKorisnik = new KorisnikClass(brojKorisnika++, imePrezime, username, password);
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
                 //try?catch
                int IDKartice = Integer.parseInt(brojKartice);
                KarticaClass novaKartica = new KarticaClass(IDKartice,KarticaClass.TipKartice.Debitna,2500);
                for(KorisnikClass k:registrovaniKorisnici){
                    if(noviKorisnik.IDKorisnika == k.IDKorisnika){
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
            dodavanjeNovogKorisnikaUFile();
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
            
            //minimum 4 karaktera
            //validacijaUsername(usernameTemp);
            
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
            
            //minimum 8 karaktera, jedno veliko slovo i cifra
            //validacijaPassword(passwordTemp);
            
            
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
         //try?catch
        IDKartice = Integer.parseInt(tempBrojKartice);
        izlazniTokKaKlijentu.println("Unesite iznos za dopunu: ");
        try {
            tempIznos = ulazniTokOdKlijenta.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        //try?catch
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
