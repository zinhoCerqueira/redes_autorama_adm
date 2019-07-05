package model;

import java.io.Serializable;

public class Pista implements Serializable{
    String nome;
    String recorde;
    String nomeRecorde;
    String equipeRecorde;

    public Pista(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRecorde() {
        return recorde;
    }

    public void setRecorde(String recorde) {
        this.recorde = recorde;
    }

    public String getNomeRecorde() {
        return nomeRecorde;
    }

    public void setNomeRecorde(String nomeRecorde) {
        this.nomeRecorde = nomeRecorde;
    }

    public String getEquipeRecorde() {
        return equipeRecorde;
    }

    public void setEquipeRecorde(String equipeRecorde) {
        this.equipeRecorde = equipeRecorde;
    }
    
    
}
