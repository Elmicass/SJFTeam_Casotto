package Casotto.model;

/**
 * Rappresenta una singola attrezzatura in possesso dello chalet, che verrà utilizzata per organizzare attività.
 */
public interface IEquipment {

    /**
     * Restituisce l'ID dell'attrezzatura.
     * @return - ID dell'attrezzatura
     */
    String getID();

    /**
     * Restituisce la descrizione dell'attrezzatura.
     * @return - descrizione dell'attrezzatura
     */
    String getDescription();


}
