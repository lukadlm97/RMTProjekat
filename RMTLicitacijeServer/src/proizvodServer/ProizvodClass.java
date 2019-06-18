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
public class ProizvodClass {
    public int IDProizvoda;
    public String nazivProizvoda;

    @Override
    public String toString() {
        return "Proizvod{" + "IDProizvoda=" + IDProizvoda + ", nazivProizvoda=" + nazivProizvoda + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
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
        final ProizvodClass other = (ProizvodClass) obj;
        if (this.IDProizvoda != other.IDProizvoda) {
            return false;
        }
        return true;
    }

    public void setIDProizvoda(int IDProizvoda) {
        this.IDProizvoda = IDProizvoda;
    }

    public void setNazivProizvoda(String nazivProizvoda) {
        this.nazivProizvoda = nazivProizvoda;
    }

    public int getIDProizvoda() {
        return IDProizvoda;
    }

    public String getNazivProizvoda() {
        return nazivProizvoda;
    }

    public ProizvodClass(int IDProizvoda, String nazivProizvoda) {
        this.IDProizvoda = IDProizvoda;
        this.nazivProizvoda = nazivProizvoda;
    }

    public ProizvodClass() {
    }
    
}
