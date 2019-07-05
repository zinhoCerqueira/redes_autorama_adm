package model;

import java.io.Serializable;

public class Carro implements Serializable{
    String cod;
    String cor;

    public Carro(String cod, String cor) {
        this.cod = cod;
        this.cor = cor;
    }
    
    public Carro(String cod){
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
 
}
