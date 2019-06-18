/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnikServer;

import java.util.LinkedList;
import korisnikServer.KarticaClass.TipKartice;

/**
 *
 * @author Team
 */
public class KorisnikClass {
    public int IDKorisnika;
    public String imePrezimeKorisnika;
    public String username;
    public String password;
    public LinkedList<KarticaClass> karticeKorisnika = new LinkedList<KarticaClass>();

    public KorisnikClass(int IDKorisnika, String imePrezimeKorisnika, String username, String password) {
        this.IDKorisnika = IDKorisnika;
        this.imePrezimeKorisnika = imePrezimeKorisnika;
        this.username = username;
        this.password = password;
    }

    public int getIDKorisnika() {
        return IDKorisnika;
    }

    public String getImePrezimeKorisnika() {
        return imePrezimeKorisnika;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<KarticaClass> getKarticeKorisnika() {
        return karticeKorisnika;
    }

    public void setIDKorisnika(int IDKorisnika) {
        this.IDKorisnika = IDKorisnika;
    }

    public void setImePrezimeKorisnika(String imePrezimeKorisnika) {
        this.imePrezimeKorisnika = imePrezimeKorisnika;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setKarticeKorisnika(LinkedList<KarticaClass> karticeKorisnika) {
        this.karticeKorisnika = karticeKorisnika;
    }
    
    public void dodajKarticu(KarticaClass kartica){
        karticeKorisnika.add(kartica);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KorisnikClass other = (KorisnikClass) obj;
        if (this.IDKorisnika != other.IDKorisnika) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KorisnikClass{" + "IDKorisnika=" + IDKorisnika + ", imePrezimeKorisnika=" + imePrezimeKorisnika + ", username=" + username + ", password=" + password + ", karticeKorisnika=" + karticeKorisnika + '}';
    }
    
    public static void main(String[] args) {
           KorisnikClass k = new KorisnikClass(0, "Mika", "m", "a");
           KarticaClass kr = new KarticaClass(0,TipKartice.Debitna,0.0);
           k.dodajKarticu(kr);
           System.out.println(k.toString());
    }
    
    
    
}
