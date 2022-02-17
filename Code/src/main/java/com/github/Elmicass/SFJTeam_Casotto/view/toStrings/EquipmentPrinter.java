package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.EquipmentsManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class EquipmentPrinter implements Printer<Equipment> {

    @Autowired
    private EquipmentsManager equipmentsManager;

    @Override
    public String shortToStringVersion(Equipment eq) {
        String returnValue;
        returnValue = "- [ID: " + eq.getID() + " | Name: " + eq.getName() + " | Type: " + eq.getType().name() + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(Equipment eq) {
        String returnValue;
        String firstLine = String.format("%-215s", new String("+")) + "+";
        String secondLine = String.format("%-215s", new String("| [ID: " + eq.getID() + " - Equipment name: "
                + eq.getName() + " - Type: " + eq.getType().name() + " ]")) + "|";
        String thirdLine = String.format("%-215s", new String("| Description: " + eq.getDescription())) + "|";
        String fourthLine = String.format("%-215s", new String("+")) + "+";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<Equipment> list) {
        if (!(list.isEmpty())) {
            for (Equipment equipment : list) {
                System.out.println(shortToStringVersion(equipment));
                System.out.flush();
            }
        } else {
            System.out.println("[No equipments exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(Equipment eq) {
        System.out.println(shortToStringVersion(eq));
        System.out.flush();
    }

    @Override
    public void printFullVersion(Equipment eq) {
        System.out.println(fullToStringVersion(eq));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
