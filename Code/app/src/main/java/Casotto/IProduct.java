/**
 * Interfaccia per la definizione del singolo prodotto
 */
public interface IProduct {
    /**
     * getID() restituisce ID del prodotto.
     * @return ID del prodotto.
     */
    getID();

    /**
     * getName() restituisce nome del prodotto.
     * @return Nome del prodotto.
     */
    getName();

    /**
     * getDescription() restituisce descrizione del prodotto.
     * @return descrizione del prodotto.
     */
    getDescription();

    /**
     * getPrice() restituisce il prezzo del pordotto.
     * @return prezzo del prodotto.
     */
    getPrice();
}
// come faccio il commit