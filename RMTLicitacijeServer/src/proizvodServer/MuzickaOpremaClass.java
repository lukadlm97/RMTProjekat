/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proizvodServer;

/**
 *
 * @author Luka
 */
public class MuzickaOpremaClass extends ProizvodClass{
    public enum KategorijaMuzickeOpreme{
        MuzickaPloca,
        MuzickiCD,
        MuzickiInstrument,
        MuzickiAlbum
    }
    public String proizvodjac;
    public KategorijaMuzickeOpreme kategorijaOpreme;
    public int godinaIzdanja;

    @Override
    public String toString() {
        return "Proizvod: "+IDProizvoda+" Naziv: "+nazivProizvoda+"\n"+"MuzickaOpremaClass{" + "proizvodjac=" + proizvodjac + ", kategorijaOpreme=" + kategorijaOpreme + ", godinaIzdanja=" + godinaIzdanja + '}';
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public void setKategorijaOpreme(KategorijaMuzickeOpreme kategorijaOpreme) {
        this.kategorijaOpreme = kategorijaOpreme;
    }

    public void setGodinaIzdanja(int godinaIzdanja) {
        this.godinaIzdanja = godinaIzdanja;
    }

    public void setIDProizvoda(int IDProizvoda) {
        this.IDProizvoda = IDProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public KategorijaMuzickeOpreme getKategorijaOpreme() {
        return kategorijaOpreme;
    }

    public int getGodinaIzdanja() {
        return godinaIzdanja;
    }

    public int getIDProizvoda() {
        return IDProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public MuzickaOpremaClass() {
    }

    public MuzickaOpremaClass(String proizvodjac, KategorijaMuzickeOpreme kategorijaOpreme, int godinaIzdanja, int IDProizvoda, String nazivProizvoda) {
        super(IDProizvoda, nazivProizvoda);
        this.proizvodjac = proizvodjac;
        this.kategorijaOpreme = kategorijaOpreme;
        this.godinaIzdanja = godinaIzdanja;
    }
    
}
