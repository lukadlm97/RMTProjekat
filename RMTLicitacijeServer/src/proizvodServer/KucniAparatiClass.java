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
public class KucniAparatiClass extends ProizvodClass{
    public String proizvodjac;
    public int godinaProizvodnje;

    @Override
    public String toString() {
        return "Proizvod: "+IDProizvoda+" Naziv: "+nazivProizvoda+"KucniAparatiClass{" + "proizvodjac=" + proizvodjac + ", godinaProizvodnje=" + godinaProizvodnje + '}';
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public void setGodinaProizvodnje(int godinaProizvodnje) {
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public void setIDProizvoda(int IDProizvoda) {
        this.IDProizvoda = IDProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    public KucniAparatiClass(String proizvodjac, int godinaProizvodnje, int IDProizvoda, String nazivProizvoda) {
        super(IDProizvoda, nazivProizvoda);
        this.proizvodjac = proizvodjac;
        this.godinaProizvodnje = godinaProizvodnje;
    }

    public KucniAparatiClass() {
    }

    public String getProizvodjac() {
        return proizvodjac;
    }

    public int getGodinaProizvodnje() {
        return godinaProizvodnje;
    }

    public int getIDProizvoda() {
        return IDProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }
    
}
