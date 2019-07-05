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

    public ServerNitClass(Socket klijentSoket) {
        this.soketZaKomunikaciju = klijentSoket;
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
            prijavljivanjeKorisnika();
            String izborTemp = ulazniTokOdKlijenta.readLine();
            izbor=izborLogMeni.valueOf(izborTemp);
            if(izbor == izborLogMeni.Izlaz){
                izlazniTokKaKlijentu.println("Dovidjenja!");
                return;
            }
            if(izbor == izborLogMeni.Registracija){
                registracijaKorisnika();
            }
           // izlazniTokKaKlijentu.println(izbor);
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void prijavljivanjeKorisnika(){
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
            KorisnikClass noviKorisnik = new KorisnikClass(1, imePrezime, username, password);
            izlazniTokKaKlijentu.println("Uspesno ste se registrovali!");
        }
    }
    public boolean validacija(String str1,String str2,String str3){
        return str1.equals("prazan") || str2.equals("prazan") || str3.equals("prazan");
    }
}
