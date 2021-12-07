package Casotto.controller;

import Casotto.model.*;

/**
 * Questa interfaccia ha la responsabilità di gestire le attrezzature dello chalet nell'applicazione. Sa restituire un'istanza di qualsiasi attrezzatura mediante il suo ID,
 * può crearne di nuove o eliminare attrezzature esistenti.
 */
public interface IEquipmentManager {

    /**
     * Restituisce un'istanza dell'attrezzatura associata all'ID dato.
     * @param ID - l'ID dell'attrezzatura ricercata
     * @return un'istanza dell'attrezzatura
     */
    Equipment getEquipmentInstance(String equipmentID);

    





}
