package model.daos;

/**
 * Questa eccezione viene lanciata quando si verifica una violazione di un entità di qualunque tipo.
 * @author De Caro Antonio
 * @version 0.1
 * */
public class ViolazioneEntity extends RuntimeException {

    public ViolazioneEntity() {
        super();
    }

    public ViolazioneEntity(String message) {
        super(message);
    }
}
