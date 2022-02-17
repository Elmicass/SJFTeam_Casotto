package com.github.Elmicass.SFJTeam_Casotto.view.toStrings;

import java.util.List;

import com.github.Elmicass.SFJTeam_Casotto.controller.PriceListsManager;
import com.github.Elmicass.SFJTeam_Casotto.model.PriceList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Lazy
@Component
@Transactional
public class PriceListPrinter implements Printer<PriceList> {

        @Autowired
        private PriceListsManager plManager;

        @Override
        public String shortToStringVersion(PriceList pl) {
                String returnValue;
                returnValue = "- [ID: " + pl.getID() + " | Name: " + pl.getName() + " | Sunbeds price: "
                                + pl.getSingleSunbedHourlyPrice() + "EUR/h | Small sunshades price: "
                                + pl.getSmallSunshadeHourlyPrice() + "EUR/h | Medium sunshades price: "
                                + pl.getMediumSunshadeHourlyPrice() + "EUR/h | Large sunshades price: "
                                + pl.getLargeSunshadeHourlyPrice()
                                + " ]";
                return returnValue;
        }

        @Override
        public String fullToStringVersion(PriceList pl) {
                String returnValue;
                String firstLine = String.format("%-215s", new String("+")) + "+";
                String secondLine = String.format("%-215s", new String("| [ID: " + pl.getID() + " - Pricelist name: " + pl.getName() + " ]")) + "|";
                String thirdLine = String.format("%-215s", new String("| Single sunbed hourly price: " + pl.getSingleSunbedHourlyPrice() + "EUR/h")) + "|";
                String fourthLine = String.format("%-215s", new String("| Small sunshade hourly price: " + pl.getSmallSunshadeHourlyPrice() + "EUR/h")) + "|";
                String fifthLine = String.format("%-215s", new String("| Medium sunshade hourly price: " + pl.getMediumSunshadeHourlyPrice() + "EUR/h")) + "|";
                String sixthLine = String.format("%-215s", new String("| Large sunshade hourly price: " + pl.getLargeSunshadeHourlyPrice() + "EUR/h")) + "|";
                String seventhLine = String.format("%-215s", new String("+")) + "+";
                returnValue = firstLine + "\n" + secondLine + "\n" + thirdLine + "\n" + fourthLine + "\n" + fifthLine + "\n"+ sixthLine + "\n" + seventhLine;
                return returnValue;
        }

        @Override
        public void printListOfObjects(List<PriceList> list) {
                if (!(list.isEmpty())) {
                        for (PriceList priceList : list) {
                                System.out.println(shortToStringVersion(priceList));
                                System.out.flush();
                        }
                } else {
                        System.out.println("[No price lists exists at the moment!]");
                }
                System.out.flush();
        }

        @Override
        public void printShortVersion(PriceList pl) {
                System.out.println(shortToStringVersion(pl));
                System.out.flush();
        }

        @Override
        public void printFullVersion(PriceList pl) {
                System.out.println(fullToStringVersion(pl));
                System.out.flush();
        }

        @Override
        public void clearConsoleScreen() {
                System.out.print("\033[H\033[2J");
                System.out.flush();
        }

}
