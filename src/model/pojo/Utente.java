package model.pojo;

/**
 * Questa classe modella il concetto di "Utente" all'interno del sistema.
 * @inv il campo password deve sempre essere codificato in SHA_12
 * @author De Caro Antonio
 * @version 0.1
 * @see java.security.MessageDigest
 * */
public class Utente {

    public Utente(){}

    public Utente(String email, String nome, String cognome, String password, TipoUtente utente) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.utente = utente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @throws IllegalArgumentException viene lanciata l'eccezione se la password non è codificata in SHA-256.
     * */
    public void setPassword(String password) {
        if (password.matches("^[A-Fa-f0-9]{64}$")) {
            throw new IllegalArgumentException("La password non è codificata in SHA-256");
        }

        this.password = password;
    }

    public TipoUtente getUtente() {
        return utente;
    }

    public void setUtente(TipoUtente utente) {
        this.utente = utente;
    }

    private String email;
    private String nome;
    private String cognome;
    private String password;
    private TipoUtente utente;
}
