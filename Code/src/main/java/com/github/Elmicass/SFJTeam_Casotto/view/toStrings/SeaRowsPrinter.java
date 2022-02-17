package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.Collections;
import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.SeaRowsManager;
import com.github.Elmicass.SFJTeam_Casotto.model.SeaRow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class SeaRowsPrinter implements Printer<SeaRow> {

    @Autowired
    private SeaRowsManager manager;

    @Override
    public String shortToStringVersion(SeaRow seaRow) {
        String returnValue;
        returnValue = "- [ID: " + seaRow.getID() + " | Row number: " + seaRow.getSeaRowNumber() + " | "
                + seaRow.getBeachPlaces().size() + "/" + seaRow.getMaxBeachPlacesInThisRow()
                + " | " + seaRow.getFixedPrice() + "EUR ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(SeaRow object) {
        return "";
    }

    @Override
    public void printListOfObjects(List<SeaRow> list) {
        if (!(list.isEmpty())) {
            for (SeaRow seaRow : list) {
                System.out.println(shortToStringVersion(seaRow));
                System.out.flush();
            }
        } else {
            System.out.println("[No sea rows exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(SeaRow sr) {
        System.out.println(shortToStringVersion(sr));
        System.out.flush();
    }

    @Override
    public void printFullVersion(SeaRow sr) {
        System.out.println(shortToStringVersion(sr));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void beachStructure(List<SeaRow> list) {
        if (!(list.isEmpty())) {
            for (SeaRow row : list) {
                System.out.println("[" + row.getSeaRowNumber() + "]" + " - " + "[" + rowMapToString(row) + " ]");
                System.out.flush();            
            }
        } else {
            System.out.println("[No sea row exists at the moment!]");
        }
        System.out.flush();
    }

    public String rowMapToString(SeaRow row) {
        String returnValue = "";
        for (int i = 1; i <= row.getMaxBeachPlacesInThisRow(); i++) {
            String beachPlace;
            if (row.getSeaRowMap().get(i) != null)
                beachPlace = row.getSeaRowMap().get(i).getType().name();
            else
                beachPlace = "EMPTY";
            returnValue = returnValue + " (" + i + ":" + beachPlace + ")";
            
        }
        return returnValue;
    }

    public String graphicRepSeaRow(SeaRow row, int beachPlacePos) {
        String returnValue = "";
        for (int i = 1; i <= row.getMaxBeachPlacesInThisRow(); i++) {
            String beachPlace;
            if (row.getSeaRowMap().get(i) != null)
                if (row.getSeaRowMap().get(i).getPosition() == beachPlacePos)
                    beachPlace = ">O<";
                else
                    beachPlace = "O";
            else
                beachPlace = "x";
            returnValue = returnValue + " (" + beachPlace + ")";            
        }
        return returnValue;
    }

    public String graphicRepBeachStructure(int beachPlacePos) {
        String returnValue = "";
        String init = "| ";
        List<SeaRow> seaRows = manager.getAll();
        if (!(seaRows.isEmpty())) {
            Collections.sort(seaRows); 
            for (SeaRow row : seaRows) {
                returnValue = returnValue + String.format("%-215s", init + "[" + row.getSeaRowNumber() + "]" + " - " + "[" + graphicRepSeaRow(row, beachPlacePos) + "]") + "|\n";            
            }
        } else
            returnValue = String.format("%-271s", "[Error in printing the beach structure!]");
        return returnValue;
    }

}
