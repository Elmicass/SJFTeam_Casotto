package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.ReservationsManager;
import com.github.Elmicass.SFJTeam_Casotto.model.Activity;
import com.github.Elmicass.SFJTeam_Casotto.model.BeachPlace;
import com.github.Elmicass.SFJTeam_Casotto.model.JobOffer;
import com.github.Elmicass.SFJTeam_Casotto.model.Reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class ReservationPrinter implements Printer<Reservation> {

    @Autowired
    private ReservationsManager resManager;

    @Autowired
    private TimeSlotPrinter timeSlotPrinter;

    @Autowired
    private BeachPlacePrinter bpPrinter;

    @Autowired
    private ActivityPrinter actPrinter;

    @Autowired
    private JobOfferPrinter joPrinter;

    @Override
    public String shortToStringVersion(Reservation reservation) {
        String returnValue;
        String when;
        switch (reservation.getType()) {
            case BeachPlace:
                when = timeSlotPrinter.fullToStringVersion(reservation.getTimeSlot());
                break;
            case Activity:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
            case JobOffer:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
            default:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
        }
        returnValue = "◉ - [ID: " + reservation.getID() + " | Customer username: " + reservation.getUser()
                + " | Reservation type: " + reservation.getType().name() + " | " + reservation.getType().name()
                + " ID: " + reservation.getEntityID() + " | When: " + when + " ]";
        return returnValue;
    }

    @Override
    public String fullToStringVersion(Reservation reservation) {
        String returnValue;
        String when;
        switch (reservation.getType()) {
            case BeachPlace:
                when = timeSlotPrinter.fullToStringVersion(reservation.getTimeSlot());
                break;
            case Activity:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
            case JobOffer:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
            default:
                when = timeSlotPrinter.shortToStringVersion(reservation.getTimeSlot());
                break;
        }
        String firstLine = String.format("%-271s", new String("┌")) + "┐";
        String secondLine = String.format("%-271s",
                new String(
                        "| [ID: " + reservation.getID() + " - Customer username: " + reservation.getUser()
                        + " - Reservation type: " + reservation.getType().name() + " - " + reservation.getType().name()
                        + " ID: " + reservation.getEntityID() + " - When: " + when + " ]")) + "|";
        String thirdLine = "";
        switch (reservation.getEntityObject().getClass().getSimpleName()) {
            case "BeachPlace":
                thirdLine = bpPrinter.fullToStringVersion((BeachPlace)reservation.getEntityObject());
                break;
            case "Activity":
                thirdLine = actPrinter.fullToStringVersion((Activity)reservation.getEntityObject());
                break;
            case "JobOffer":
                thirdLine = joPrinter.fullToStringVersion((JobOffer)reservation.getEntityObject()); 
                break;
        }
        String fourthLine = String.format("%-271s", new String("└")) + "┘";
        returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine;
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<Reservation> list) {
        if (!(list.isEmpty())) {
            for (Reservation reservation : list) {
                System.out.println(shortToStringVersion(reservation));
                System.out.flush();
            }
        } else
            System.out.println("[No reservations exists at the moment!]");
        System.out.flush();
    }

    @Override
    public void printShortVersion(Reservation res) {
        System.out.println(shortToStringVersion(res));
        System.out.flush();
    }

    @Override
    public void printFullVersion(Reservation res) {
        System.out.println(fullToStringVersion(res));
        System.out.flush();
    }

    public TimeSlotPrinter getTimeSlotPrinter() {
        return timeSlotPrinter;
    }

    public BeachPlacePrinter getBpPrinter() {
        return bpPrinter;
    }

    public ActivityPrinter getActPrinter() {
        return actPrinter;
    }

    public JobOfferPrinter getJoPrinter() {
        return joPrinter;
    }

}
