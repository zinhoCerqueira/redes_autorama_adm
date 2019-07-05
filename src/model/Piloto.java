package model;

import java.io.Serializable;

public class Piloto implements Serializable{
    String nome;
    Equipe equipe;
    Carro carro;
    String num;

    public Piloto(String nome, Equipe equipe, String num) {
        this.nome = nome;
        this.equipe = equipe;
        this.num = num;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
    
    
    
    
}
