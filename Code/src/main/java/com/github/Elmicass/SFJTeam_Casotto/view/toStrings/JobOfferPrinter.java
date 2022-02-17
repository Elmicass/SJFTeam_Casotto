package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.JobOffersManager;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class JobOfferPrinter implements Printer<JobOffer> {

    @Autowired
    private JobOffersManager joManager;

    @Autowired
    private TimeSlotPrinter timePrinter;

    @Override
    public String shortToStringVersion(JobOffer offer) {
        String returnValue;
        String open;
        if (offer.isOpen())
            open = "Open";
        else
            open = "Closed";
        returnValue = "- [ID: " + offer.getID() + " | Name: " + offer.getName()
                + " | Expiration: " + timePrinter.localDateTimeToString(offer.getExpiration().getStop()) + " | "
                + open + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(JobOffer offer) {
        String returnValue;
        String open;
        if (offer.isOpen())
            open = "Open";
        else
            open = "Closed";
        String firstLine = String.format("%-215s", new String("+")) + "+";
        String secondLine = String.format("%-215s", new String("| [ID: " + offer.getID() + " - Name: " + offer.getName()
                                + " - Expiration: " + timePrinter.localDateTimeToString(offer.getExpiration().getStop())
                                + " - " + open + " ]")) + "|";
        String thirdLine = String.format("%-215s", new String("| Description: " + offer.getDescription())) + "|";
        String fourthLine = String.format("%-215s", new String("+")) + "+";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<JobOffer> list) {
        if (!(list.isEmpty())) {
            for (JobOffer jobOffer : list) {
                System.out.println(shortToStringVersion(jobOffer));
                System.out.flush();
            }
        } else {
            System.out.println("[No job offers exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(JobOffer jo) {
        System.out.println(shortToStringVersion(jo));
        System.out.flush();
    }

    @Override
    public void printFullVersion(JobOffer jo) {
        System.out.println(fullToStringVersion(jo));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public TimeSlotPrinter getTimePrinter() {
        return timePrinter;
    }

}
