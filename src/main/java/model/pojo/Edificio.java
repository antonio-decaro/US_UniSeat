package model.pojo;

import java.util.Set;
import java.util.TreeSet;

/**
 * Questa classe modella il concetto di "Edificio" all'interno del sistema.
 *
 * @author De Caro Antonio
 * @version 0.1
 */
public class Edificio {

    private String nome;
    private Set<Aula> aule;

    {
        this.aule = new TreeSet<Aula>();
    }

    public Edificio() {
    }

    public Edificio(String nome) {
        this();
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Aula> getAule() {
        return aule;
    }

    public void setAule(Set<Aula> aule) {
        this.aule = aule;
    }

    public void addAula(Aula aula){
        this.aule.add(aula);
    }

    public void removeAula(Aula aula){
        this.aule.remove(aula);
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "nome='" + nome + '\'' +
                ", aule=" + aule +
                '}';
    }
}
