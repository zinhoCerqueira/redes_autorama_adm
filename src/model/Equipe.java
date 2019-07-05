package model;

import java.io.Serializable;

public class Equipe implements Serializable{
    String nome; 

    public Equipe(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
     
}
