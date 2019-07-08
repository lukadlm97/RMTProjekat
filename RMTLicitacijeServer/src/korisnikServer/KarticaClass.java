/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnikServer;

/**
 *
 * @author Team
 */


public class KarticaClass {
    public enum TipKartice{
        Kreditna,
        Debitna
    }
    public int IDKartice;
    public TipKartice tipKartice;
    public double iznos;

    public KarticaClass(int IDKartice, TipKartice tipKartice, double iznos) {
        this.IDKartice = IDKartice;
        this.tipKartice = tipKartice;
        this.iznos = 2000;
    }

    public KarticaClass() {
    }

    public int getIDKartice() {
        return IDKartice;
    }

    public TipKartice getTipKartice() {
        return tipKartice;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIDKartice(int IDKartice) {
        this.IDKartice = IDKartice;
    }

    public void setTipKartice(TipKartice tipKartice) {
        this.tipKartice = tipKartice;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
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
        final KarticaClass other = (KarticaClass) obj;
        if (this.IDKartice != other.IDKartice) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "IDKartice: "+IDKartice+"\tTip kartice: "+tipKartice+"\tIznos:"+iznos; //To change body of generated methods, choose Tools | Templates.
    }
    

    
}
