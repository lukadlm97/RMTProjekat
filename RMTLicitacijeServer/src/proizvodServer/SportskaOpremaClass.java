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
public class SportskaOpremaClass extends ProizvodClass{
    public String marka;
    public String model;

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setIDProizvoda(int IDProizvoda) {
        this.IDProizvoda = IDProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    @Override
    public String toString() {
        return "Proizvod: "+IDProizvoda+" Naziv: "+nazivProizvoda+"\t"+"SportskaOprema{" + "marka=" + marka + ", model=" + model + '}';
    }

    
    public String getMarka() {
        return marka;
    }

    public String getModel() {
        return model;
    }

    public int getIDProizvoda() {
        return IDProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public SportskaOpremaClass() {
    }

    public SportskaOpremaClass(String marka, String model, int IDProizvoda, String nazivProizvoda) {
        super(IDProizvoda, nazivProizvoda);
        this.marka = marka;
        this.model = model;
    }

   
    
}
