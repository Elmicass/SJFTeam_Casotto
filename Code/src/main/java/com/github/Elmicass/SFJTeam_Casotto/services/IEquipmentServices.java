package com.github.Elmicass.SFJTeam_Casotto.services;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;

/**
 * Questa interfaccia è responsabile della gestione di tutte le attrezzatura per l'applicazione. 
 * Sa restituire un'istanza di qualsiasi attrezzatura mediante il suo ID, può crearne di nuove o eliminare attrezzature esistenti.
 */
public interface IEquipmentServices extends EntityServices<Equipment, String> {

    /**
     * 
     * @param name
     * @param description
     * @param type
     * @return
     */
    boolean createEquipment(String name, String description, String type);

}
