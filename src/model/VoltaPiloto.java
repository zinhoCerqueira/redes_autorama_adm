package model;

import java.io.Serializable;
import java.util.ArrayList;

public class VoltaPiloto implements Serializable, Comparable{
    Piloto piloto;
    ArrayList voltas;
    double melhorVoltaClassifica;
    int volta;

    public VoltaPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public ArrayList getVoltas() {
        return voltas;
    }

    public void setVoltas(ArrayList voltas) {
        this.voltas = voltas;
    }
    

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public double getMelhorVoltaClassifica() {
        return melhorVoltaClassifica;
    }

    public void setMelhorVoltaClassifica(double melhorVoltaClassifica) {
        this.melhorVoltaClassifica = melhorVoltaClassifica;
    }

    @Override
    public int compareTo(Object o) {
        VoltaPiloto x = (VoltaPiloto) o;
        if (this.contatempo() > x.contatempo()) {
            return -1;
        }
        if (this.contatempo() < x.contatempo()) {
            return -1;
        }
        return 0;
    }
    
    private double contatempo() {
        ArrayList x = this.getVoltas();
        double tempo = 0;
        for(Object i : x){
            tempo = tempo + (double)i;
        }
        return tempo;
    }
    
    
    
}
