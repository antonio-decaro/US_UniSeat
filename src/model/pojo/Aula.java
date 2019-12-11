package model.pojo;

import java.util.Set;

/**
 * Questa classe modella il concetto di "Aula" all'interno del sistema.
 * @author De Caro Antonio
 * @version 0.1
 * ciao
 * */
public class Aula {

    public Aula(){}

    public Aula(int id, String nome, int nPostiLiberi, int nPosti, String disponibilita) {
        this.id = id;
        this.nome = nome;
        this.nPostiLiberi = nPostiLiberi;
        this.nPosti = nPosti;
        this.disponibilita = disponibilita;
        this.edificio = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPostiLiberi() {
        return nPostiLiberi;
    }

    public void setPostiLiberi(int nPostiLiberi) {
        this.nPostiLiberi = nPostiLiberi;
    }

    public int getPosti() {
        return nPosti;
    }

    public void setPosti(int nPosti) {
        this.nPosti = nPosti;
    }

    public String getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(String disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public Set<Servizio> getServizi() {
        return servizi;
    }

    public void setServizi(Set<Servizio> servizi) {
        this.servizi = servizi;
    }

    private int id;
    private String nome;
    private int nPostiLiberi;
    private int nPosti;
    private String disponibilita;
    private Edificio edificio;
    private Set<Servizio> servizi;
}
