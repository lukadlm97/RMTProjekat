/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luka
 */
public class KlijentClass implements Runnable{

    
    public  static Socket soketZaKomunikaciju = null;
    public static PrintStream izlazniTokKaServeru = null;
    public static BufferedReader ulazniTokOdServera = null;
    public static BufferedReader ulazKonzola = null;
    
    public  static boolean kraj = false;
    
    public static void main(String[] args) {
        int port = 22272;
        String[] podaci = new String[1000];
        
        try {
            soketZaKomunikaciju = new Socket("localhost",port);
            ulazKonzola = new BufferedReader(new InputStreamReader(System.in));
            izlazniTokKaServeru = new PrintStream(soketZaKomunikaciju.getOutputStream());	
            ulazniTokOdServera = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            new Thread(new KlijentClass()).start();
            
            while (kraj != true) {                
                izlazniTokKaServeru.println(ulazKonzola.readLine());
            }
            soketZaKomunikaciju.close();
        } catch (IOException ex) {
            Logger.getLogger(KlijentClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        String linijaOdServera;
        try {
            while ((linijaOdServera = ulazniTokOdServera.readLine()) != null ) {
                if(linijaOdServera.contains("Dovidjenja!")){
                    System.out.println("Dovidjenja!");
                    break;
                }
                System.out.println(linijaOdServera);
            }
        } catch (IOException ex) {
            Logger.getLogger(KlijentClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
