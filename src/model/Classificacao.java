package model;

import java.io.Serializable;
import java.util.List;

public class Classificacao implements Serializable{
    String nome;
    List <VoltaPiloto> voltaPiloto;
    String melhorTempo;
    Piloto donoMelhorTempo;

    public Classificacao(String nome, List<VoltaPiloto> pilotosCorrida) {
        this.nome = nome;
        for(VoltaPiloto x: pilotosCorrida){
            voltaPiloto.add(x);
        }       
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VoltaPiloto> getPilotos() {
        return voltaPiloto;
    }

    public void setPilotos(List<VoltaPiloto> pilotos) {
        this.voltaPiloto = pilotos;
    }

    public String getMelhorTempo() {
        return melhorTempo;
    }

    public void setMelhorTempo(String melhorTempo) {
        this.melhorTempo = melhorTempo;
    }

    public Piloto getDonoMelhorTempo() {
        return donoMelhorTempo;
    }

    public void setDonoMelhorTempo(Piloto donoMelhorTempo) {
        this.donoMelhorTempo = donoMelhorTempo;
    } 
    
}
