package model.dao;

/**
 * Questa eccezione viene lanciata quando si verifica una violazione di qualunque tipo di un entità presente in un
 * gestore della persistenza.
 *
 * @author De Caro Antonio
 * @version 0.1
 */
public class ViolazioneEntityException extends RuntimeException {

    public ViolazioneEntityException() {
        super();
    }

    public ViolazioneEntityException(String message) {
        super(message);
    }
}
