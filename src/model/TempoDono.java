
package model;

import java.io.Serializable;

public class TempoDono implements Serializable{
    Double tempo;
    String dono;

    public TempoDono(Double tempo, String dono) {
        this.tempo = tempo;
        this.dono = dono;
    }

    public Double getTempo() {
        return tempo;
    }

    public void setTempo(Double tempo) {
        this.tempo = tempo;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }
    
    
}
