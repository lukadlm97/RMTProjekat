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
public class KnjigaClass extends ProizvodClass{
    public String autor;
    public String izdavac;
    public int godinaIzdanja;

    @Override
    public String toString() {
        return "Proizvod: "+IDProizvoda+" Naziv: "+nazivProizvoda+"\n"+"KnjigaClass{" + "autor=" + autor + ", izdavac=" + izdavac + ", godinaIzdanja=" + godinaIzdanja + '}';
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setIzdavac(String izdavac) {
        this.izdavac = izdavac;
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

    public String getAutor() {
        return autor;
    }

    public String getIzdavac() {
        return izdavac;
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

    public KnjigaClass() {
    }

    public KnjigaClass(String autor, String izdavac, int godinaIzdanja, int IDProizvoda, String nazivProizvoda) {
        super(IDProizvoda, nazivProizvoda);
        this.autor = autor;
        this.izdavac = izdavac;
        this.godinaIzdanja = godinaIzdanja;
    }
    
}
