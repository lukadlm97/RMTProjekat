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
public class KozmetikaClass extends ProizvodClass{
    public enum Pol{
    Muski,
    Zenski}
    public String proizvodjac;
    public Pol namenjenaZa;

    @Override
    public String toString() {
        return "Proizvod: "+IDProizvoda+" Naziv: "+nazivProizvoda+"KozmetikaClass{" + "proizvodjac=" + proizvodjac + ", namenjenaZa=" + namenjenaZa + '}';
    }

    public void setProizvodjac(String proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    public void setNamenjenaZa(Pol namenjenaZa) {
        this.namenjenaZa = namenjenaZa;
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

    public Pol getNamenjenaZa() {
        return namenjenaZa;
    }

    public int getIDProizvoda() {
        return IDProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public KozmetikaClass() {
    }

    public KozmetikaClass(String proizvodjac, Pol namenjenaZa, int IDProizvoda, String nazivProizvoda) {
        super(IDProizvoda, nazivProizvoda);
        this.proizvodjac = proizvodjac;
        this.namenjenaZa = namenjenaZa;
    }

 
    
}
