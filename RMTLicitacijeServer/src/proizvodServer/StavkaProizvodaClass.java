/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proizvodServer;

import java.util.LinkedList;
import korisnikServer.KorisnikClass;

/**
 *
 * @author Luka
 */
public class StavkaProizvodaClass {
    public ProizvodClass proizvod;
    public String vlasnik;
    public double cena;
    public LinkedList<KorisnikClass> zainteresovaniKorisnici = new LinkedList<KorisnikClass>();

    @Override
    public String toString() {
        return "StavkaProizvodaClass{" + "proizvod=" + proizvod + ", vlasnik=" + vlasnik + ", cena=" + cena + '}';
    }

    public void setZainteresovaniKorisnici(LinkedList<KorisnikClass> zainteresovaniKorisnici) {
        this.zainteresovaniKorisnici = zainteresovaniKorisnici;
    }

    public LinkedList<KorisnikClass> getZainteresovaniKorisnici() {
        return zainteresovaniKorisnici;
    }

    public void setProizvod(ProizvodClass proizvod) {
        this.proizvod = proizvod;
    }

    public void setVlasnik(String vlasnik) {
        this.vlasnik = vlasnik;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public ProizvodClass getProizvod() {
        return proizvod;
    }

    public String getVlasnik() {
        return vlasnik;
    }

    public double getCena() {
        return cena;
    }

    public StavkaProizvodaClass(ProizvodClass proizvod, String vlasnik, double cena,LinkedList<KorisnikClass> zainteresovani) {
        this.proizvod = proizvod;
        this.vlasnik = vlasnik;
        this.cena = cena;
        this.zainteresovaniKorisnici = zainteresovani;
    }
}
