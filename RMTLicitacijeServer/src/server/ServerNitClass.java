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

    @Override
    public void run() {
        try {
            ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
            izlazniTokKaKlijentu = new PrintStream(soketZaKomunikaciju.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerNitClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
