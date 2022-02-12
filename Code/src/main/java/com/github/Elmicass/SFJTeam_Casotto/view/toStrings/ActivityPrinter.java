package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.ActivitiesManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.Equipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class ActivityPrinter implements Printer<Activity> {

    @Autowired
    private ActivitiesManager activitiesManager;

    @Autowired
    private TimeSlotPrinter tsPrinter;

    @Override
    public String shortToStringVersion(Activity act) {
        String returnValue;
        String maxEntries;
        if (act.getMaxEntries() == Integer.MAX_VALUE)
            maxEntries = "∞";
        else
            maxEntries = String.valueOf(act.getMaxEntries());
        returnValue = "◉ - [ID: " + act.getID() + " | Name: " + act.getName() + " | Timetable: "
                + tsPrinter.shortToStringVersion(act.getTimeSlot()) + " | "
                + act.getReservations().size() + "/" + maxEntries + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(Activity act) {
        String returnValue;
        String maxEntries;
        if (act.getMaxEntries() == Integer.MAX_VALUE)
            maxEntries = "∞";
        else
            maxEntries = String.valueOf(act.getMaxEntries());
        String firstLine = String.format("%-271s", new String("┌")) + "┐";
        String secondLine = String.format("%-271s",
                new String(
                        "| [ID: " + act.getID() + " - Activity name: " + act.getName() + " - Timetable: "
                                + tsPrinter.shortToStringVersion(act.getTimeSlot()) + " - "
                                + act.getReservations().size() + "/" + maxEntries + " ]"))
                + "|";
        String thirdLine = String.format("%-271s", new String("| Description: " + act.getDescription())) + "|";
        String fourthLine = String.format("%-271s", new String("| Equipment used:" + activityEquipmentsUsed(act)))
                + "|";
        String fifthLine = String.format("%-271s", new String("└")) + "┘";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + "\n" + fifthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<Activity> list) {
        if (!(list.isEmpty())) {
            for (Activity activity : list) {
                System.out.println(shortToStringVersion(activity));
                System.out.flush();
            }
        } else
            System.out.println("[No activities exists at the moment!]");
        System.out.flush();
    }

    @Override
    public void printShortVersion(Activity act) {
        System.out.println(shortToStringVersion(act));
        System.out.flush();
    }

    @Override
    public void printFullVersion(Activity act) {
        System.out.println(fullToStringVersion(act));
        System.out.flush();
    }

    public String activityEquipmentsUsed(Activity activity) {
        String returnValue = "";
        for (Equipment eq : activity.getEquipments()) {
            returnValue = returnValue + " " + eq.getName() + ", ";
        }
        returnValue = returnValue.substring(0, returnValue.length() - 2);
        return returnValue;
    }

    public TimeSlotPrinter getTsPrinter() {
        return tsPrinter;
    }

}
