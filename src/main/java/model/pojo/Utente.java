package model.pojo;

import org.jetbrains.annotations.NotNull;

/**
 * Questa classe modella il concetto di "Utente" all'interno del sistema.
 *
 * @author De Caro Antonio
 * @version 0.1
 * @inv il campo password deve sempre essere codificato in SHA_12
 * @see java.security.MessageDigest
 */
public class Utente {

    private String email;
    private String nome;
    private String cognome;
    private String password;
    private TipoUtente utente;

    public Utente() {
    }

    public Utente(@NotNull String email, @NotNull String nome, @NotNull String cognome,
                  @NotNull String password, @NotNull TipoUtente utente) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.utente = utente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(@NotNull String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(@NotNull String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(@NotNull TipoUtente utente) {
        this.utente = utente;
    }
}
