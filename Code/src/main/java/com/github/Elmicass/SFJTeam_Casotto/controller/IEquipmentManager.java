package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;

public interface IEquipmentManager extends EntityManager<Equipment> {

    boolean createEquipment(String name, String description, String type);

}
