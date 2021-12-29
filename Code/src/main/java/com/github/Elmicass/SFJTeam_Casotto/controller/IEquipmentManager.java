package com.github.Elmicass.SFJTeam_Casotto.controller;

import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment.EquipmentType;

public interface IEquipmentManager extends EntityManager<Equipment, String> {

    boolean createNewEquipment(String name, String description, EquipmentType type);


}
