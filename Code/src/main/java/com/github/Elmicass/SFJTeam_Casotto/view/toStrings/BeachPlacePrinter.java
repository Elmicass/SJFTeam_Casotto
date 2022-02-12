package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.BeachPlacesManager;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class BeachPlacePrinter implements Printer<BeachPlace> {

    @Autowired
    private BeachPlacesManager bpManager;

    @Autowired
    private SeaRowsPrinter seaRowPrinter;

    @Override
    public String shortToStringVersion(BeachPlace bp) {
        String returnValue;
        returnValue = "◉ - [ID: " + bp.getID() + " | Sea row n°: " + bp.getSeaRow().getSeaRowNumber() + " | Size: "
                + bp.getType().name() + " | " + bp.getSunbedsNumber() + " sunbeds | " + bp
                        .getHourlyPrice()
                + "€/h ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(BeachPlace bp) {
        String returnValue;
        String firstLine = String.format("%-271s", new String("┌")) + "┐";
        String secondLine = String.format("%-271s",
                new String("| [ID: " + bp.getID() + " | Sea row n°: " + bp.getSeaRow().getSeaRowNumber() + " | Size: "
                        + bp.getType().name() + " | " + bp.getSunbedsNumber() + " sunbeds | " + bp
                                .getHourlyPrice()
                        + "€/h ]"))
                + "|";
        String thirdLine = String.format("%-271s", new String("|")) + "|";
        String fourthLine = seaRowPrinter.graphicRepBeachStructure(bp.getPosition());
        String fifthLine = String.format("%-271s", new String("└")) + "┘";
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
        } else
            System.out.println("[No beach places exists at the moment!]");
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

    public SeaRowsPrinter getSeaRowPrinter() {
        return seaRowPrinter;
    }

}
