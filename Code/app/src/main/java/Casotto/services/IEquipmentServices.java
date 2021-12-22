package Casotto.services;

import Casotto.model.*;

/**
 * TODO interfaccia javadoc
 */
public interface IEquipmentServices {

    /**
     * Crea un nuovo equipaggiamento
     * 
     * @param name        - nome del equipaggiamento
     * @param description - descrizione del equipaggiamento
     * @param type        - tipo del equipaggiamento
     * @return - true se la creazione è stata fatta correttamente, false altrimenti
     */
    boolean createEquipment(String name, String description, EquipmentType type);

    /**
     * Restituisce l'oggetto equipment associato al dato ID
     * 
     * @param equipmentID - ID associato a un dato equipment
     * @return - equipment
     */
    Equipment getEquipmentIstance(String equipmentID);

    /**
     * Rimouve l'equipaggiamento associato al dato ID
     * 
     * @param equipmentID - ID asssociato a un dato equipment
     * @return - true se la cancellazione è avvenuta correttamente, false altrimetni
     */
    boolean delete(String equipmentID);

}
