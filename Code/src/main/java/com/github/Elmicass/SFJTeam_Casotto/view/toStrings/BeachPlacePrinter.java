package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.IBeachPlaceManager;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class BeachPlacePrinter implements Printer<BeachPlace> {

    @Autowired
    private IBeachPlaceManager bpManager;

    @Autowired
    private SeaRowsPrinter seaRowPrinter;

    @Override
    public String shortToStringVersion(BeachPlace bp) {
        String returnValue;
        returnValue = "- [ID: " + bp.getID() + " | Sea row n.: " + bp.getSeaRow().getSeaRowNumber() + " | Size: "
                + bp.getType().name() + " | " + bp.getSunbedsNumber() + " sunbeds | " + bp
                        .getHourlyPrice()
                + "EUR/h ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(BeachPlace bp) {
        String returnValue;
        String firstLine = String.format("%-215s", new String("+")) + "+";
        String secondLine = String.format("%-215s", new String("| [ID: " + bp.getID() + " | Sea row n.: " + bp.getSeaRow().getSeaRowNumber() + " | Size: "
                        + bp.getType().name() + " | " + bp.getSunbedsNumber() + " sunbeds | " + bp.getHourlyPrice() + "EUR/h ]")) + "|";
        String thirdLine = String.format("%-215s", new String("|")) + "|";
        String fourthLine = seaRowPrinter.graphicRepBeachStructure(bp.getPosition());
        String fifthLine = String.format("%-215s", new String("+")) + "+";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + fifthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<BeachPlace> list) {
        if (!(list.isEmpty())) {
            for (BeachPlace beachPlace : list) {
                System.out.println(shortToStringVersion(beachPlace));
                System.out.flush();
            }
        } else {
            System.out.println("[No beach places exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(BeachPlace bp) {
        System.out.println(shortToStringVersion(bp));
        System.out.flush();
    }

    @Override
    public void printFullVersion(BeachPlace bp) {
        System.out.println(fullToStringVersion(bp));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
