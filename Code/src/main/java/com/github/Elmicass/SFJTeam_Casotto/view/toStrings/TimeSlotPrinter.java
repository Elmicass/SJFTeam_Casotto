package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.model.TimeSlot;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class TimeSlotPrinter implements Printer<TimeSlot> {

    @Override
    public String shortToStringVersion(TimeSlot ts) {
        String returnValue;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        returnValue = ts.getStart().format(formatter);
        return returnValue;
    }

    @Override
    public String fullToStringVersion(TimeSlot ts) {
        String returnValue;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        returnValue = ts.getStart().format(formatter) + "/" + ts.getStop().format(formatter);
        return returnValue;
    }

    @Override
    public void printListOfObjects(List<TimeSlot> list) {
        if(!(list.isEmpty())) {
            clearConsoleScreen();
            for (TimeSlot timeSlot : list) {
                System.out.println(fullToStringVersion(timeSlot));
                System.out.flush();
            }
        } else {
            clearConsoleScreen();
            System.out.println("[No time slots exists at the moment!]");
        }
        System.out.flush();
    }

    @Override
    public void printShortVersion(TimeSlot ts) {
        System.out.println(shortToStringVersion(ts));
        System.out.flush();
    }

    @Override
    public void printFullVersion(TimeSlot ts) {
        System.out.println(fullToStringVersion(ts));
        System.out.flush();
    }

    @Override
    public void clearConsoleScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void printLocalDateTime(LocalDateTime dateTime) {
        System.out.println(localDateTimeToString(dateTime));
        System.out.flush();
    }

    public String localDateTimeToString(LocalDateTime dateTime) {
        String returnValue;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
        returnValue = dateTime.format(formatter);
        return returnValue;
    }

    

    
    
}
