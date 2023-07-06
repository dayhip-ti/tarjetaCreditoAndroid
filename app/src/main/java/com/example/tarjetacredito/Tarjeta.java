package com.example.tarjetacredito;

public class Tarjeta {
    private int numero;
    private String titular;
    private int expira;
    private  String tipo;

    //Constructor Vacio
    public Tarjeta() {
    }

    //Constructor lleno
    public Tarjeta(int numero, String titular, int expira, String tipo) {
        this.numero = numero;
        this.titular = titular.toUpperCase();
        this.expira = expira;
        this.tipo = tipo;
    }

    /// Getters y Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular.toUpperCase();
    }

    public int getExpira(int i) {
        return expira;
    }

    public void setExpira(int expira) {
        this.expira = expira;
    }

    public int getExpira() {
        return expira;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
